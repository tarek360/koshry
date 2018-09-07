package io.github.tarek360.core.cl

interface Commander {
    /**
     * Execute a command line and it's optional to save the output in a file
     * @param command line to execute
     * @return a list of lines of the execution output or empty list if no output
     */
    fun executeCL(command: String): List<String>

    /**
     * Execute a shell script file
     * @param filePath shell script file path to execute the inside script
     */
    fun executeSh(filePath: String)
}
