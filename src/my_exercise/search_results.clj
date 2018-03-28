(ns my-exercise.search_results
  (:require [hiccup.page :refer [html5]]
            [my-exercise.us-state :as us-state]
            [ring.middleware.params :as params]
            [clj-http.client :as client]
            [clojure.edn :as edn]))

(defn get-params [request]
  (params/assoc-form-params request "UTF-8"))

(defn params-to-url [params]
  (str "https://api.turbovote.org/elections/upcoming?district-divisions=" "ocd-division/country:us/state:la/parish:orleans" \, "ocd-division/country:us/state:ia/place:ames"))
; curl 'https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:la/parish:orleans,ocd-division/country:us/state:ia/place:ames'

(defn get-election-data [url]
  (client/get url))

(defn show-election-data [data]
  [:div {:class "election-description"}
    [:h1 (edn/read-string (get data :body))]]
)

(defn page [request]
  (html5
    (-> request
      (get-params)
      (params-to-url)
      (get-election-data)
      (show-election-data))))
