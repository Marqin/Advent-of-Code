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
        (as-> (.digest hash-bytes) v
          (java.math.BigInteger. 1 v)
          (.toString v 16))
      padding
        (as-> (count hash-string) v
          (- 32 v)
          (repeat v "0")
          (apply str v))]
    (str padding hash-string)))

(defn -main []
  (let [pass "iwrupvqb"]
    (->
      [x (range) :when
        (->
          (str pass x)
          (md5 ,,,)
          (.startsWith ,,, "00000"))]
      (for ,,, x)
      (first ,,,)
      (println ,,,))))
