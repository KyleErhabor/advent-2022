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

(def input s)

(def crates
  (-> (str/replace input #"\[|\]" " ")
    (str/replace #" " "-")
    (str/split-lines)
    (as-> c
      (let [separator (.indexOf c "")
            c (take separator c)
            c (map #(str/split % #"") c)]
        (->>
          (apply map vector c)
          (mapv
            #(filterv
               (fn [x] (re-find #"[a-zA-Z0-9]" x))
               %))
          (filterv #(> (count %) 1))
          (map #(let [key (read-string (last %))
                      res (pop %)]
                  {key (apply list res)}))
          (apply merge))))))

(def steps
  (-> input
    (str/split-lines)
    (as-> c
      (let [separator (.indexOf c "")
            c (nthrest c (+ separator 1))
            c (map #(str/split % #" ") c)
            c (mapv #(filterv
                       (fn [x]
                         (re-find #"[0-9]" x))
                       %)
                c)]
        (mapv #(mapv (fn [x] (read-string x)) %)
          c)))))

(def arranged-crates
  (reduce
    (fn [acc step]
      (let [from (second step)
            to (last step)
            quantity (first step)]
        (-> acc
          (assoc to
            (concat (reverse
                      (take quantity
                        (get acc from)))
              (get acc to)))
          (assoc from
            (remove (set (take quantity
                           (get acc
                             from)))
              (get acc from))))))
    crates
    steps))

(defn part-one
  [coll]
  (->> (sort coll)
    (vals)
    (map #(first %))
    str/join))
