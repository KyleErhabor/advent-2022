(ns kyleerhabor.advent-2022.rucksack-reorganization 
  (:require
    [clojure.set :refer [intersection]]
    [clojure.string :as str]))

(defn same? [f x]
  (= x (f x)))

(defn priority [c]
  (+ (int c) (if (same? #(first (str/lower-case %)) c)
               -96
               -38)))

(defn part-one [s]
  (->> (str/split-lines s)
    (map #(partition (/ (count %) 2) %))
    (map (fn [[c1 c2]]
           (priority (first (intersection (set c1) (set c2))))))
    (reduce +)))

(defn part-two [s]
  (->> (partition 3 (str/split-lines s))
    (map (fn [[b1 b2 b3]]
           (priority (first (intersection (set b1) (set b2) (set b3))))))
    (reduce +)))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [input]])
  
  (def s (input "inputs/rucksack-reorganization.txt"))
  
  (part-one s)
  (part-two s))
