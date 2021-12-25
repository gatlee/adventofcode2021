(ns adventofcode2021.07
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(defn parse-input
  [input]
  (as-> input $
    (s/trim $)
    (s/split $ #",")
    (map parseInt $)))

(defn diff
  [a b]
  (Math/abs (- a b)))

(defn increasingDiff
  ""
  [a b]
  (let [diff (Math/abs (- a b))]
    (* (inc diff) (/ diff 2) )))

(defn linearFuel
  [crabs pos diffFn]
  (reduce + (map #(diffFn % pos) crabs)))

(defn findLowestFuel
  [crabs fuelFn]
  (loop [i 1
         prev (linearFuel crabs 0 fuelFn)]
    (let [curr (linearFuel crabs i fuelFn)]
      (if (> curr prev)
        {:prev prev
         :pos (dec i )}
        (recur (inc i) curr)))))

(defn solvep1
  [input]
  (as-> (parse-input input) $
      (findLowestFuel $ diff)))

(defn solvep2
  [input]
  (as-> (parse-input input) $
      (findLowestFuel $ increasingDiff)))

(solvep1 (slurp (io/resource "07-input.txt")))
(solvep2 (slurp (io/resource "07-input.txt")))
