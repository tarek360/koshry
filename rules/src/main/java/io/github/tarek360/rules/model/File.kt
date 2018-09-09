package io.github.tarek360.rules.model

import io.github.tarek360.gitdiff.GitFile

class File(gitFile: GitFile) {

    val name: String by lazy {
        gitFile.path.substringAfterLast('/')
    }

    val path: String = gitFile.path

    val isAdded: Boolean = gitFile.type == GitFile.Type.ADDED

    val isModified: Boolean = gitFile.type == GitFile.Type.MODIFIED

    val isDeleted: Boolean = gitFile.type == GitFile.Type.DELETED
}
