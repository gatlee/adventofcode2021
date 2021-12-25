(ns adventofcode2021.03
  [:require [clojure.string :as s]]
  (:require [clojure.java.io :as io])
  (:require [adventofcode2021.util :refer [transpose]]))




(defn mostCommon
  [lst]
  (first (apply max-key second (frequencies lst))))


(defn inverse
  [s]
  (String/join "" (map (fn [ch]
                     (case ch
                       \0 \1
                       \1 \0)) s)))

(defn gammaRate
  [input]
  (as-> (slurp (io/resource "03-input.txt")) $
    (s/split-lines $)
    (transpose $)
    (map mostCommon $)))

(def epsilonRate #(inverse (gammaRate %)))

(defn solve-p1
  [input]
  )

(defn getRating
  [input majFn]
  (loop [index 0
         candidates input]
    (if (= 1 (count candidates))
      (first candidates)
      (let [zeroCandidates (filter #(= "0" (get % index)) candidates)
            oneCandidates  (filter #(= "1" (get % index)) candidates)]
        (recur (inc index) (majFn zeroCandidates oneCandidates))))))


(defn oxygen
  [zeros ones]
  (if (<= (count zeros) (count ones))
    ones
    zeros))

(defn co2
  [zeros ones]
  (if (<= (count zeros) (count ones))
    zeros
    ones))

(defn solve-p2
  [input]
  (let [oxygenRating (Integer/parseInt (String/join "" (getRating nums oxygen)) 2)
        co2Rating (Integer/parseInt (String/join "" (getRating nums co2)) 2)]
    (* oxygenRating co2Rating)))



(def nums
  (as-> (slurp (io/resource "03-input.txt")) $
    (s/split-lines $)
    (mapv #(s/split % #"") $)))



(solve-p1 nums)
;;(solve-p2 nums)
