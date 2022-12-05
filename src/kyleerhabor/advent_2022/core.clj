(ns kyleerhabor.advent-2022.core
  (:require [clojure.java.io :as io]))

(defn input [res]
  (slurp (io/resource res)))
