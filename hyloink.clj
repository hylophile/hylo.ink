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

(def out (str (h/html
               [:head
                [:meta {:charset "utf-8"}]
                [:script {:src "https://cdn.tailwindcss.com"}]
                [:script {:src "./live-reload.js"}]]
               [:main.max-w-prose.mx-auto
                [:div.grid.grid-cols-2.gap-4.m-4
                                (map img imgs)]])))



(spit "./index.html" out)