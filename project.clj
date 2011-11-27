(defproject shorturl "1.0.0-SNAPSHOT"
  :description "A very simple url shortener"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [postgresql/postgresql "9.0-801.jdbc4"]
                 [org.clojure/java.jdbc "0.1.0"]
                 [noir "1.2.0"]
                 [commons-validator/commons-validator "1.3.1"]
                 [oro/oro "2.0.8"]
                 ]
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :main shorturl.server)