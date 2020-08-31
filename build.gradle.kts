plugins {
    kotlin("jvm") version "1.4.0"
    application
}

group = "online.ruin_of_future"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:1.4.0")
    implementation("io.ktor:ktor-server-netty:1.4.0")
}


application {
    mainClassName = "online.ruin_of_future.pyhttpkt.MainKt"
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

distributions {
    main {
        contents {
            from("src/py")
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

