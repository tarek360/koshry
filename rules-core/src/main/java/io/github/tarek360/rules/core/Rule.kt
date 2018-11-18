package io.github.tarek360.rules.core

import io.github.tarek360.gitdiff.GitDiff

abstract class Rule {

    abstract fun run(): Report?

    var ci: Ci? = null
        private set

    var pullRequest: PullRequest? = null
        private set

    lateinit var gitDiff: GitDiff
        private set

    fun init(ci: Ci?, pullRequest: PullRequest?, gitDiff: GitDiff) {
        this.ci = ci
        this.pullRequest = pullRequest
        this.gitDiff = gitDiff
    }
}

@DslMarker
annotation class RuleDsl
