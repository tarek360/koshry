package io.github.tarek360.koshry.url

interface FileUrlGenerator {
  fun generate(filePath: String, lineNumber: Int?): String
}