(ns kyleerhabor.advent-2022.rock-paper-scissors
  (:require [clojure.string :as str]))

(defn play [s]
  (case s
    ("A" "X") :rock
    ("B" "Y") :paper
    ("C" "Z") :scissors))

(defn round [s]
  (map play (str/split s #" ")))

(defn parse [s]
  (map round (str/split-lines s)))

(def wins {:rock {:draw :scissors
                  :score 1}
           :paper {:draw :rock
                   :score 2}
           :scissors {:draw :paper
                      :score 3}})

(defn won? [p o]
  (= (:draw (p wins)) o))

(defn score [s f]
  (->> (parse s)
    (map f)
    (reduce +)))

(defn part-one [s]
  (score s (fn [[o p]]
             (+ (:score (p wins)) (cond
                                    (won? p o) 6
                                    (won? o p) 0
                                    :else 3)))))

(defn part-two [s]
  (score s (fn [[o p]]
             (case p
               ;; X, lose.
               :rock (:score ((:draw (o wins)) wins))
               ;; Y, draw.
               :paper (+ 3 (:score (o wins)))
               ;; Z, win.
               :scissors (+ 6 (:score ((:draw ((:draw (o wins)) wins)) wins)))))))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [input]])
  
  (def s (input "inputs/rock-paper-scissors.txt"))
  
  (part-one s)
  (part-two s))
