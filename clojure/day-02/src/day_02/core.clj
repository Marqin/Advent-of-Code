(ns day-02.core
  (:gen-class)
  (:require clojure.string))

(defn get-facets [[h l w]]
  [(* l w) (* h w) (* l h)])

(defn paper [dimensions]
  (let [facets (get-facets dimensions)]
    (+
      (reduce + (map #(* 2 %) facets))
      (apply min facets))))

(defn ribbon [dimensions]
  (+
    (* 2 (reduce + (take 2(sort dimensions))))
    (reduce * dimensions)))

(defn parse-line [line]
  (map #(java.lang.Integer/parseInt %) (clojure.string/split line #"x" )))

(defn -main []
  (let [boxes (map parse-line (line-seq (java.io.BufferedReader. *in*)))]
      (println (reduce + (map paper boxes)))
      (println (reduce + (map ribbon boxes)))))
