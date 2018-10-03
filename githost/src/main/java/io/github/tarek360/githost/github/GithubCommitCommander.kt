package io.github.tarek360.githost.github

import io.github.tarek360.core.cl.Commander
import io.github.tarek360.githost.GitHostInfo

class GithubCommitCommander(private val commander: Commander, private val gitHostInfo: GitHostInfo) {

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
    commander.executeCL(
        "git remote add origin-pages https://${gitHostInfo.token}@github.com/${gitHostInfo.projectId}.git > /dev/null 2>&1")
    commander.executeCL("git push --quiet --set-upstream origin-pages $branchName")
  }

}
