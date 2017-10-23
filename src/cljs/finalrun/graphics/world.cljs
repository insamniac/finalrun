(ns finalrun.graphics.world)


;inverted because the ground goes opposite
(def dir-map
  {:up [:y 1]
   :down [:y -1]
   :left [:x 1]
   :right [:x -1]})

(defn print-obj-coords [obj]
  (let [{:keys [x y id]} obj]
    (println {:name id :x x :y y})
    obj))

(defn scroll [wmap]
 (let [m (first (:moves wmap))]
   (cond (some? m)
            (let [k (first m) v (second m)]
            (-> wmap
            (update  k + v)
            (update :moves rest)))
         :otherwise wmap)))

(defn check-horiz [state bw mx dx]
     (cond (> (+ dx mx) (* bw 0.75))
		   (let [frames 36 dist -8 k :x]
	 		(assoc-in state [:world-map :moves] (repeat frames [k dist])))
	       (< (+ dx mx) (* bw 0.25))
		   (let [frames 36 dist 8 k :x]
	 		(assoc-in state [:world-map :moves] (repeat frames [k dist])))
			:otherwise state))

(defn check-vert [state bh my dy]
     (cond (> (+ dy my) (* bh 0.75))
		   (let [frames 36 dist -8 k :y]
	 		(assoc-in state [:world-map :moves] (repeat frames [k dist])))
	       (< (+ dy my)  (* bh 0.25))
		   (let [frames 36 dist 8 k :y]
	 		(assoc-in state [:world-map :moves] (repeat frames [k dist])))
			:otherwise state))

(defn check-bounds [state]
  "should return updated world-map"
  (let [bw (:w (:board state))
        bh (:h (:board state))
        mx (:x (:world-map state))
        my (:y (:world-map state))
        dx (:x (:dude state))
        dy (:y (:dude state))]
        (-> state
          (check-horiz bw mx dx)
          (check-vert  bh my dy))))





(def init-map { :id "world-map"
               :x -10184
               :y -9920
               :w 16384
               :h 16320
               :img "/img/maps/ff1_overworld.png" :moves []})


