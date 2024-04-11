(defn operation [f]
  (fn [& args]
    (apply mapv f args)))

(defn *s [f] (fn [o s] (mapv (fn [e] (f e s)) o)))

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

(def v*s (*s *))

(def m+ (operation v+))
(def m- (operation v-))
(def m* (operation v*))
(def md (operation vd))

(def m*s (*s v*s))

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
