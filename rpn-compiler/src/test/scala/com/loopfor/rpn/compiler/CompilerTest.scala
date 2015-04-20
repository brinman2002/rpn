package com.loopfor.rpn.compiler

import scala.io.Source

object CompilerTest {
  def main(args: Array[String]): Unit = {
    val lexer = Lexer()
    val parser = Parser()
    val generator = Generator()
    val optimizer = Optimizer()
    val c = (lexer.apply _) andThen (parser.apply _)
    val cg = c andThen (generator.apply _) andThen (optimizer.apply _)

    val examples = Seq(
          "1.2 + x pow y * (3.32 / 4.981 + y root (1 / x)) - ((y * 6.1) + 7.0001 + (x min y) % 4.23) - z % (t * 0.123)",
          "1 + 2 + 3",
          "(1 + 2) + 3",
          "1 + (2 + 3)",
          "1 + 2 * 3",
          "1 + 2 + 3 + 4",
          "1 + (2 * 3) + 4",
          "x min y min z"
          )

    examples foreach { s =>
      try {
        val in = Source.fromString(s).toStream
        val ast = c(in)
        println(s"$s -> $ast")
        val codes = generator(ast)
        codes foreach { c => println(c.repr) }
        println("---")
        optimizer(codes)
      } catch {
        case e: Exception => println(s"$s -> ${e.getMessage}")
      }
    }
  }
}
