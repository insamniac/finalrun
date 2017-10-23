(ns finalrun.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [finalrun.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[finalrun started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[finalrun has shut down successfully]=-"))
   :middleware wrap-dev})
