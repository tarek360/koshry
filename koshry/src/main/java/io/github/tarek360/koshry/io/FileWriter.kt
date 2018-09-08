package io.github.tarek360.koshry.io

import java.io.File

class FileWriter {

    fun writeToFile(filePath: String, data: String) {
        val file = File(filePath)
        file.parentFile.mkdirs()
        file.bufferedWriter().use { out ->
            out.write(data)
        }
    }
}