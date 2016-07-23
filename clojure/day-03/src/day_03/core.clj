(ns day-03.core
  (:gen-class)
  (:require clojure.set))

(defn move [[x y] ch]
  (case ch
    \^ [x (+ y 1)]
    \v [x (- y 1)]
    \> [(+ x 1) y]
    \< [(- x 1) y]
    [x y]))

(defn -main []
  (let [x (read-line)]
    (println (count (set (reductions move [0 0] x))))
    (println (let [ santa (take-nth 2 x) robot (take-nth 2 (rest x)) ]
      (count (clojure.set/union (set (reductions move [0 0] santa)) (set (reductions move [0 0] robot))))))))
