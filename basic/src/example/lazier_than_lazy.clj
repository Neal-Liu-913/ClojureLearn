(ns src.example.lazier-than-lazy)
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
(println (count-heads-pairs2 [:h :h :h :h]))