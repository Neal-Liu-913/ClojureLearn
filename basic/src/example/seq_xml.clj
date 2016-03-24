(ns src.example.seq-xml
  (:import (java.io File)))
(use '[clojure.xml :only (parse)])
(println (parse (File. "basic/src/example/composition.xml")))
(println
  (for [x (xml-seq
            (parse (File. "basic/src/example/composition.xml")))
        :when (= :composition (:tag x))]
    (:composer (:attrs x))))