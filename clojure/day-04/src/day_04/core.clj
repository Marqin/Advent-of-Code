; six 0 version took 1.5 min on 1.3 GHz i5 processor
; TODO - concurrent version?

(ns day-04.core
  (:gen-class))

(defn md5 [input]
  (let [
      hash-bytes
        (doto
          (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes input)))
      hash-string
        (.toString
          (java.math.BigInteger. 1 (.digest hash-bytes)) 16)
      padding (apply str (repeat (- 32 (count hash-string)) "0"))]
    (str padding hash-string)))

(defn -main []
  (let [pass "iwrupvqb"]
    (println (first (for [x (range) :when (.startsWith (md5 (str pass x)) "000000")] x)))))
