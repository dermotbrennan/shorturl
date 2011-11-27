(ns shorturl.server
  (:require [noir.server :as server])
  (:use shorturl.middleware)
  )

(server/load-views "src/shorturl/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/add-middleware wrap-request-logging)
    (server/start port {:mode mode
                        :ns 'shorturl})))
