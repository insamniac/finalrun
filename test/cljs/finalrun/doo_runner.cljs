(ns finalrun.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [finalrun.core-test]))

(doo-tests 'finalrun.core-test)

