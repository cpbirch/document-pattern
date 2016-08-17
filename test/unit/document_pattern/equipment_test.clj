(ns document-pattern.equipment-test
  (:require [document-pattern.equipment :refer :all]
            [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [document-pattern.mongodb :as db]))

(def mongo-conf {:db-host "localhost" :db-port 27017 :db-name "test-vrb"})

(def event-extras {:equipment string? :created-date anything})
(def boat-1 {:type "Ship", :name "RRS Boaty McBoatface"})
(def boat-2 {:sub-type "research vessel", :tonnage 15000})

(facts "about equipment" :equipment-tests
       (with-state-changes [(before :contents (db/connect-to-db mongo-conf))
                            (after :contents (do (db/remove-all-counters)
                                                 (db/remove-collection "equipment")
                                                 (db/disconnect)))]
                           (fact "Create equipment"
                                 (create-event boat-1) => (contains (merge boat-1 event-extras)))
                           (fact "Update equipment"
                                 (update-event "0" boat-2) => (contains boat-2))
                           (fact "get equipment (aggregated)"
                                 (get-aggregate "0") => (contains (merge boat-1 boat-2)))
                           ))
