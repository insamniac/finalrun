(ns finalrun.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[finalrun started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[finalrun has shut down successfully]=-"))
   :middleware identity})
