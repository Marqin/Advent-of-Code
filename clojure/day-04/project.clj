(defproject day-04 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
    :url "https://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/clojure "1.8.0"],
    [org.clojure/core.async "0.2.385"]]
  :main ^:skip-aot day-04.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
