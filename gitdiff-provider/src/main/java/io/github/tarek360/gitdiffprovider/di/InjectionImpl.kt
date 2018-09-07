package io.github.tarek360.gitdiffprovider.di

import io.github.tarek360.core.cl.Commander
import io.github.tarek360.core.cl.CommanderImpl

open class InjectionImpl : Injection {

  override val commander: Commander by lazy { CommanderImpl() }

}
