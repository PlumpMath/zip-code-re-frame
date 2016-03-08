(ns zip-code-reframe.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [goog.net.XhrIo :as xhr]))

(re-frame/register-sub :zip-code (fn [db] (reaction (:zip-code @db))))

(re-frame/register-sub 
  :zip-response 
  (fn 
    [db _] 
    (let [raw (reaction (:zip-response @db))]
      (reaction @raw))))

(re-frame/register-sub 
  :look-up-zip-code 
  (fn 
    [db [_ zip]] 
    (let [xhr-obj (xhr/send 
                    (str "http://api.zippopotam.us/us/" zip) 
                    #(re-frame/dispatch [:zip-response (-> % .-target .getResponseText)]))]
      (println "Sent request! Before reaction")
      (reaction
        (fn [] (println "in request reaction") (get-in @db [:zip-response] ""))))))

