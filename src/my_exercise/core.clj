(ns my-exercise.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [my-exercise.home :as home]
            [my-exercise.search_results :as search_results]))

(defroutes app
  (GET "/" [] home/page)
  (POST "/search" [] search_results/page)
  (route/resources "/")
  (route/not-found "Not found"))

(def handler
  (-> app
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      wrap-reload))
