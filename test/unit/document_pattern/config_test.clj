(ns document-pattern.config-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [document-pattern.config :refer :all]))

(def test-config "http:\n  port: 9090\n  admin-port: 9091")

(facts "about config"
       (fact "configuration is the first element is the args"
             (get-config-filename ["config/equip.yml" "nothing to see here"])
             => "config/equip.yml")
       (fact "configuration is missing"
             (get-config-filename [])
             => nil)

       (fact "load configuration from a file"
             (empty? (get-config-content "config/equip.yml"))
             => false)

       (fact "parse config as yaml"
             ((get-config test-config) :http)
             => {:port 9090 :admin-port 9091})
       (fact "don't parse when there's no config string"
             (get-config nil) => nil)

       (fact "loads configuration from a file"
             (empty? (load-config ["config/equip.yml"]))
             => false)
       (fact "check configuration loaded from a file is in a variable, requires previous load config test to have run"
             (get-in @config [:http :port])
             => 9090)
       (fact "fails to load configuration from a file"
             (load-config ["wibble"])
             => nil)
       (fact "check configuration still exist"
             (get-in @config [:http :port])
             => 9090))