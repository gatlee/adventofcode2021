(ns adventofcode2021.06
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(defn parse-input
  [input]
  (as-> input $
    (re-seq #"\w+" $)
    (map parseInt $)
    (frequencies $)))

(defn iterate-fish
  [fish-tally]
  {0 (get-in fish-tally [1] 0)
   1 (get-in fish-tally [2] 0)
   2 (get-in fish-tally [3] 0)
   3 (get-in fish-tally [4] 0)
   4 (get-in fish-tally [5] 0)
   5 (get-in fish-tally [6] 0)
   6 (+ (get-in fish-tally [0] 0) (get-in fish-tally [7] 0))
   7 (get-in fish-tally [8] 0)
   8 (get-in fish-tally [0] 0)
   })

(defn solvep1
  [input]
  (->> (parse-input input)
       (iterate iterate-fish)
       (take 81)
       last
       (map val)
       (reduce +)))

(defn solvep2
  [input]
  (->> (parse-input input)
       (iterate iterate-fish)
       (take 257)
       last
       (map val)
       (reduce +)))

(solvep1 (slurp (io/resource "06-input.txt")))
(solvep2 (slurp (io/resource "06-input.txt")))
