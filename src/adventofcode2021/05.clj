(ns adventofcode2021.05
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(defn parse-line
  [line]
  (let [[x1 y1 x2 y2] (map parseInt (re-seq #"\d+" line))]
    {:from [x1 y1]
     :to [x2 y2]}))

(defn parse-input
  [input]
  (as-> input $
    (s/split-lines $)
    (map parse-line $)))

(defn get-directional-unit
  [val]
  (cond
    (pos? val) 1
    (neg? val) -1
    (zero? val) 0))

(defn get-unit-vector
  [[ax ay] [bx by]]
  [(get-directional-unit (- bx ax))
   (get-directional-unit (- by ay))])

(defn coord-sum
  [a b]
  (map + a b))

(defn generate-coords-between
  [a b]
  (let [unit (get-unit-vector a b)]
    (take-while (comp not nil?) (iterate (fn [curr] (if (= curr b)
                                     nil
                                     (coord-sum curr unit))) a))))

(defn is-horizontal-vertical?
  [[ax ay] [bx by]]
  (xor (= ax bx)
            (= ay by)))

(defn solvep1
  [input]
  (->> input
       (parse-input)
       (filter #(is-horizontal-vertical? (:from %) (:to %)))
       (mapcat #(generate-coords-between (:from %) (:to %)))
       (frequencies)
       (filter #(> (val %) 1))
       (count)))

(defn solvep2
  [input]
  (->> input
       (parse-input)
       (mapcat #(generate-coords-between (:from %) (:to %)))
       (frequencies)
       (filter #(> (val %) 1))
       (count)))

(solvep1 (slurp (io/resource "05-input.txt")))
(solvep2 (slurp (io/resource "05-input.txt")))
