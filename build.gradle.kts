plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.13.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "xyz.jxmm"
version = "0.6.1"

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
        maven("com.google.zxing")
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.json:json:20220924")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.alibaba:fastjson:1.2.83")
    implementation("xyz.jxmm:MinecraftInfo:1.1")

    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.4.1")

}


