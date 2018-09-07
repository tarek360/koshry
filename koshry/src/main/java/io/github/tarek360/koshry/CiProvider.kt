package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.ci.CircleCi
import io.github.tarek360.ci.TravisCi

class CiProvider {

  fun provide(): Ci? {
    return when {
      System.getenv("TRAVIS") == "true" -> TravisCi()
      System.getenv("CIRCLECI") == "true" -> CircleCi()
      else -> null
    }

  }


}