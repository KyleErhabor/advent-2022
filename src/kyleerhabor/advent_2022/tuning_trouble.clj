(ns kyleerhabor.advent-2022.tuning-trouble
  (:require
   [clojure.string :as str]))

(defn part-one [s]
  (+ 4 (str/index-of s (->> (map (fn [& cs]
                                   (if (apply distinct? cs)
                                     (apply str cs))) s (drop 1 s) (drop 2 s) (drop 3 s))
                         (filter identity)
                         first))))

(defn part-two [s]
  (+ 14 (str/index-of s (->> (map (fn [& cs]
                                    (if (apply distinct? cs)
                                      (apply str cs))) s
                               (drop 1 s) (drop 2 s) (drop 3 s) (drop 4 s) (drop 5 s) (drop 6 s) (drop 7 s) (drop 8 s)
                               (drop 9 s) (drop 10 s) (drop 11 s) (drop 12 s) (drop 13 s))
                          (filter identity)
                          first))))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [input]])

  (def s (input "inputs/tuning-trouble.txt"))
  
  (part-one s)
  
  (part-two s))
