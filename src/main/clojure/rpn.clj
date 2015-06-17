(ns rpn
   (:gen-class :methods [#^{:static true} [compileString [String] void]])
)

(def DIGIT_PATTERN (re-pattern "^[0-9]+$"))
(def SYMBOL_PATTERN (re-pattern "^[a-zA-Z]+"))

(defn ^:private tokenizeDigit [c is tok]
  (cond
    (some #{c} (re-matches DIGIT_PATTERN (str c))) (recur (char (.read is)) is (str tok c))
    :else {"c" c "token" tok }))

;TODO see if we can pass in a matcher instead of one method per concern
(defn ^:private tokenizeSymbol [c is tok]
  (cond
    (some #{c} (re-matches SYMBOL_PATTERN (str c))) (recur (char (.read is)) is (str tok c))
    :else {"c" c "token" tok }))

;TODO broken, needs to deal with returned 'c' value as well as collecting tokens
(defn ^:private tokenize [c is]
  (cond
    ; Digits 0..9
    (some #{c} (re-matches DIGIT_PATTERN (str c))) (println (tokenizeDigit c is ""))
    ; Alpha characters a..z
    (some #{c} (re-matches SYMBOL_PATTERN (str c))) (println (tokenizeSymbol c is ""))
    (some #{c} #{\ }) (println "<space>")
    :else (throw (Exception. (.concat "Unknown token in stream " (.toString c))))))


(defn ^:private lex [is] 
  (let [byteRead (.read is) ]
    (cond 
      (< byteRead 0) '() 
      :else ( do (tokenize (char byteRead) is) (recur is))))) 


(defn ^:private parse [tokens]
  )

(defn -compileString [theString]
  (lex (new java.io.ByteArrayInputStream (. theString getBytes))))
