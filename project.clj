(defproject venue-resource-booking "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[bidi "1.25.0"]
                 [clj-time "0.11.0"]
                 [clj-yaml "0.4.0"]
                 [com.novemberain/monger "3.0.2"]
                 [metrics-clojure "2.6.1"]
                 [metrics-clojure-ring "2.6.1"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-json "0.4.0"]]

  :main ^:skip-aot venue-resource-booking.core
  :target-path "target/%s"
  :test-paths  ["test/unit"]

  :plugins  [[lein-ring "0.9.7"]
             [lein-midje "3.2"]]

  :profiles {:uberjar {:aot :all}
             :dev     {:dependencies [[midje "1.8.3"]]}})
