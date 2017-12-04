(defproject day-02 "0.1.0-SNAPSHOT"
  :description "Day 2: Corruption Checksum"
  :url "http://adventofcode.com/2017/day/2"
  :license {:name "MIT License"
    :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.4"]]
  :main ^:skip-aot day-02.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
