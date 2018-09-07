package io.github.tarek360.gitdiffprovider

import io.github.tarek360.gitdiffprovider.di.Injection
import io.github.tarek360.gitdiffprovider.di.InjectionImpl

val injection: Injection = InjectionImpl()

class GitCommanderProvider {

  companion object {
    fun provide(sha1: String, sha2: String): GitCommander {
      return GitDiffCommander(injection.commander, sha1, sha2)
    }
  }

}
