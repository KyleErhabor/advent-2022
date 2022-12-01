(ns kyleerhabor.advent.calorie-counting
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn- cals []
  (->> (str/split (slurp (io/resource "inputs/calorie-counting.txt")) #"\n{2}")
    (map #(reduce + (map parse-long (str/split-lines %))))))

(defn part-one []
  (reduce max (cals)))

(defn part-two []
  (reduce + (take 3 (sort > (cals)))))
