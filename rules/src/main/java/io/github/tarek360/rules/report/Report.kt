package io.github.tarek360.rules.report

data class Report(val title: String, val issues: ArrayList<Issue> = arrayListOf())
