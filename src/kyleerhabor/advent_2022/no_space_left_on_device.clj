(ns kyleerhabor.advent-2022.no-space-left-on-device
  (:require
   [clojure.string :as str]
   [clojure.walk :refer [postwalk]]))

(def max-disk-space 70000000)
(def update-required-space 30000000)
(def update-max-disk-space (- max-disk-space update-required-space))

(defn parse [s]
  (reduce (fn [ws [cmd res]]
            (let [[name arg] (str/split cmd #" ")]
              (case name
                "cd" (if (= ".." arg) ; Up one
                       (update ws :dir pop)
                       (update ws :dir conj arg))
                "ls" (assoc-in ws `[:contents ~@(interpose :contents (:dir ws)) :contents]
                       (reduce (fn [dir content]
                                 (let [[type filename] (str/split content #" ")]
                                   (assoc dir filename (if (= "dir" type)
                                                         {:contents {}}
                                                         {:size (parse-long type)})))) {} (str/split-lines res)))
                ws))) {:dir []}
    (map #(str/split (str/trim %) #"\n" 2) (next (str/split s #"\$ ")))))

(defn dir-sizes [tree]
  (postwalk (fn [node]
              (if (and (map? node) (contains? node :contents))
                (assoc node :size (reduce + (map :size (vals (:contents node))))) 
                node)) tree))

(defn part-one [s]
  (let [dir (dir-sizes (:contents (parse s)))
        dir-size (get-in dir ["/" :size])
        size (transient [])]
    (postwalk (fn [node]
                (if (and (map? node) (contains? node :contents))
                  (conj! size (:size node))
                  node)) dir)
    (reduce (fn [x y]
              (let [space (- dir-size update-max-disk-space y)]
                (if (and (not (pos? space)) (< y x))
                  y
                  x))) dir-size (persistent! size))))

(defn part-two [s]
  (postwalk (fn [node]
              (if (map? node)
                
                node)) (parse s)))

(comment
  (require '[kyleerhabor.advent-2022.core :refer [input]])
  
  (def s (input "inputs/no-space-left-on-device.txt"))
  
  (part-one s))
