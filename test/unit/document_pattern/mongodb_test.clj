(ns document-pattern.mongodb-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [document-pattern.mongodb :refer :all]))

(def mongo-conf {:db-host "localhost" :db-port 27017 :db-name "test-vrb"})

(def boaty {:type "Ship" :name "RRS Boaty McBoatface" :aggregate-id 2})

(def marley-1 {:time 1 :equipment 3 :type "Ship"})
(def marley-2 {:time 2 :equipment 3 :name "RRS Boat Marley and the Whalers"})

(facts "about connecting to mongodb" :mongodb-tests
       (with-state-changes [(before :contents (connect-to-db mongo-conf))
                            (after :contents (do (remove-all-counters)
                                                 (remove-collection "equipment")
                                                 (disconnect)))]
                           (fact "get a sequence value"
                                 (get-next-counter-val "equipment") => 0)
                           (fact "get a sequence value"
                                 (get-next-counter-val "equipment") => 1)
                           (fact "insert a document"
                                 (insert "equipment" boaty)
                                 => (contains boaty))
                           (fact "fetch a document"
                                 (fetch-one "equipment" {:aggregate-id 2}) => (contains boaty))
                           (fact "fetch two documents"
                                 (insert "equipment" marley-2)
                                 (insert "equipment" marley-1)
                                 (count (fetch "equipment" {:equipment 3})) => 2
                                 (count (fetch "equipment" {:equipment 3} {:time 1})) => 2)
                           ))
