; ; slower than loop version
;
; (ns day-04-async.core
;   (:gen-class)
;   (:require [clojure.core.async
;     :as a
;     :refer [>! <! >!! <!! go-loop chan put! poll!]]))
;
;
; ;;; lazy (and blocking) sequence from channel
; ; (defn seq!! [ch]
; ;   (lazy-seq
; ;     (when-let [v (<!! ch)]
; ;     (cons v (seq!! ch)))))
;
; (defn md5 [input]
;   (let [
;       hash-bytes
;         (doto
;           (java.security.MessageDigest/getInstance "MD5")
;           (.reset)
;           (.update (.getBytes input)))
;       hash-string
;         (as-> (.digest hash-bytes) v
;           (java.math.BigInteger. 1 v)
;           (.toString v 16))
;       padding
;         (as-> (count hash-string) v
;           (- 32 v)
;           (repeat v "0")
;           (apply str v))]
;     (str padding hash-string)))
;
; (defn md5-producer [ch pass]
;   (go-loop [x 0]
;     (->>
;       (str pass x)
;       (md5 ,,,)
;       (vector x ,,,)
;       (>! ch ,,,))
;       (recur (inc x))))
;
; (defn md5-consumer [ch-in ch-out]
;   (go-loop []
;     (let [[x hash] (poll! ch-in)]
;         (if
;           (and
;             hash
;             (.startsWith hash "000"))
;           (>! ch-out x)
;           (recur)))))
;
; (defn -main []
;   (let [pass "iwrupvqb" pc-pipe (chan 5000000) result (chan)]
;     (md5-producer pc-pipe pass)
;     (md5-consumer pc-pipe result)
;     (println (<!! result))))
