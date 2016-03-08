(ns zip-code-reframe.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [zip-code-reframe.handlers]
              [zip-code-reframe.subs]
              [zip-code-reframe.views :as views]
              [zip-code-reframe.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
