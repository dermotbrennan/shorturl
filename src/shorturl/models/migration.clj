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
                      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
                      ["CONSTRAINT urls_pkey PRIMARY KEY (id)"] ,
                      ["CONSTRAINT urls_original_key UNIQUE (original)"],
                      ["CONSTRAINT urls_shortened_key UNIQUE (shortened)"]
                      )
    (sql/do-commands "CREATE INDEX urls_created_at_idx ON urls USING btree (created_at)"
                     "CREATE INDEX urls_id_idx ON urls USING btree (id)"
                     "CREATE INDEX urls_original_idx ON urls USING btree (original)"
                     "CREATE INDEX urls_shortened_idx ON urls USING btree (shortened)"
                     "CREATE INDEX urls_views_idx ON urls USING btree (views)")))

(defn -main []
  (print "Migrating database...") (flush)
  (create-urls)
    (println " done"))