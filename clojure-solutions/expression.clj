(defn operation [f]
  (fn [& args]
  (fn [varSet]
    (apply f (mapv (fn [e] (e varSet)) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) (double b)))))
(def negate (operation -))

(def sin (operation (fn [a] (Math/sin a))))
(def cos (operation (fn [a] (Math/cos a))))

(def constant constantly)
(defn variable [var]
  (fn [varSet] (varSet var)))


(def opMap {'+      add
            '-      subtract
            '*      multiply
            '/      divide
            'negate negate
            'sin sin
            'cos cos})

(defn makeExpression [token]
  (cond
    (list? token) (apply (get opMap (first token)) (mapv makeExpression (rest token)))
    (symbol? token) (variable (str token))
    :else (constant token)))

(defn parseFunction [expr]
  (makeExpression (read-string expr)))
