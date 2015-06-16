(ns rpn
   (:gen-class :methods [#^{:static true} [compileString [String] void]])
)

(defn ^:private tokenizeDigit [c is tok]
  (cond
    (some #{c} (map char (range (int \0) (int \9)))) (recur (char (.read is)) is (str tok c))
    :else {"c" c "token" tok }))

(defn ^:private tokenize [c is]
  (cond
    ; Digits 0..9
    (some #{c} (map char (range (int \0) (int \9)))) (println (tokenizeDigit c is ""))
    ; Alpha characters a..z
    (some #{c} (map char (range (int \a) (int \z)))) (println "char: " c) 
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
