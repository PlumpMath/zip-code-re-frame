(ns zip-code-reframe.config)

(def debug?
  ^boolean js/goog.DEBUG)

#_(when debug?
  (enable-console-print!))
(enable-console-print!)
