(ns day-01.core
  (:gen-class))

(defn group-digits [x]
  (->>
    (list (first x))
    (concat x)
    (partition-by identity)))

(defn has-multiple-elements [x]
  (> (count x) 1))

(defn solve-puzzle [x]
  (->>
    (group-digits x)
    (filter has-multiple-elements)
    (map butlast)
    (flatten)
    (map #(Character/digit % 10))
    (reduce +)))

(defn -main []
  (let [x (read-line)]
    (println (solve-puzzle x))))
