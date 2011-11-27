(ns shorturl.views.urls
  (:require [shorturl.views.common :as common]
            [shorturl.models.url :as url_model]
            [shorturl.models.url_form :as url_form]
            [noir.response :as resp]
            [noir.validation :as vali]
            )
  (:use noir.core
        noir.request
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers
        [shorturl.middleware :only (log)]))

(defpage "/:shorturl" {:keys [shorturl]}
  (if-let [url_obj (url_model/find_by_shortened shorturl)] 
    (do
     (url_model/inc_views url_obj)
     (resp/redirect (:original url_obj)))
    (common/layout [:h1 "/" shorturl " not found."]))
  )

(defn pluralize [num word]
  (str num " " (if (= num 1) word (str word "s"))))

(defpage "/urls" []
  (def urls (url_model/all) )
  (common/layout
   [:h1 "Urls"]
   [:div {:id "urls"}
    (map (fn [url] [:div {:class "url"}
                   (let [url-to (str "http://" (:server-name (ring-request)) "/" (:shortened url))]
                     [:h2 (link-to url-to url-to)])
                   [:p "=> "
                    (link-to (:original url) (:original url))
                    " - " (pluralize (:views url) "view")]
                   ])
            urls)]
   (link-to "/urls/new" "Create new url") 
   )
  )

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial url_fields [{:keys [original shortened]}]
  (vali/on-error :original error-item)
  (vali/on-error :shortened error-item)
  [:p (label "original" "URL:")
      (text-field "original" original)]
  [:p (label "shortened" "Name")
      (text-field "shortened" shortened)])

(defpartial url_form [{:as url}] 
  (form-to [:post "/urls/new"]
           (url_fields url)
           (submit-button "SHORTEN")))

(defpage "/urls/new" {:as url}
  (common/layout
   [:h1 "Create new Shortened URL"]
   (url_form url)
   ))

(defpage [:post "/urls/new"] {:as url}
  (if (url_form/valid? url)
    (do (url_model/create url)
        (common/layout [:p "URL added!"]
                       [:p "Your shortened url is: "
                        (format "%s/%s"
                                (:server-name ring-request)
                                (:shortened url) )]
                       [:p (link-to "/urls/new" "Shorten another url")]))
    (do
      (common/layout
       [:h2 "Create new Shortened URL"]
       (url_form url))
     )))


