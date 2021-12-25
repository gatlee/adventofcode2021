(ns adventofcode2021.06
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor]]))

(defn parse-input
  [input]
  (as-> input $
    (s/split-lines $)))

(defn solvep1
  [input]
  input)

(defn solvep2
  [input]
  input)

(solvep1 (slurp (io/resource "06-input.txt")))
(solvep2 (slurp (io/resource "06-input.txt")))
