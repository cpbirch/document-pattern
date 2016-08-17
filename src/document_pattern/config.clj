(ns document-pattern.config
  (:require [clj-yaml.core :as yaml]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]))

(def config (atom "Not initialized yet"))

(defn get-config-filename [args]
  (if (nil? args)
    nil
    (first args)))

(defn get-config-content [filename]
  (if (.exists (io/file filename))
    (slurp filename)
    (do
      (log/warnf "Config file [%] does not exist" filename)
      nil)))

(defn get-config [yaml-str]
  (if (nil? yaml-str)
    nil
    (->> yaml-str
         yaml/parse-string
         (reset! config))))

(def load-config (comp get-config get-config-content get-config-filename))