package io.github.tarek360.rules.coverage

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class CsvParserImpl : CsvParser {

    override fun parse(csvFilePath: String): ArrayList<ClassCoverage> {
        val classes = ArrayList<ClassCoverage>()

        try {

            val fileReader = BufferedReader(FileReader(csvFilePath))
            var notFirstLine = false
            fileReader.forEachLine {
                if (notFirstLine) {

                    val line = it.split(',')

                    val classPackage = line[1]
                    val fileName = line[2]
                    val filePath = convertToFilePath(classPackage, fileName)

                    val missedBranches = line[5].toInt()
                    val coveredBranches = line[6].toInt()

                    val coverage = if (coveredBranches == 0 && missedBranches == 0) {
                        100f
                    } else if (coveredBranches == 0) {
                        0f
                    } else {
                        coveredBranches.toFloat() / (missedBranches + coveredBranches) * 100
                    }

                    classes.add(ClassCoverage(classPackage = classPackage, fileName = fileName, filePath = filePath, coveredBranches = coverage.toInt()))
                } else {
                    notFirstLine = true
                }

            }
        } catch (e: IOException) {
            println("Reading Jacoco CSV Report Error!")
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            println("Parsing Jacoco CSV Report Error!")
            e.printStackTrace()
        }
        return classes
    }

    private fun convertToFilePath(pkg: String, className: String): String {
        val name = className.replace('.', '$')
        return "${pkg.replace('.', '/')}/$name"
    }
}
