(ns src.example.functional-programming)
;bad idea about Fibonaccis
(defn stack-consuming-fibo [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (stack-consuming-fibo (- n 1))
             (stack-consuming-fibo (- n 2)))))
;This is easyly cause StackOverFlow Error


;tail recursion
(defn tail-fibo [n]
  (letfn [(fib
            [current next n]
            (if (zero? n)
              current
              (fib next (+ current next) (dec n))))]
    (fib 0N 1N n)))
;This is better, but also cause StackOVerFlow Error when n is too big


;self-recursion
(defn recur-fibo [n]
  (letfn [(fib
            [current next n]
            (if (zero? n)
              current
              (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))
;This is way better


;lazy-seq Fibonaccis
(defn lazy-seq-fibo
  ([]
   (concat [0 1] (lazy-seq-fibo 0N 1N)))
  ([a b]
   (let [n (+ a b)]
     (lazy-seq
       (cons n (lazy-seq-fibo b n))))))


;build on map and iterator
(defn fibo []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0N 1N])))
;(println (nth (fibo) 1000000))



; Fuctional Programming
;Coute toss coin result for heads pairs, :h is for Head :t is for Tail.
;Write all you code by yourself.
(defn count-heads-pairs [coll]
  (loop [cnt 0 coll coll]
    (if (empty? coll)
      cnt
      (recur (if (= :h (first coll) (second coll))
               (inc cnt)
               cnt)
             (rest coll)))))


;Use clojure seq-libraries
(defn by-pair [coll]
  (let [take-pairs (fn [c]
                     (when (next c) (take 2 c)))]
    (lazy-seq
      (when-let [pair (seq (take-pairs coll))]
        (cons pair (by-pair (rest coll)))))))
(defn count-heads-pairs2 [coll]
  (count (filter (fn [pair] (every? #(= :h %) pair))
                 (by-pair [coll]))))

;Clojure already hava some method can do by-pair thing called partition
(def ^{:doc "Count items matching a filter"}
   count-if (comp count filter))

(defn count-runs
  "Count runs of length n where pred is true in coll"
  [n pred coll]
  (count-if #(every? pred %) (partition n 1 coll)))

(def ^{:doc "Count runs of length two that are both heads"}
 count-head-pairs3 (partial count-runs 2 #(= % :h)))



