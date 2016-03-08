(ns zip-code-reframe.handlers
  (:require [re-frame.core :as re-frame]
            [zip-code-reframe.db :as db]))

(re-frame/register-handler
  :initialize-db
  (fn  [_ _]
    db/default-db))

(re-frame/register-handler
  :update-text
  (fn [db [_ new-val path]]
    (assoc-in db path new-val)))

(re-frame/register-handler
  :zip-response
  (fn [db [_ response]]
    (assoc-in db [:zip-response] response)))

