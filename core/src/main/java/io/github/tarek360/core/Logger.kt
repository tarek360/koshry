package io.github.tarek360.core


val logger: Logger = Logger()

class Logger {

  val debugable = true

  val ANSI_RED = "\u001B[31m"
  val ANSI_YELLOW = "\u001B[33m"
  val ANSI_WHITE = "\u001B[37m"
  val ANSI_GREEN = "\u001B[32m"
  val ANSI_RESET = "\u001B[0m"

  fun d(msg: () -> String?) {
    if (debugable) {
      println("$ANSI_WHITE${msg()}$ANSI_RESET")
    }
  }

  fun e(msg: () -> String?) {
    println("$ANSI_RED${msg()}$ANSI_RESET")
  }

  fun w(msg: () -> String?) {
    println("$ANSI_YELLOW${msg()}$ANSI_RESET")
  }

  fun i(msg: () -> String?) {
    println("$ANSI_GREEN${msg()}$ANSI_RESET")
  }

}
