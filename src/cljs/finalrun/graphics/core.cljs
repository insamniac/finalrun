(ns finalrun.graphics.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
			[finalrun.graphics.dudes :as dudes]
			[finalrun.graphics.world :as world]))


(def board { :w 1536 :h 1020 })

(defn setup []
  (println "setting up quil.")
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  (let [ state {:tick 1
                :board board
                :world-map (update world/init-map :img q/load-image)
                :dude (-> dudes/init-dude
                          (update-in [:images :up]  #(map q/load-image %))
                          (update-in [:images :down] #(map q/load-image %))
                          (update-in [:images :left] #(map q/load-image %))
                          (update-in [:images :right] #(map q/load-image %))
						  (dudes/set-image))}]

                (println "done setting up.")
                state))


(defn update-state [state]
     (-> state
	   (update :tick inc)
       (update-in [:dude :tick] inc)
       (update :dude dudes/do-movement)
       (update :world-map world/scroll)
       world/check-bounds
       (update :dude dudes/check-moving)))


(defn draw-image [obj]
    (let [ {:keys [x y w h img]} obj]
     (if (> (.-width img) 0) (q/image img x y w h))))

(defn draw-dude [state]
	(let [wmap (:world-map state)
		  dude (-> (:dude state)
				   (update :x + (:x wmap))
				   (update :y + (:y wmap)))]
		(draw-image dude)))

  (defn draw-state [state]
		(-> state :world-map draw-image)
		(-> state draw-dude))


;    	(if (> (.-width img) 0) (q/image img x y w h)))
;	(let [ {{:keys [x y w h alt direction images]} :dude} state
;          img (nth (direction  images) alt)]
;  	(if (> (.-width img) 0) (q/image img x y w h))))


(defn on-key-down [state event]
  (let  [dmap
   {:up    [:up :y -1]
    :w     [:up :y -1]
    :down  [:down :y 1]
    :s     [:down :y 1]
    :left  [:left :x -1]
    :a     [:left :x -1]
    :right [:right :x 1]
    :d     [:right :x 1]}
	dvec   (-> event :key dmap)
    [d k v] dvec]
 	(if (or (-> state :dude :moving?)
            (-> state :dude :moves seq some?))
        ;(-> state (assoc-in  [:dude :direction] d))
		state
 	    (cond (nil? d) state
		   :otherwise
			(-> state
			 (assoc-in [:dude :moving?] true)
			 (assoc-in [:dude :direction] d))))))

(defn on-key-up [state]
  (assoc-in state [:dude :moving?] false))

(comment  (if (contains? #{:left :right :a :d}
                 (q/key-as-keyword))
  (println "key-up")))


(defn play-theme []
  (let [player (.getElementById js/document "aplayer")]
    (.log js/console  player)))

(defn first-sketch []
(q/defsketch canvas1
  :host "canvas1"
  :size [(:w board) (:h board)]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :key-pressed on-key-down
  :key-released on-key-up
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode]))

