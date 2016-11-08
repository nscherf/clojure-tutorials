(def fizz? #(zero? (mod % 3)))
(def buzz? #(zero? (mod % 5)))
(def fizzbuzz? #(and (fizz? %) (buzz? %)))

(def fizzbuzz
  #(cond
     (fizzbuzz? %) "FizzBuzz"
     (fizz? %) "Fizz"
     (buzz? %) "Buzz"
     :else %
     ))

(defn fizzbuzz [n]
  (let [s (str (if (fizz? n) "Fizz") (if (buzz? n) "Buzz"))]
    (if (empty? s) n s)
    )
  )

;;using some-fn, it creates a function that returns the first logical true value. The rest is done via short-circuiting  
(defn fizzbuzz [n]
  (let [to-words (some-fn #(when (fizzbuzz? %) "FizzBuzz")
                          #(when (fizz? %) "Fizz")
                          #(when (buzz? %) "Buzz"))]
    (or (to-words n) n))
  )

;; now this is using a lot of clojure goodies arity overloading of the function definition, lazy-sequences, deconstruction, function applications...  
(defn fizzbuzz
  ([n] (fizzbuzz n (array-map fizz? "Fizz" buzz? "Buzz")))
  ([n lookup]
   (if-let [matches (seq (keep (fn [[pred? word]] (when (pred? n) word)) lookup))]
     (apply str matches) n)))

;;test
(map fizzbuzz (range 1 101))
