(ns adventofcode2021.04
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [clojure.walk :as w])
  (:require [clojure.set :refer [union subset?]])
  (:require [adventofcode2021.util :refer [transpose parseInt not-in?]]))

(defn createBoardCell
  [val]
  {:value val
   :picked false})

(defn cleanBoard
  "Clean string representation of a single board"
  [board]
  (let [board (as-> (s/split board #"\n") $
                (map s/trim $)
                (mapv #(s/split % #" +") $)
                (map #(map parseInt %) $)
                )

        rows (map #(set %) board)
        columns (map #(set %) (transpose board))]
    {:board board
     :bingos (concat rows columns)}))

(defn parse-input
  [input]
  (let [[calls & boards] (s/split input #"\n\n")
        cleanedCalls (as-> calls $
                       (s/split $ #",")
                       (map #(Integer/parseInt %) $)
                       )
        cleanedBoards (map cleanBoard boards)]
    {:calls cleanedCalls
     :boards cleanedBoards
     :score 0}))

(defn bingo-found?
  [board calls]
  ((complement empty?) (filter #(subset? % (set calls)) (:bingos board))))

(defn sum-board-no-calls
  "Gets the sum of the board not including marked values"
  [board marked]
  (->> board
       (flatten)
       (filter #(not-in? marked %))
       (reduce +)))

(defn score-board
  "Returns a new board object with the :score attribute defined"
  [board marked]
  (assoc board :score
         (* (last marked) (sum-board-no-calls (:board board) marked))))

(defn find-bingo
  [{:keys [boards calls]}]
  (loop [n 0
         in-play-boards boards
         bingod []]
    (let [current-calls (take n calls)
          bingod-boards (group-by #(bingo-found? % current-calls) in-play-boards)
          scored-boards (map #(score-board % current-calls) (get bingod-boards true))]
      (if (empty? in-play-boards)
        bingod
        (recur (inc n) (get bingod-boards false) (concat bingod scored-boards))))))



(defn solvep1
  [input]
  (->> (parse-input input)
       (find-bingo)
       (first)
       (:score)))

(defn solvep2
  [input]
  (->> (parse-input input)
       (find-bingo)
       (last)
       (:score)))


(solvep1 (slurp (io/resource "04-input.txt")))
(solvep2 (slurp (io/resource "04-input.txt")))
