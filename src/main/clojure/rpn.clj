(ns rpn
   (:gen-class :methods [#^{:static true} [compileString [String] void]])
   ;(:gen-class :methods [#^{:static true} [doit [InputStream] void]])
)


(defn -lex [is] 
  (let [byteRead (.read is) ]
    (cond 
      (< byteRead 0) '() 
      :else ( do (println (char byteRead)) (recur is))
    )
  )
)

(defn -compileString [theString]
  (-lex (new java.io.ByteArrayInputStream (. theString getBytes)))
)
