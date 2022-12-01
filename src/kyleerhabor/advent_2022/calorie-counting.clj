(ns kyleerhabor.advent-2022.calorie-counting
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn calorie [s]
  {:calorie (parse-long s)})

(defn elf [s]
  {:calories (map calorie (str/split-lines s))})

(defn elves [s]
  (map elf (str/split s #"\n{2}")))

(defn calories [cals]
  (reduce + (map :calorie cals)))

(defn cals [s]
  (map #(calories (:calories %)) (elves s)))

(defn part-one [input]
  (reduce max (cals input)))

(defn part-two [input] 
  (reduce + (take 3 (sort > (cals input)))))

(comment
  (def counts (slurp (io/resource "inputs/calorie-counting.txt")))
  
  (part-one counts)
  (part-two counts))
