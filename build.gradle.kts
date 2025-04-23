import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
}

allprojects {
    group = "com.coindex.applovin.kmp"
    version = "0.0.1-beta01"

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "maven"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = gradleLocalProperties(rootDir, providers).getProperty("sonatypeUsername")
                    password = gradleLocalProperties(rootDir, providers).getProperty("sonatypePassword")
                }
            }
        }

        publications {
            withType<MavenPublication> {

                pom {
                    name.set("Basic")
                    description.set("Easily integrate AppLovin into your Kotlin Multiplatform Mobile (KMP / KMM) project")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://raw.githubusercontent.com/hoanghiephui/AppLovin-for-KMP/refs/heads/main/LICENSE")
                        }
                    }
                    url.set("https://github.com/hoanghiephui/AppLovin-for-KMP")
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/hoanghiephui/AppLovin-for-KMP/issues")
                    }
                    scm {
                        connection.set("https://github.com/hoanghiephui/AppLovin-for-KMP.git")
                        url.set("https://github.com/hoanghiephui/AppLovin-for-KMP")
                    }
                    developers {
                        developer {
                            id.set("hoanghiephui")
                            name.set("Hoang Hiep")
                            email.set("hoanghiep.hui@gmail.com")
                            url.set("https://coindex.money")
                        }
                    }
                }
            }
        }
    }

    val publishing = extensions.getByType<PublishingExtension>()

    if (gradle.startParameter.taskNames.any { it == "publish" }) {
        extensions.configure<SigningExtension> {
            useInMemoryPgpKeys(
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeyId"),
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeySecret"),
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeyPassword")
            )
            sign(publishing.publications)
        }
    } else {
        extensions.configure<SigningExtension> {
            useGpgCmd()
            sign(publishing.publications)
        }
    }

    // remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
    project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        dependsOn(project.tasks.withType(Sign::class.java))
    }
}