package io.github.tarek360.koshry

import io.github.tarek360.rules.Rule
import io.github.tarek360.rules.RuleDsl

fun koshry(block: KoshryBuilder.() -> Unit): KoshryConfig = KoshryBuilder().apply(block).build()

@DslMarker
annotation class KoshryDsl

@KoshryDsl
class KoshryBuilder {

  private val rules = ArrayList<Rule>()

  fun rules(block: RULES.() -> Unit) {
    rules.addAll(RULES().apply(block).build())
  }

  fun build(): KoshryConfig = KoshryConfig(rules)
}


@RuleDsl
@KoshryDsl
class RULES {
  private val rules = mutableListOf<Rule>()

  private var tmpRule: Rule? = null

  var rule: Rule
    get() = tmpRule ?: throw UninitializedPropertyAccessException("property \"rule\" has not been initialized")
    set(value) {
      tmpRule = value
      rules.add(value)
    }

  fun build(): List<Rule> = rules
}
