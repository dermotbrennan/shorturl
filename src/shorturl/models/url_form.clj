(ns shorturl.models.url_form
  (:require [noir.validation :as vali]
            [shorturl.models.url :as url_model])
  (:use [shorturl.middleware :only (log)]
        noir.core
        noir.request
        hiccup.core
        hiccup.page-helpers)
  (:import (org.apache.commons.validator UrlValidator)))

(defn valid? [{:keys [original shortened]}]
  (def validator (new UrlValidator))
  (vali/rule (vali/has-value? original)
             [:original "You must have a url"])
  (vali/rule (.isValid validator original)
             [:original "You must have be a valid url"])
  (vali/rule (or (not (vali/has-value? shortened))
                 (and (vali/has-value? shortened)
                   (not (url_model/shortened_already_exists? shortened))))
             [:shortened "Shortened url name already exists!"])
  (vali/rule (not (url_model/original_already_exists? original))
             [:original (str "URL already exists as: "
                             (str "<a href='"
                              (format "/%s"
                                      (:shortened (url_model/find_by_original original)))
                              "' target='_blank'>"
                              (format "/%s"                                    
                                      (:shortened (url_model/find_by_original original)))
                              "</a>" ))])
  (not (vali/errors? :original :shortened)))
