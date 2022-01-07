(ns adventofcode2021.09
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [adventofcode2021.util :refer [parseChar in? not-in?]]))

(defn parse-input
  [input]
  (let [grid (map #(map parseChar (str % )) (s/split-lines input))]
    {:width (count (first grid))
     :height (count grid)
     :data grid}))

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
  [{:keys [width height data]}]
  (for [x (range width)
        y (range height)
        :when (isLowPoint? [x y] data)]
    [x y]))

(defn getLowPointValues
  [dataObj]
  (map #(getHeightAtCoord % (:data dataObj)) (getLowPointCoords dataObj)))

(defn solvep1
  [input]
  (->> (parse-input input)
       getLowPointValues
       (map inc)
       (reduce +)))

(defn getBasinSize
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


(defn solvep2
  [input]
  (let [dataObj (parse-input input)
        data (:data dataObj)]
    (->> (map #(getBasinSize % data) (getLowPointCoords dataObj))
         (map count)
         sort
         (take-last 3)
         (reduce *))))

;(solvep1 (slurp (io/resource "09-input.txt")))
(solvep2 (slurp (io/resource "09-input.txt")))
