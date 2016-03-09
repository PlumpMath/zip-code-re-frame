(ns zip-code-reframe.handlers
  (:require [re-frame.core :as re-frame]
            [zip-code-reframe.db :as db]
            [goog.net.XhrIo :as xhr]))

(re-frame/register-handler
  :initialize-db
  (fn  [_ _]
    db/default-db))

(defn valid-zip? [s] (re-find #"^[0-9]{5}$" s))

(re-frame/register-handler
  :update-zip
  (fn
    [db [_ new-val]]
    (let [xhr-obj (when (valid-zip? new-val)
                    (xhr/send
                      (str "http://api.zippopotam.us/us/" new-val)
                      #(do
                         (println "hi from xhr cb:" (-> % .-target .isSuccess))
                         (re-frame/dispatch
                           [:zip-response
                            (if (-> % .-target .isSuccess)
                              [:success (-> % .-target .getResponseText)]
                              [:not-found])]))))]
      (when xhr-obj (println "XHR requested!"))
      (-> db
          (assoc-in [:zip-response] [:sent])
          (assoc-in [:zip-code] new-val)))))

(re-frame/register-handler
  :zip-response
  (fn [db [_ response]]
    (assoc-in db [:zip-response] response)))

