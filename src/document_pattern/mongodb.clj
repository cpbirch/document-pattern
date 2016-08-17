(ns document-pattern.mongodb
  (:refer-clojure :exclude [sort find])
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :refer :all]
            [monger.query :refer :all]
            [monger.util :as util])
  (:import org.bson.types.ObjectId)
  (:gen-class))

(def conn (atom "Not initialized yet"))
(def db (atom "Not initialized yet"))

(defn connect-to-db [{:keys [db-host db-port db-name]}]
  (do
    (println (str "Setting Up the conn for " db-host db-port))
    (reset! conn (mg/connect {:host db-host :port db-port}))
    (println (str "Setting up the DB for " db-name))
    (reset! db (mg/get-db @conn db-name))))

(defn disconnect []
  (reset! conn (mg/disconnect @conn)))

(defn get-next-counter-val [counter]
  (or
    (:val (mc/find-and-modify @db "counters" {:counter-name counter} {$inc {:val 1}} {:upsert true}))
    0))

(defn remove-collection [collection] (mc/remove @db collection))

(defn remove-all-counters [] (remove-collection "counters"))

(defn insert [collection doc]
  (->> (merge doc {:_id (util/random-uuid)})
       (mc/insert-and-return @db collection)))

(defn fetch-one [collection query]
  (mc/find-one-as-map @db collection query))

(defn fetch
  ([collection query] (mc/find-maps @db collection query))
  ([collection query sorter] (with-collection @db collection
                                             (find query)
                                             (sort sorter))))


