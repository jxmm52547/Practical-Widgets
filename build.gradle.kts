plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.13.0"
}

group = "xyz.jxmm"
version = "0.6.0"

allprojects {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            credentials {
                username = "63fd92aa69c5d5aa17c50aae"
                password = "j1I-dlBgXNPc"
            }
            url = uri("https://packages.aliyun.com/maven/repository/2337074-release-f5MMk7/")
        }
        maven {
            credentials {
                username = "63fd92aa69c5d5aa17c50aae"
                password = "j1I-dlBgXNPc"
            }
            url = uri("https://packages.aliyun.com/maven/repository/2337074-snapshot-aCdbmF/")
        }
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/apache-snapshots")
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.json:json:20220924")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    api("com.alibaba:fastjson:1.2.83")
    implementation("xyz.jxmm:MinecraftInfo:1.1")

}

