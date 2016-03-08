(ns zip-code-reframe.handlers
    (:require [re-frame.core :as re-frame]
              [zip-code-reframe.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
