;; mypage.bb.clj
;; <img src="http://localhost:3001/display?w=1000&path=2023-12-03.kra.png&op=resize" />
(ns hyloink
  (:require [babashka.fs :as fs]
            [hiccup2.core :as h]))

(def imgs (->> (fs/glob "./export" "*.png" {:follow-links true})
               (map fs/file-name)
               sort
               reverse))

(defn img [s]
  [:figure
   [:a {:href (format "/art/api/fullhd/%s" s)}
    [:img.mx-auto.transition.ease-in-out {:class "hover_scale-105" :src (format "/art/api/thumbnail/%s" s)}]]
   [:figcaption.text-center.pt-2
    (fs/strip-ext s {:ext "kra.png"})]])


(defn menu-item [{:keys [active? name link]}]
  (let [base "text-3xl font-semibold tracking-wide hover_underline decoration-sky-400 decoration-4 hover_text-slate-900 transition"
        active "underline"]
   [:li {:class (format "%s %s" base (if active? active ""))}
    [:a {:href link} name]]))

(defrecord Page [name link filename content])

(def page-home (Page. "Home" "/" "index" [:div]))
(def page-daily (Page.
                 "Daily"
                 "/daily"
                 "daily"
                  [:div.grid.grid-cols-3.gap-8.mx-auto {:class "max-w-[80ch]"}
                                  (map img imgs)]))
(def page-about (Page. "About" "/about" "about" [:div]))

(def pages [page-home page-daily page-about])

(defn menu [current-page]
         [:nav.bg-gradient-to-b.from-sky-100.h-32.flex
          [:ol.flex.justify-center.w-full.items-baseline.justify-evenly.mt-12.max-w-prose.mx-auto
           (for [page pages]
             (menu-item {:active? (= (:name current-page) (:name page))
                         :link (:link page)
                         :name (:name page)}))]])
                         



(defn gen [page]
  (str (h/html
        [:head
         [:meta {:charset "utf-8"}]
         [:link {:href "tailwind.css" :rel "stylesheet"}]
         [:script {:src "./live-reload.js"}]
         [:title (format "hylo ∘ ink ⸺ %s" (:name page))]]

        [:body.text-slate-700
         (menu page)
         [:main
          (:content page)
          [:div]]])))

(doseq [page pages]
  (spit (format "web/%s.html" (:filename page)) (gen page)))
