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

(def invalidCharToPoints
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(defn validLine?
  [line]
  (loop [stack []
         [currSymbol & rest] line]

    (cond
      (nil? currSymbol) stack
      (openers->closers currSymbol) (recur (conj stack (openers->closers currSymbol)) rest)
      (= (peek stack) currSymbol) (recur (pop stack) rest)
      :else currSymbol)))

(defn solvep1
  [input]
  (->> input
       parse-input
       (map validLine?)
       (map invalidCharToPoints)
       (filter some?)
       (reduce +)))

(def completionCharToPoints
  {\) 1
   \] 2
   \} 3
   \> 4})

(defn scoreCompletions
  [list]
  (->> (for [x (range (count list))]
         (* (Math/pow 5 x) (completionCharToPoints (nth list x))))
       (reduce + )
       biginteger))

(defn getMiddleScore
  [list]
  (nth list (/ (count list) 2)))

(defn solvep2
  [input]
  (->> input
       parse-input
       (map validLine?)
       (filter seqable?)
       (map scoreCompletions)
       sort
       getMiddleScore))

(solvep1 (slurp (io/resource "10-input.txt")))
(solvep2 (slurp (io/resource "10-input.txt")))
