package io.github.tarek360.rules.coverage

data class ClassCoverage constructor(val classPackage: String,
                         val className: String,
                         val classPath: String,
                         val coveredBranches: Int)
