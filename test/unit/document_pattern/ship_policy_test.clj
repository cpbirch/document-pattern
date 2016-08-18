(ns document-pattern.ship-policy-test
  (:require [document-pattern.ship-policy :as policy]
            [clojure.test :refer :all]
            [midje.sweet :refer :all]))

(def boat-2 {:type "Ship", :name "RRS Boaty McBoatface" :sub-type "research vessel"})
(def boat-1 (assoc boat-2 :tonnage 15000))
(def spacecraft {:type "Spacecraft"})

(facts "about insurance for ships" :insurance-tests
       (fact "insure a ship"
             (policy/evaluate boat-1) => (contains {:insured-value 1500000 :premium 150}))
       (fact "can't underwrite the ship, missing information"
             (policy/evaluate boat-2) => (contains {:missing-info "No tonnage specified"}))
       (fact "unprocessable, is this an application for ship insurance?"
             (policy/evaluate spacecraft) => (contains {:invalid string?})))
