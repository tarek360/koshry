package io.github.tarek360.rules.core

data class Report(val msgTitle: String = "",
                  val descTitle: String = "",
                  val issues: ArrayList<Issue> = arrayListOf())
