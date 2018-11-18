import java.io.BufferedReader

tasks.create<DefaultTask>("release") {

    val releaseBranch = "master"
    val currentBranch = executeCommand("git rev-parse --abbrev-ref HEAD")[0]

    val bintrayUploadTasks = subprojects.mapNotNull {
        val tasks = it.getTasksByName("bintrayUpload", false)
        if (tasks.isNotEmpty()) {
            tasks
        } else {
            null
        }
    }

    if (currentBranch == releaseBranch) {
        finalizedBy(bintrayUploadTasks)
    }

    doFirst {
        if (currentBranch == releaseBranch) {
            println("Releasing from master branch!")
        } else {
            println("No master! No release!")
        }
    }
}


fun executeCommand(command: String): List<String> {
    val proc = Runtime.getRuntime().exec(command)
    val lines = proc.inputStream.bufferedReader().use(BufferedReader::readLines)
    proc.waitFor()
    return lines
}
