(ns day-02.core
  (:gen-class))

(require 'clojure.string)
(import 'java.lang.Integer)

(defn sides [h l w]
  [(* l w) (* h w) (* l h)])

(defn paper [[h l w]]
  (let [sizes (sides h l w)]
    (+
      (reduce + (map #(* 2 %) sizes))
      (apply min sizes))))

(defn parse-line [line]
  (paper (map #(Integer/parseInt %) (clojure.string/split line #"x" ))))

(defn -main []
  (println (reduce + (map parse-line (line-seq (java.io.BufferedReader. *in*))))))
