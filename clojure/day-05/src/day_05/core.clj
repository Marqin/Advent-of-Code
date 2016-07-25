(ns day-05.core
  (:gen-class))

(defn test-string-1 [string]
  (if (<= 3 (count (take 3 (re-seq #"[aeiou]" string))))
    (if (empty? (re-seq #"(ab|cd|pq|xy)" string))
      (if (not (empty? (re-seq #"([a-z])\1" string)))
        "nice"
        "naughty")
      "naughty")
    "naughty"))

;(defn test-string-1 [string]
;  "nice")

(defn -main []
  (let [lines (line-seq (java.io.BufferedReader. *in*))]
      (println (count (filter #(= "nice" %) (map test-string-1 lines))))))
;      (println (count (filter #(= "nice" %) (map test-string-2 lines))))))
