(ns document-pattern.resources
  (:require [ring.middleware.json :refer :all]
            [ring.util.response :as r]
            [document-pattern.equipment :as e]
            [document-pattern.ship-policy :as ship]
            [document-pattern.spacecraft-policy :as spacecraft])
  (:gen-class))

(def routes ["/" {"equipment"   {:get      {["/" :id] :fetch-equipment}
                                 ["/" :id] {:post :update-equipment}
                                 :post     :create-equipment}
                  "insure-ship" {:post     :insure-ship}
                  "insure-spacecraft" {:post :insure-spacecraft}}])

(defn fetch-equipment [{:keys [route-params]}]
  (r/response (e/get-aggregate (:id route-params))))

(defn create-equipment [{body :body}]
  (r/response (e/create-event body)))

(defn update-equipment [{body :body params :route-params}]
  (r/response (e/update-event (:id params) body)))

(defn insure-ship [{body :body}]
  (println body)
  (r/response (ship/evaluate body)))

(defn insure-spacecraft [{body :body}]
  (r/response (spacecraft/evaluate body)))

(def handlers
  {:fetch-equipment   (wrap-json-response fetch-equipment)
   :create-equipment  (wrap-json-response (wrap-json-body create-equipment))
   :update-equipment  (wrap-json-response (wrap-json-body update-equipment))
   :insure-ship       (wrap-json-response (wrap-json-body insure-ship {:keywords? true :bigdecimals? true}))
   :insure-spacecraft (wrap-json-response (wrap-json-body insure-spacecraft {:keywords? true :bigdecimals? true}))})
