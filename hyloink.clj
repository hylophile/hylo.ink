;; mypage.bb.clj
;; <img src="http://localhost:3001/display?w=1000&path=2023-12-03.kra.png&op=resize" />
(ns hyloink
  (:require [babashka.fs :as fs])
  (:require [babashka.process :as p :refer [process]]
            [hiccup2.core :as h]))

(def imgs (into [] (map str (fs/list-dir "./export"))))
;; (def imgs (into [] (map str (fs/glob "/home/n/art/export" "*.png"))))
(defn img [s]
  [:a {:href s}
   [:img {:src s :width "250px"}]])

(def out (str (h/html
               [:head
                [:meta {:charset "utf-8"}]
               [:div.grid.grid-cols-2.gap-2
                (map img imgs)]
                [:script {:src "https://cdn.tailwindcss.com"}]
                [:script {:src "./live-reload.js"}]]

               [:ul
                      (for [x (range 1 9)]
                        [:li x])])))


(spit "./index.html" out)
