package io.github.tarek360.rules.test

import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.gitdiff.Line

class TestGitFile(override val path: String,
                  override val type: GitFile.Type,
                  override val addedLines: List<Line>,
                  override val deletedLines: List<Line>
) : GitFile
