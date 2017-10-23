(ns ^:figwheel-no-load finalrun.app
  (:require [finalrun.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
