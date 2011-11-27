(ns shorturl.models.migration
  (:use [shorturl.models.base :only (db)])
  (:require [clojure.java.jdbc :as sql]))

(defn create-urls []
  (sql/with-connection db
    (sql/create-table :urls
                      [:id :serial "PRIMARY KEY"]
                      [:shortened :varchar "NULL"]
                      [:original :varchar "NOT NULL"]
                      [:views :integer "NOT NULL"]
                      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn -main []
  (print "Migrating database...") (flush)
  (create-urls)
    (println " done"))