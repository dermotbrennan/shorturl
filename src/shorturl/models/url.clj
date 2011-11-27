(ns shorturl.models.url
  (:use [shorturl.models.base :only (db)]
        [shorturl.middleware :only (log)])
  (:require [clojure.java.jdbc :as sql]))

(defn all []
  (sql/with-connection db
    (sql/with-query-results results
      ["select * from urls order by created_at desc"]
      (into [] results))))

(defn create [url]
  (sql/with-connection db
    (sql/insert-values :urls (keys url) (vals url))))

(defn inc_views [url]
  (log (str (:id url)))
  (sql/with-connection db
    (sql/do-prepared "update urls set views = views + 1 where id = ?" [(:id url)]))) 


(defn find_by_shortened [shortened]
  (sql/with-connection db
    (sql/with-query-results results
      ["select * from urls where shortened = ? limit 1" shortened]
      (first results))))

(defn find_by_original [original]
  (sql/with-connection db
    (sql/with-query-results results
      ["select * from urls where original = ? order by created_at asc limit 1" original]
      (first results))))

(defn shortened_already_exists? [shortened]
  (not (nil? (find_by_shortened shortened))))

(defn original_already_exists? [original]
  (not (nil? (find_by_original original))))