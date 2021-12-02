(ns adventofcode2021.02
  [:require [clojure.string :as s]]
  (:require [clojure.java.io :as io]))

(def nums
  (as-> (slurp (io/resource "02-input.txt")) $
    (s/split-lines $)
    (mapv #(s/split % #" ") $)))

(defn getNextPos
  [[x y] [direction amountStr]]
  (let [amount (Integer/parseInt amountStr)]
    (case direction
      "forward" [(+ x amount) y]
      "down" [x (+ y amount)]
      "up" [x (- y amount)])))

(defn getNextPosP2
  [[x y aim] [direction amountStr]]
  (let [amount (Integer/parseInt amountStr)]
    (case direction
      "forward" [(+ x amount) (+ y (* aim amount)) aim]
      "down" [x y (+ aim amount)]
      "up" [x y (- aim amount)])))

(defn solve-p1
  [input]
  (reduce getNextPos [0 0] input))

(defn solve-p2
  [input]
  (reduce getNextPosP2 [0 0 0] input))

(solve-p1 nums)
(solve-p2 nums)
