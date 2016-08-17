(ns document-pattern.equipment
  (:require [document-pattern.mongodb :as db]
            [clj-time.core :as t]
            [clj-time.format :as f])
  (:gen-class))

(def fmt (f/formatters :basic-date-time-no-ms))
(defn fmt-now [] (f/unparse fmt (t/now)))

(defn create-event [attribute-map]
  (->> {:event-type   "create-event"
        :created-date (fmt-now)
        :equipment    (str (db/get-next-counter-val "equipment"))}
       (merge attribute-map)
       (db/insert "equipment")))

(defn update-event [equipment-id attribute-map]
  (->> {:event-type   "update-event"
        :created-date (fmt-now)
        :equipment    equipment-id}
       (merge attribute-map)
       (db/insert "equipment")))

(defn remove-meta [equipment]
  (dissoc equipment :event-type :created-date))

(defn get-aggregate [equipment-id]
  (->> (db/fetch "equipment" {:equipment equipment-id} {:created-time 1})
       (reduce merge)
       (remove-meta)))