(ns zip-code-reframe.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [cognitect.transit :as transit]))

(def json-reader (transit/reader :json))

(re-frame/register-sub :zip-code (fn [db] (reaction (:zip-code @db))))

(re-frame/register-sub 
  :zip-response
  (fn 
    [db _] 
    (let [raw (reaction (:zip-response @db))]
      (reaction (condp = (first @raw)
                  :success (get-in #_(js->clj (.parse js/JSON @raw)) 
                                   (transit/read json-reader (second @raw))
                                   ["places" 0 "place name"] "Parse error?")
                  :not-found "Zip code not found!"
                  :sent "Loading…"
                  "eh? unknown error!")))))

