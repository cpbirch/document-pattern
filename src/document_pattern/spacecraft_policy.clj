(ns document-pattern.spacecraft-policy)

(defn- spaceship-check [doc]
  (if (and (contains? doc :type) (= (:type doc) "Spacecraft"))
    doc; return the doc, it's OK
    (assoc doc :invalid "Type is not Spacecraft")))

(defn- warp-factor-check [{:keys [warp-factor] :as doc}]
  (if warp-factor
    (assoc doc :insured-value (* 1000000 warp-factor))
    (assoc doc :missing-info "No warp factor specified")))

(defn- calc-premium [doc]
  (if (or (contains? doc :invalid) (contains? doc :missing-info))
    doc
    (as-> {} result
          (:warp-factor doc)
          (* result 1000)
          (assoc doc :premium result))))

(defn evaluate [doc]
  (-> doc
      (spaceship-check)
      (warp-factor-check)
      (calc-premium)))
