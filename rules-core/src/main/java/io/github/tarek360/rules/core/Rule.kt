package io.github.tarek360.rules.core

import io.github.tarek360.gitdiff.GitDiff

interface Rule {
  fun apply(gitDiff: GitDiff): Report?
}

@DslMarker
annotation class RuleDsl
