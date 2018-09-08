package io.github.tarek360.ci

class Environment {
    companion object {
        fun getVariable(name: String): String? = System.getenv(name)
    }
}