(ns adventofcode2021.09
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseChar in? not-in?]]))

(defn parse-input
  [input]
  (map #(map parseChar (str % )) (s/split-lines input)))

(defn getHeightAtCoord
  "Gets the height at the coord. If it's out of bounds we return backupValue.
  If not passed in we default to Integer/MAX_VALUE"
  ([[x y] map]
   (getHeightAtCoord [x y] map Integer/MAX_VALUE))
  ([[x y] map backupValue]
   (nth (nth map y []) x backupValue)))

(defn getSurroundingCoords
  [[x y]]
  (let [up [x (dec y)]
        down [x (inc y)]
        right [(inc x) y]
        left [(dec x) y]]
    [up down right left]))

(defn isLowPoint?
  [[x y] map]
  (let [currHeight (getHeightAtCoord [x y] map)
        surrounding (getSurroundingCoords [x y])]
    (every? #(> (getHeightAtCoord % map) currHeight) surrounding)))

(defn getLowPointCoords
  [grid]
  (let [width (count (first grid))
        height (count grid)]
    (for [x (range width)
          y (range height)
          :when (isLowPoint? [x y] grid)]
      [x y])))

(defn getLowPointValues
  [grid]
  (map #(getHeightAtCoord % grid) (getLowPointCoords grid)))
(defn getBasinSize
  "DFS For basins"
  [[x y] map]
  (loop [visited #{}
         toVisit [[x y]]]
    (if (empty? toVisit)
      visited
        (let [[curr & rest] toVisit
              currHeight (getHeightAtCoord [x y] map)
              surrounds (getSurroundingCoords curr)
              candidates (filter #(and (not-in? visited %)
                                       (not-in? toVisit %)
                                       (> (getHeightAtCoord % map -1) currHeight)
                                       (not= (getHeightAtCoord % map) 9)) surrounds)]
          (println candidates)
          (recur (conj visited curr) (concat rest candidates))))))

(defn solvep1
  [input]
  (->> (parse-input input)
       getLowPointValues
       (map inc)
       (reduce +)))

(defn solvep2
  [input]
  (let [grid (parse-input input)]
    (->> (map #(getBasinSize % grid) (getLowPointCoords grid))
         (map count)
         sort
         (take-last 3)
         (reduce *))))

(solvep1 (slurp (io/resource "09-input.txt")))
(solvep2 (slurp (io/resource "09-input.txt")))
