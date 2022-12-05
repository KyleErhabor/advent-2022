(ns kyleerhabor.advent-2022.camp-cleanup 
  (:require [clojure.string :as str]))

(defn overlaps-fully? [a1 a2]
  (<= (first a1) (first a2) (second a2) (second a1)))

(defn overlaps? [a1 a2]
  ;; Adapted.
  (cond
    (= (first a1) (first a2)) true
    (< (first a1) (first a2)) (>= (second a1) (first a2))
    :else (>= (second a2) (first a1))))

(defn parse-assignment [s]
  (map parse-long (str/split s #"-")))

(defn parse-pair [s]
  (map parse-assignment (str/split s #",")))

(defn parse [s]
  (map parse-pair (str/split-lines s)))

(defn part-one [s]
  (->> (parse s)
    (filter (fn [[a1 a2]]
              (or (overlaps-fully? a1 a2) (overlaps-fully? a2 a1))))
    count))

(defn part-two [s]
  (->> (parse s)
    (filter (fn [[a1 a2]]
              (overlaps? a1 a2)))
    count))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [input]])

  (def s (input "inputs/camp-cleanup.txt"))
  
  (part-one s)
  (part-two s))
