package br.com.pdt.cargojunit

import org.codehaus.cargo.container.deployable.WAR
import org.codehaus.cargo.container.installer.ZipURLInstaller
import org.codehaus.cargo.container.jboss.JBoss75xInstalledLocalContainer
import org.codehaus.cargo.container.jboss.JBoss75xStandaloneLocalConfiguration
import java.io.File
import java.net.URL
import java.nio.file.Paths

object CargoManager {

    val executionDir: File = Paths.get("").toAbsolutePath().toFile()
    val containerSource = URL("file:////Volumes/D/Users/shino/Development/Java/Servers/jboss-eap-6.4.0.zip")

    val container = JBoss75xInstalledLocalContainer(
        JBoss75xStandaloneLocalConfiguration(File(executionDir, "target/cargo-container-config").toString())
    )

    fun setupContainer(): Unit {
        ZipURLInstaller(containerSource, "target", "target").install()
        container.timeout = 15000
        container.home = File(executionDir, "target/" + containerSource.file.split('/').last().dropLast(4)).toString()
    }

    fun startContainer(): Unit {
        setupContainer()
        container.start()
    }

    fun stopContainer(): Unit = container.stop()

    fun deployApp(): Unit = container.configuration.addDeployable(
        WAR("target/cargo-junit-webapp.war").apply { context = "app" }
    )

}
