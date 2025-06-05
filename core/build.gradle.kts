import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.30.0"
    signing
}

kotlin {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }

    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "core"
            isStatic = true
        }
//            noPodspec()
//            pod("FirebaseCore") {
//                version = "11.14.0"
//                extraOpts += listOf("-compiler-option", "-fmodules")
//            }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.14.0"))
            implementation(libs.firebase.common)
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.theoec.kmpfirebasics"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("io.github.theoec", "kmp-firebasics-core", "1.0.0")

    pom {
        name = "KMP Firebasics"
        description = "Kotlin Multiplatform Firebase tools"
        url = "https://github.com/TheoEC/KMP-FireBasics"
        licenses {
            license {
                name = "Apache License 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "theoec"
                name = "Theo Moura"
            }
        }
        scm {
            url = "https://github.com/TheoEC/KMP-FireBasics"
            connection = "scm:git:git://github.com/TheoEC/KMP-FireBasics.git"
            developerConnection = "scm:git:ssh://git@github.com/TheoEC/KMP-FireBasics.git"
        }
    }
}

signing {
    val signingInMemoryKey: String by project
    val signingInMemoryKeyPassword: String by project
    useInMemoryPgpKeys(signingInMemoryKey, signingInMemoryKeyPassword)
    sign(publishing.publications)
}