(ns zip-code-reframe.views
  (:require [re-frame.core :as r]))

(defn valid-zip? [s] (re-find #"^[0-9]{5}$" s))

(defn zip-lookupper [zip-code zip-response]
  (println "input" @zip-code "output" @zip-response)
  [:div.zip
   [:input {:value @zip-code
            :type "text"
            :name "zip"
            :placeholder "enter US zipcode"
            :onChange #(r/dispatch [:update-text (-> % .-target .-value) [:zip-code]])}]
   [:p (if (valid-zip? @zip-code)
         (do (r/subscribe [:look-up-zip-code @zip-code]) 
             @zip-response) 
         "Enter a VALID zip code please…")]])

(defn main-panel []
  (let [zip-code (r/subscribe [:zip-code])
        zip-response (r/subscribe [:zip-response])]
    (fn []
      [zip-lookupper zip-code zip-response])))
