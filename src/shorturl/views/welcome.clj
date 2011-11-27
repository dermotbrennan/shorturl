(ns shorturl.views.welcome
  (:require [shorturl.views.common :as common]
            [shorturl.views.urls :as url_views]
            )
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        [shorturl.middleware :only (log)]
        ))

(defpage "/" []
  (common/layout
   [:p "ShortURL is a url shortener built in Clojure and Noir as an experiment. The code is available on github."]
   [:hr]
   [:h2 "Create new Shortened URL"]
   (url_views/url_form url)))