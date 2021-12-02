(ns adventofcode2021.01
  [:require [clojure.string :as s]]
  (:require [clojure.java.io :as io]))

(def window-size 3)

(def nums
  (->>
   (slurp (io/resource "01-input.txt"))
   (s/split-lines)
   (mapv #(Integer/parseInt %))))

(defn countIncreases
  [arr]
  (->> (partition 2 1 arr) ;; Create 2 wide sliding window
       (filter (fn [[a b]] (< a b)))
       (count)))

(defn solve-p1
  []
  (countIncreases nums))


(defn solve-p2
  []
  (->> nums
       (partition window-size 1)
       (map #(reduce + %))
       (countIncreases)))
