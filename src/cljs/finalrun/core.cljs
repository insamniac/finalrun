(ns finalrun.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [finalrun.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [finalrun.graphics.core :as g])
  (:import goog.History))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:span "what the hell am I doing"]
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(defn what-page []
  [:div.container
   (when-let [docs (session/get :docs)]
     [:div.row>div.col-sm-12
      [:div {:dangerouslySetInnerHTML
             {:__html (md->html docs)}}]])])

(defn home-page []
  [:div.container
      [:canvas#canvas1 {:style {:position "absolute" :left 10}}]
      (g/first-sketch)])

(def pages
  {:home #'home-page
   :about #'about-page
   :what #'what-page})

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :page :home))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(session/put! :docs %)}))

(defn mount-components []
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
