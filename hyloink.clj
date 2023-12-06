;; mypage.bb.clj
;; <img src="http://localhost:3001/display?w=1000&path=2023-12-03.kra.png&op=resize" />
(ns hyloink
  (:require [babashka.fs :as fs])
  (:require [babashka.process :as p :refer [process]]
            [hiccup2.core :as h]))

(def imgs (->> (fs/glob "./export" "*.jpg" {:follow-links true})
               (map fs/file-name)
               sort
               reverse))

(defn img [s]
  [:figure
   [:a {:href (format "/art/api/fullhd/%s" s)}
    [:img.mx-auto.transition.ease-in-out {:class "hover:scale-105" :src (format "/art/api/thumbnail/%s" s)}]]
   [:figcaption.text-center.pt-2
    (fs/strip-ext s {:ext "kra.jpg"})]])


(defn menu-item [{:keys [active? name link]}]
  (let [base "text-3xl font-semibold tracking-wide hover:underline decoration-sky-400 decoration-4 hover:text-slate-900 hover:underline transition"
        active "underline"]
   [:li {:class (format "%s %s" base (if active? active nil))}
    [:a {:href link} name]]))

(defrecord Page [name link filename content])

(def page-home (Page. "Home" "/" "index" [:div]))
(def page-daily (Page.
                 "Daily"
                 "/daily"
                 "daily"
                 [:div.col-span-2.max-w-prose.mx-auto
                  [:div.grid.grid-cols-2.gap-4.m-4
                                  (map img imgs)]]))
(def page-about (Page. "About" "/about" "about" [:div]))

(def pages [page-home page-daily page-about])

(defn menu [current-page]
         [:nav.bg-gradient-to-b.from-sky-100.h-32.flex.col-span-4
          [:ol.flex.justify-center.w-full.items-baseline.justify-evenly.mt-12
           (for [page pages]
             (menu-item {:active? (= (:name current-page) (:name page))
                         :link (:link page)
                         :name (:name page)}))]])
                         



(defn gen [page]
  (str (h/html
        [:head
         [:meta {:charset "utf-8"}]
         [:script {:src "https://cdn.tailwindcss.com"}]
         [:script {:src "./live-reload.js"}]]

        [:body.text-slate-700
         (menu page)
         [:main
          (:content page)
          [:div]]])))

(doseq [page pages]
  (spit (format "%s.html" (:filename page)) (gen page)))
