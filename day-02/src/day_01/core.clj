(ns day-02.core
  (:gen-class)
  (:require[clojure.string :as str])
  (:use [clojure.math.combinatorics :only [combinations]]))

(defn custom-divide [x y]
  (quot (max x y) (min x y)))

(defn evenly-divisible? [x y]
  (zero? (mod (max x y) (min x y))))

(defn get-min-max [list]
  [(apply min list) (apply max list)])

(defn get-evenly-divisible [list]
  (->>
    (combinations list 2)
    (filter #(apply evenly-divisible? %))
    (flatten)
    (vec)))

(defn parse-line [number-selector reducer line]
  (->>
    (str/split line #"\s+")
    (map #(Integer/parseInt %))
    (number-selector)
    (reduce reducer)
    (Math/abs)))

(defn solve-puzzle [number-selector reducer lines]
  (->>
    lines
    (map #(parse-line number-selector reducer %))
    (reduce +)))

(defn -main []
  (let [lines (line-seq (java.io.BufferedReader. *in*))]
    (println (solve-puzzle get-min-max - lines))
    (println (solve-puzzle get-evenly-divisible custom-divide lines))))