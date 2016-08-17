(ns document-pattern.resources
  (:require [ring.middleware.json :refer :all]
            [ring.util.response :as r]
            [document-pattern.equipment :as e])
  (:gen-class))

(def routes ["/" {"equipment"   {:get      {["/" :id] :fetch-equipment}
                                 ["/" :id] {:post :update-equipment}
                                 :post     :create-equipment}}])

(defn fetch-equipment [{:keys [route-params]}]
  (r/response (e/get-aggregate (:id route-params))))

(defn create-equipment [{body :body}]
  (r/response (e/create-event body)))

(defn update-equipment [{body :body params :route-params}]
  (r/response (e/update-event (:id params) body)))

(def handlers
  {:fetch-equipment  (wrap-json-response fetch-equipment)
   :create-equipment (wrap-json-response (wrap-json-body create-equipment))
   :update-equipment (wrap-json-response (wrap-json-body update-equipment))})
