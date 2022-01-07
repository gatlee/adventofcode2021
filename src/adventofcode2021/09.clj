(ns adventofcode2021.09
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseChar]]))

(defn parse-input
  [input]
  (let [grid (map #(map parseChar (str % )) (s/split-lines input))]
    {:width (count (first grid))
     :height (count grid)
     :data grid}))

(defn solvep1
  [input]
  (parse-input input))

(defn solvep2
  [input]
  input)

(solvep1 (slurp (io/resource "09-input.txt")))
;(solvep2 (slurp (io/resource "09-input.txt")))
