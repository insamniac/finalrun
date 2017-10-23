(ns user
  (:require 
            [mount.core :as mount]
            [finalrun.figwheel :refer [start-fw stop-fw cljs]]
            finalrun.core))

(defn start []
  (mount/start-without #'finalrun.core/repl-server))

(defn stop []
  (mount/stop-except #'finalrun.core/repl-server))

(defn restart []
  (stop)
  (start))


