(ns adventofcode2021.util)

(defn downloadProblem
  [num])

(defn transpose [m]
  (apply mapv vector m))

(defn parseInt [val]
  (Integer/parseInt val))

(defn in?
  [coll elm]
  (some #(= elm %) coll))

(def not-in? (complement in?))
(defn xor
  [a b]
  (or (and a (not b))
      (and (not a) b)))
