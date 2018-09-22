package io.github.tarek360.rules.core

import io.github.tarek360.gitdiff.GitDiff

abstract class Rule {

    abstract fun run(): Report?

    lateinit var gitDiff: GitDiff
        private set

    fun init(gitDiff: GitDiff) {
        this.gitDiff = gitDiff
    }
}

@DslMarker
annotation class RuleDsl
