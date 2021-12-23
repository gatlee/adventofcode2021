(ns adventofcode2021.04
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [clojure.walk :as w]))

(defn createBoardCell
  [val]
  {:value val
   :picked false})

(defn cleanBoard
  "Clean string representation of a single board"
  [board]
  (as-> (s/split board #"\n") $
            (map s/trim $)
            (mapv #(s/split % #" +") $)
            (mapv #(mapv createBoardCell %) $)))


(defn parsep1
  [input]
  (let [[calls & boards] (s/split input #"\n\n")
        cleanedCalls (as-> calls $
                          (s/split $ #",")
                          (map #(Integer/parseInt %) $))
        cleanedBoards (map cleanBoard boards)]
    (def c cleanedCalls)
    (def b cleanedBoards)
    ))

(defn solvep1
  [input]
  (->> (parsep1 input)))

(defn solvep2
  [input])


(solvep1 (slurp (io/resource "04-input.txt")))
;; (solvep2 (slurp (io/resource "04-input.txt")))

b
