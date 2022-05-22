(ns adventofcode2021.10
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(defn parse-input
  [input]
  (as-> input $
    (s/split-lines $)))

(def openers->closers
  {\{ \}
   \( \)
   \[ \]
   \< \>})

(def charToPoints
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(defn validLine?
  [line]
  (loop [stack []
         [currSymbol & rest] line]

    (cond
      (nil? currSymbol) true
      (openers->closers currSymbol) (recur (conj stack (openers->closers currSymbol)) rest)
      (= (peek stack) currSymbol) (recur (pop stack) rest)
      :else currSymbol)))



(defn solvep1
  [input]
  (->> input
       parse-input
       (map validLine?)
       (map charToPoints)
       (filter some?)
       (reduce +)))

(defn solvep2
  [input]
  input)

(solvep1 (slurp (io/resource "10-input.txt")))
(solvep2 (slurp (io/resource "10-input.txt")))
