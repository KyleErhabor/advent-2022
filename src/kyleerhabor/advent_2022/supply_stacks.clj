(ns kyleerhabor.advent-2022.supply-stacks
  (:require [clojure.string :as str]))

(defn parse-stacks [s]
  (let [lines (str/split-lines s)
        stacks (butlast lines)
        nums (last lines)
        line-nums (map parse-long (re-seq #"\d+" nums))]
    (mapv (fn [line]
            (let [pad (* 4 (dec line))]
              (remove str/blank? (map #(subs % pad (+ 3 pad)) stacks)))) line-nums)))

(defn parse-move [s]
  (let [[n from to] (map parse-long (re-seq #"\d+" s))]
    {:crates n
     :from from
     :to to}))

(defn parse-moves [s]
  (map parse-move (str/split-lines s)))

(defn parse [s]
  (let [[crates moves] (str/split s #"\n{2}")]
    {:stacks (parse-stacks crates)
     :moves (parse-moves moves)}))

(defn msg
  ([s] (msg s identity))
  ([s f]
   (let [data (parse s)
         o-stacks (reduce (fn [crates move]
                            (let [idx (dec (:from move))
                                  [moving orig] (split-at (:crates move) (get crates idx))]
                              (-> crates
                                (update (dec (:to move)) #(concat (f moving) %))
                                (assoc idx orig)))) (:stacks data) (:moves data))]
     (str/join (map #(subs (first %) 1 2) o-stacks)))))

(defn part-one [s]
  (msg s reverse))

(defn part-two [s]
  (msg s))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [capture input]])

  (def s (input "inputs/supply-stacks.txt"))

  (capture (part-one s))
  
  (part-two s))
