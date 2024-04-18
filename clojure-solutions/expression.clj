(defn operate-between [f]
  (fn [& args]
  (fn [varSet]
    (apply f (mapv (fn [e] (e varSet)) args)))))

(def add (operate-between +))
(def subtract (operate-between -))
(def multiply (operate-between *))
(def divide (operate-between (fn [a b] (/ (double a) (double b)))))
(def negate (operate-between -))

(def constant constantly)
(defn variable [var]
  (fn [varSet] (varSet var)))


(def opMap {'+      add
            '-      subtract
            '*      multiply
            '/      divide
            'negate negate})

(defn makeExpression [token]
  (cond
    (list? token) (apply (get opMap (first token)) (mapv makeExpression (rest token)))
    (symbol? token) (variable (str token))
    :else (constant token)))

(defn parseFunction [expr]
  (makeExpression (read-string expr)))
