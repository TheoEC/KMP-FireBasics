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
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
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
            baseName = "firestore"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.14.0"))
            implementation(libs.firebase.firestore)
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

//publishing {
//    publications {
//        withType<MavenPublication>().all {
//            groupId = "com.github.TheoEC.KMP-FireBasics"
//            version = "1.0.2"
//        }
//    }
//
//    repositories {
//        maven {
//            name = "GitHub"
//            url = uri("https://maven.pkg.github.com/TheoEC/KMP-FireBasics")
////            credentials {
////                username = project.findProperty("gpr.user") as String? ?: ""
////                password = project.findProperty("gpr.key") as String? ?: ""
////            }
//        }
//    }
//}

// <module directory>/build.gradle.kts

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("io.github.theoec", "kmp-firebasics-firestore", "1.0.0")

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