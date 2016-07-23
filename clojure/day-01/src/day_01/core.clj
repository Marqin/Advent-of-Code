(ns day-01.core
  (:gen-class))

(defn move [ch]
  (case ch
    \( 1
    \) -1
    0))

(defn part-one [x]
  (println (reduce + x)))

(defn part-two [x]
  (println (+ 1 (.indexOf (reductions + x) -1))))

(defn -main []
  (let [x (map move (read-line))]
    (part-one x)
    (part-two x)))
