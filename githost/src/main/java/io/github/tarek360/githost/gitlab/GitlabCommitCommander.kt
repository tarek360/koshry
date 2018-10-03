package io.github.tarek360.githost.gitlab

import io.github.tarek360.core.cl.Commander
import io.github.tarek360.core.cl.CommanderImpl
import io.github.tarek360.githost.GitHostInfo

class GitlabCommitCommander(private val commander: Commander = CommanderImpl()) {

    fun commit(filePath: String, branchName: String, commitMsg: String) {
        setupGit()
        commitFile(filePath, branchName, commitMsg)
        gitPush(branchName)
    }

    private fun setupGit() {
        commander.executeCL("git config --global user.email \"koshry@koshry.com\"")
        commander.executeCL("git config --global user.name \"louga-bot\"")
    }

    private fun commitFile(filePath: String, branchName: String, commitMsg: String) {
        commander.executeCL("git checkout -b $branchName")
        commander.executeCL("git pull origin $branchName")
        commander.executeCL("git add -- $filePath")
        commander.executeCL("git commit --message \"$commitMsg\"")
    }

    private fun gitPush(branchName: String) {
        commander.executeCL("git push --quiet --set-upstream origin-pages $branchName")
    }
}