import com.varabyte.kobweb.gradle.application.BuildTarget
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.kobwebx.markdown)
}
group = "naser09.github.io"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
        extra.set("kobwebBuildTarget",BuildTarget.RELEASE)
    }
}
kotlin {
    configAsKobwebApplication("io")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
        }

        jsMain.dependencies {
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            //implementation(libs.silk.icons.fa)
            implementation(libs.kontinx.serialization)
            // implementation(libs.kobwebx.markdown)
            
        }
    }
}
