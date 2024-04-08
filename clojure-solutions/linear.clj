(defn operation [f]
  (fn [& args]
    (apply mapv f args)))

(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def vd (operation /))

(defn scalar [v s]
  (reduce + (v* v s)))

(defn vect [[ax ay az] [bx by bz]]
  (vector
    (- (* ay bz) (* az by))
    (- (* az bx) (* ax bz))
    (- (* ax by) (* ay bx))))

(defn v*s [v s]
  (mapv (fn [e] (* e s)) v))

(def m+ (operation v+))
(def m- (operation v-))
(def m* (operation v*))
(def md (operation vd))

(defn m*s [m s]
  (mapv (fn [v] (v*s v s)) m))

(defn m*v [m v]
  (mapv (partial scalar v) m))

(defn transpose [m]
  (apply mapv vector m))

(defn m*m [m1 m2]
  (mapv (partial m*v (transpose m2)) m1))

(def c+ (operation m+))
(def c- (operation m-))
(def c* (operation m*))
(def cd (operation md))
