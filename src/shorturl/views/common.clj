(ns shorturl.views.common
  (:use noir.core
        noir.request
        hiccup.core
        hiccup.page-helpers))

(defn active_class [url_match]
  (when (re-matches url_match (:uri (ring-request))) "active")) 

(defpartial layout [& content]
  (html5
   [:head
    [:title "URL Shortener"]
    (include-css "/css/bootstrap.css")
    (include-css "/css/application.css")
    ;(include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js")
    ;(include-js "/js/application.js")
    ]
   [:body
    [:div.topbar
     [:div.fill
      [:div.container
       [:a.brand {:href "/"} "Short URL"] 
       [:ul.nav
        [:li {:class (active_class #"^/$")} (link-to "/" "Home")]
        [:li {:class (active_class #".*urls.*")} (link-to "/urls" "Links")]
        ]]]]
    [:div#wrapper.container
     [:div.content content]]
    [:footer
     "Built by "
     (link-to "http://www.dermotbrennan.net" "Dermot Brennan")
     ]]))
