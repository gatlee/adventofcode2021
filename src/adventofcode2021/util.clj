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
