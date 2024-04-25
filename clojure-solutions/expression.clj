; Common

(def div (fn [a b] (/ (double a) (double b))))
(def MathSin (fn [a] (Math/sin a)))
(def MathCos (fn [a] (Math/cos a)))
(def MathExp (fn [a] (Math/exp a)))
(def MathLn (fn [a] (Math/log a)))

; Functional expressions homework

(defn operation [f]
  (fn [& args]
    (fn [varSet]
      (apply f (mapv (fn [e] (e varSet)) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation div))
(def negate (operation -))

(def sin (operation MathSin))
(def cos (operation MathCos))

(def constant constantly)
(defn variable [var]
  (fn [varSet] (varSet var)))

(def fpMap {'+      add
            '-      subtract
            '*      multiply
            '/      divide
            'negate negate
            'sin    sin
            'cos    cos})

(defn makeFExpression [token]
  (cond
    (list? token) (apply (get fpMap (first token)) (mapv makeFExpression (rest token)))
    (symbol? token) (variable (str token))
    :else (constant token)))

(defn parseFunction [expr]
  (makeFExpression (read-string expr)))

; Object expressions homework

(load-file "proto.clj")

(def evaluate (method :evaluate))
(def toString (method :toString))
(def value (field :value))

(defn Expression [evaluate toString] {:evaluate evaluate :toString toString})

(def Constant (constructor
                (fn [this arg] (assoc this :value arg))
                (Expression
                  (fn [this _] (value this))
                  (fn [this] (str (value this))))))

(def Variable (constructor
                (fn [this value] (assoc this :value value))
                (Expression
                  (fn [this varSet] (varSet (value this)))
                  (fn [this] (value this)))))

(def OperationPrototype
  (Expression
    (fn [this varSet]
      (apply
        ((field :operator) this)
        (mapv
          (fn [var] (evaluate var varSet))
          ((field :value) this))))
    (fn [this]
      (str "(" ((field :sign) this) " "
           (clojure.string/join " "
                                (mapv
                                  (fn [var] (toString var))
                                  (value this))) ")"))))

(defn Operation [operator sign]
  (constructor
    (fn [this & args] (assoc this :value (vec args)))
    {:prototype OperationPrototype
     :operator  operator
     :sign      sign}))

(def Add (Operation + '+))
(def Subtract (Operation - '-))
(def Multiply (Operation * '*))
(def Divide (Operation div '/))
(def Negate (Operation - 'negate))
(def Exp (Operation MathExp 'exp))
(def Ln (Operation MathLn 'ln))

(def opMap {'+      Add
            '-      Subtract
            '*      Multiply
            '/      Divide
            'negate Negate
            'exp    Exp
            'ln     Ln})

(defn makeOExpression [token]
  (cond
    (list? token) (apply (get opMap (first token)) (mapv makeOExpression (rest token)))
    (symbol? token) (Variable (str token))
    :else (Constant token)))

(defn parseObject [expr]
  (makeOExpression (read-string expr)))
