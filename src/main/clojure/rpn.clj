(ns rpn
   (:gen-class :methods [#^{:static true} [compileString [String] void]])
)

(def DIGIT_PATTERN (re-pattern "^[0-9]+$"))
(def SYMBOL_PATTERN (re-pattern "^[a-zA-Z]+$"))
(def OPERATOR_PATTERN (re-pattern "[-+*/]{1}"))
(def WHITESPACE_PATTERN (re-pattern "\\s*"))

(defn ^:private tokenizePattern [b is tok pattern]
  (if (= b -1)
    {"b" -1 "token" tok}
    (let [c (char b)]
      (cond
        (some #{c} (re-matches pattern (str (char c)))) (recur (.read is) is (str tok (char c)) pattern)
        :else {"b" c "token" tok }))))

;TODO broken, needs to deal with whitespace correctly
(defn ^:private tokenize [b is tokens]
  (let [c (char b) ]
    (let [result (cond
                    (some #{c} (re-matches DIGIT_PATTERN (str c))) (tokenizePattern b is "" DIGIT_PATTERN)
                    (some #{c} (re-matches SYMBOL_PATTERN (str c))) (tokenizePattern b is "" SYMBOL_PATTERN)
                    (some #{c} (re-matches OPERATOR_PATTERN (str c))) (tokenizePattern b is "" OPERATOR_PATTERN)
                    (some #{c} (re-matches WHITESPACE_PATTERN (str c))) (tokenizePattern b is "" WHITESPACE_PATTERN)
                    :else (throw (Exception. (.concat "Unknown token in stream " (.toString c)))))]
      (if (= -1 (get result "b"))
        (flatten (list tokens (get result "token")))
        (if (re-matches WHITESPACE_PATTERN (get result "token"))
          (recur (get result "b") is tokens)
          (recur (get result "b") is (flatten (list tokens (get result "token" )))))))))


(defn ^:private lex [is] 
  (let [byteRead (.read is) ]
    (cond 
      (< byteRead 0) '() 
      :else 
        (let [tokens (tokenize byteRead is '())] 
          (do (println tokens) (println (count tokens) ) (recur is))))))


(defn ^:private parse [tokens]
  )

(defn -compileString [theString]
  (lex (new java.io.ByteArrayInputStream (. theString getBytes))))
