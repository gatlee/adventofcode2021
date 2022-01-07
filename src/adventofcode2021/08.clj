(ns adventofcode2021.08
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseInt xor in?]])
  (:require [clojure.set :refer [difference subset? union] ]))

(defn parse-input1
  [input]
  (as-> input $
    (s/split-lines $)
    (map #(re-seq #"\w+" %) $)))

(defn solvep1
           [input]
           (->> (parse-input1 input)
                (mapcat #(take-last 4 %))
                (filter #(in? [7 4 2 3] (count %)))
                count))

(defn parse-input2
  [input]
  (as-> input $
    (s/split-lines $)
    (map #(re-seq #"\w+" %) $)
    (map #(map set %) $)
    (map #(split-at 10 %) $)))


;  0:6     1:2     2:5     3:5     4:4
; aaaa    ....    aaaa    aaaa    ....
;b    c  .    c  .    c  .    c  b    c
;b    c  .    c  .    c  .    c  b    c
; ....    ....    dddd    dddd    dddd
;e    f  .    f  e    .  .    f  .    f
;e    f  .    f  e    .  .    f  .    f
; gggg    ....    gggg    gggg    ....
;
;  5:5     6:6     7:3     8:7     9:6
; aaaa    aaaa    aaaa    aaaa    aaaa
;b    .  b    .  .    c  b    c  b    c
;b    .  b    .  .    c  b    c  b    c
; dddd    dddd    ....    dddd    dddd
;.    f  e    f  .    f  e    f  .    f
;.    f  e    f  .    f  e    f  .    f
; gggg    gggg    ....    gggg    gggg


; AAAA
;B    C
;B    C
; DDDD
;E    F
;E    F
; GGGG

(defn deduce-digits
  [[signals display]]
  (let [getWithSize (fn [size] (filter #(= size (count %)) signals))
        CF (first (getWithSize 2)) ;;1
        BCDF (first (getWithSize 4)) ;;4
        ACF (first (getWithSize 3)) ;7
        ABCDEFG (first (getWithSize 7)) ;8
        A (difference ACF CF)
        set069 (getWithSize 6)
        ABDEFG (first (filter #((complement subset?) CF %) set069)) ;6
        C (difference ABCDEFG ABDEFG)
        F (difference CF C)
        set235 (getWithSize 5)
        ACDFG (first (filter #(subset? CF %) set235)) ;3
        BE (difference ABCDEFG ACDFG)
        D (difference BCDF CF BE)
        E (difference BE BCDF)
        B (difference BE E)
        G (difference ABCDEFG A B C D E F)

        signals->display {(union A B C E F G) 0
                          CF 1
                          (union A C D E G) 2
                          ACDFG 3
                          BCDF 4
                          (union A B D F G) 5
                          ABDEFG 6
                          ACF 7
                          ABCDEFG 8
                          (union A B C D F G) 9
                          }
        ]

    (map #(signals->display %) display)
    ))


(defn solvep2
  [input]
  (->> input
       parse-input2
       (map deduce-digits)
       (map (partial s/join ""))
       (map parseInt)
       (reduce +)))

(solvep1 (slurp (io/resource "08-input.txt")))
(solvep2 (slurp (io/resource "08-input.txt")))
