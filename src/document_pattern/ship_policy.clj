(ns document-pattern.ship-policy)

(defn- ship-check [doc]
  (if (and (contains? doc :type) (= (:type doc) "Ship"))
    doc; return the doc, it's OK
    (assoc doc :invalid "Type is not Ship")))

(defn- tonnage-check [{:keys [tonnage] :as doc}]
  (if tonnage
    (assoc doc :insured-value (* 100 tonnage))
    (assoc doc :missing-info "No tonnage specified")))

(defn- calc-premium [doc]
  (if (or (contains? doc :invalid) (contains? doc :missing-info))
    doc
    (as-> {} result
          (:tonnage doc)
          (/ result 100)
          (assoc doc :premium result))))

(defn evaluate [doc]
  (-> doc
      (ship-check)
      (tonnage-check)
      (calc-premium)))
