(defproject day-04 "0.1.0-SNAPSHOT"
  :description "Day 4: High-Entropy Passphrases"
  :url "http://adventofcode.com/2017/day/4"
  :license {:name "MIT License"
    :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.4"]]
  :main ^:skip-aot day-04.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
