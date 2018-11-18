package io.github.tarek360.koshry

import io.github.tarek360.rules.core.Rule

data class KoshryConfig(val rules: List<Rule> = ArrayList(), var baseSha: String, var headSha: String)
