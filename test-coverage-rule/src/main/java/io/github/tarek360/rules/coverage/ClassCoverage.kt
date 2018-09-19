package io.github.tarek360.rules.coverage

data class ClassCoverage(val classPackage: String,
                         val fileName: String,
                         val filePath: String,
                         val coveredBranches: Int)
