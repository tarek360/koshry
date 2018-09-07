package io.github.tarek360.rules

import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.rules.report.Report

interface Rule {
  fun apply(gitDiff: GitDiff): Report?
}

@DslMarker
annotation class RuleDsl
