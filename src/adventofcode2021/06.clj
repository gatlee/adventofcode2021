(ns adventofcode2021.06
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(def fishTimer 6)
(def newFishTimer 8)

(defn parse-input
  [input]
  (as-> input $
    (re-seq #"\w+" $)
    (map parseInt $)))

(defn iterate-fish
  [fish]
  (if (zero? fish)
    [fishTimer newFishTimer]
    [(dec fish)]))

(defn solvep1
  [input]
  (->>
   (iterate #(mapcat iterate-fish %) (parse-input input))
   (take 81)
   last
   count))

(defn solvep2
  [input]
  (->>
   (iterate #(mapcat iterate-fish %) (parse-input input))
   (take 257)
   last
   count))

(solvep1 (slurp (io/resource "06-input.txt")))
;;(solvep2 (slurp (io/resource "06-input.txt")))
