(ns day-04.core
  (:gen-class)
  (:require[clojure.string :as str])
  (:use [clojure.math.combinatorics :only [combinations]]))

(defn bool-to-int [b]
  (compare b false))

(defn is-anagram? [x y]
  (=
    (frequencies x)
    (frequencies y)))

(defn has-no-anagrams [passphrase]
  (->>
    (combinations passphrase 2)
    (map #(apply is-anagram? %))
    (filter true?)
    (count)
    (zero?)))

(defn has-no-repetitions [passphrase]
  (=
    (count passphrase)
    (count (set passphrase))))

(defn solve-puzzle [validator lines]
  (->>
    lines
    (map #(str/split % #"\s+"))
    (map validator)
    (map bool-to-int)
    (reduce +)))

(defn -main []
  (let [lines (line-seq (java.io.BufferedReader. *in*))]
    (println (solve-puzzle has-no-repetitions lines))
    (println (solve-puzzle has-no-anagrams lines))))
