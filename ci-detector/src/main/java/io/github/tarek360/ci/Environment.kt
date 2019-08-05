package io.github.tarek360.ci

class Environment {
    companion object {
        fun getVariable(name: String): String? {
            return try {
                val value = System.getenv(name)
                if (value == null)
                    printError("Value for [$name] Environment Variable is null!")
                value
            } catch (e: Exception) {
                printError("Can't getVariable for [$name], Exception: ${e.javaClass.name}: ${e.message}")
                e.printStackTrace()
                null
            }
        }

        private fun printError(msg: String) {
            val ansiRed = "\u001B[31m"
            println("$ansiRed$msg$ansiRed")
        }
    }
}
