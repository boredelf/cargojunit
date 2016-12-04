package br.com.pdt.cargojunit

import org.junit.ClassRule
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(TestA::class, TestB::class)
class TestSuite {

    companion object {
        @JvmField @ClassRule
        val resource: ExternalResource = object : ExternalResource() {
            override fun before(): Unit {
                CargoManager.deployApp()
                CargoManager.startContainer()
            }

            override fun after() = CargoManager.stopContainer()
        }
    }

}
