import com.android.build.gradle.api.BaseVariant
import sp.gx.core.GitHub
import sp.gx.core.Markdown
import sp.gx.core.Badge
import sp.gx.core.Maven
import sp.gx.core.assemble
import sp.gx.core.camelCase
import sp.gx.core.check
import sp.gx.core.colonCase
import sp.gx.core.existing
import sp.gx.core.file
import sp.gx.core.filled
import sp.gx.core.kebabCase
import sp.gx.core.resolve

version = "0.0.1"

val maven = Maven.Artifact(
    group = "com.github.kepocnhh",
    id = rootProject.name,
)

val gh = GitHub.Repository(
    owner = "StanleyProjects",
    name = rootProject.name,
)

repositories {
    google()
    mavenCentral()
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt
    id("org.jetbrains.dokka") version Version.dokka
}

fun BaseVariant.getVersion(): String {
    check(flavorName.isEmpty())
    return when (buildType.name) {
        "debug" -> kebabCase(version.toString(), "SNAPSHOT")
        "release" -> version.toString()
        else -> error("Build type \"${buildType.name}\" is not supported!")
    }
}

fun BaseVariant.getOutputFileName(extension: String): String {
    check(extension.isNotEmpty())
    return "${kebabCase(rootProject.name, getVersion())}.$extension"
}

fun checkReadme(variant: BaseVariant) {
    task(camelCase("check", variant.name, "Readme")) {
        doLast {
            val badge = Markdown.image(
                text = "version",
                url = Badge.url(
                    label = "version",
                    message = variant.getVersion(),
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                Markdown.link("Maven", Maven.Snapshot.url(maven.group, maven.id, variant.getVersion())),
                Markdown.link("Documentation", GitHub.pages(gh.owner, gh.name).resolve("doc").resolve(variant.getVersion())),
                "implementation(\"${colonCase(maven.group, maven.id, variant.getVersion())}\")",
            )
            rootDir.resolve("README.md").check(
                expected = expected,
                report = layout.buildDirectory.file("reports/analysis/readme/${variant.name}/index.html").get().asFile,
            )
        }
    }
}

jacoco.toolVersion = Version.jacoco

fun checkCoverage(variant: BaseVariant) {
    val taskUnitTest = camelCase("test", variant.name, "UnitTest")
    val executionData = layout.buildDirectory
        .dir("outputs/unit_test_code_coverage/${variant.name}UnitTest")
        .get()
        .file("$taskUnitTest.exec")
        .asFile
    tasks.getByName<Test>(taskUnitTest) {
        doLast {
            executionData.existing().file().filled()
        }
    }
    val taskCoverageReport = task<JacocoReport>(camelCase("assemble", variant.name, "CoverageReport")) {
        dependsOn(taskUnitTest)
        reports {
            csv.required.set(false)
            html.required.set(true)
            xml.required.set(false)
        }
        sourceDirectories.setFrom(file("src/main/kotlin"))
        val dirs = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes").get().dir(variant.name))
        classDirectories.setFrom(dirs)
        executionData(executionData)
        doLast {
            val report = layout.buildDirectory
                .dir("reports/jacoco/$name/html")
                .get()
                .file("index.html")
                .asFile
            if (report.exists()) {
                println("Coverage report: ${report.absolutePath}")
            }
        }
    }
    task<JacocoCoverageVerification>(camelCase("check", variant.name, "Coverage")) {
        dependsOn(taskCoverageReport)
        violationRules {
            rule {
                limit {
                    minimum = BigDecimal(0.96)
                }
            }
        }
        classDirectories.setFrom(taskCoverageReport.classDirectories)
        executionData(taskCoverageReport.executionData)
    }
}

fun checkCodeQuality(variant: BaseVariant) {
    val configs = setOf(
        "comments",
        "common",
        "complexity",
        "coroutines",
        "empty-blocks",
        "exceptions",
        "naming",
        "performance",
        "potential-bugs",
        "style",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    setOf(
        Triple("main", variant.sourceSets.flatMap { it.kotlinDirectories }.distinctBy { it.absolutePath }, ""),
        Triple("test", files("src/test/kotlin"), "UnitTest"),
    ).forEach { (type, sources, postfix) ->
        task<io.gitlab.arturbosch.detekt.Detekt>(camelCase("check", variant.name, "CodeQuality", postfix)) {
            jvmTarget = Version.jvmTarget
            setSource(sources)
            when (type) {
                "main" -> config.setFrom(configs)
                "test" -> {
                    val test = rootDir.resolve("buildSrc/src/main/resources/detekt/config/android/test.yml")
                        .existing()
                        .file()
                        .filled()
                    config.setFrom(configs + test)
                }
                else -> error("Type \"$type\" is not supported!")
            }
            val report = layout.buildDirectory
                .dir("reports/analysis/code/quality/${variant.name}/$type/html")
                .get()
                .file("index.html")
                .asFile
            reports {
                html {
                    required.set(true)
                    outputLocation.set(report)
                }
                md.required.set(false)
                sarif.required.set(false)
                txt.required.set(false)
                xml.required.set(false)
            }
            val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>(camelCase("detekt", variant.name, postfix))
            classpath.setFrom(detektTask.classpath)
            doFirst {
                println("Analysis report: ${report.absolutePath}")
            }
        }
    }
}

fun checkDocumentation(variant: BaseVariant) {
    val configs = setOf(
        "common",
        "documentation",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    task<io.gitlab.arturbosch.detekt.Detekt>(camelCase("check", variant.name, "Documentation")) {
        jvmTarget = Version.jvmTarget
        setSource(files("src/main/kotlin"))
        config.setFrom(configs)
        val report = layout.buildDirectory
            .dir("reports/analysis/documentation/${variant.name}/html")
            .get()
            .file("index.html")
            .asFile
        reports {
            html {
                required.set(true)
                outputLocation.set(report)
            }
            md.required.set(false)
            sarif.required.set(false)
            txt.required.set(false)
            xml.required.set(false)
        }
        val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>(camelCase("detekt", variant.name))
        classpath.setFrom(detektTask.classpath)
        doFirst {
            println("Analysis report: ${report.absolutePath}")
        }
    }
}

fun assembleDocumentation(variant: BaseVariant) {
    task<org.jetbrains.dokka.gradle.DokkaTask>(camelCase("assemble", variant.name, "Documentation")) {
        outputDirectory = layout.buildDirectory.dir("documentation/${variant.name}")
        moduleName = gh.name
        moduleVersion = variant.getVersion()
        dokkaSourceSets.create(camelCase(variant.name, "main")) {
            reportUndocumented = false
            sourceLink {
                val path = "src/main/kotlin"
                localDirectory = file(path)
                remoteUrl = GitHub.url(gh.owner, gh.name).resolve("tree/${moduleVersion.get()}/lib/$path")
            }
            jdkVersion = Version.jvmTarget.toInt()
        }
        doLast {
            val index = outputDirectory
                .get()
                .file("index.html")
                .asFile
                .existing()
                .file()
                .filled()
            println("Documentation: ${index.absolutePath}")
        }
    }
}

fun assemblePom(variant: BaseVariant) {
    task(camelCase("assemble", variant.name, "Pom")) {
        doLast {
            val file = layout.buildDirectory.get()
                .dir("maven")
                .dir(variant.name)
                .file(variant.getOutputFileName("pom"))
                .asFile
            file.assemble(
                Maven.pom(
                    groupId = maven.group,
                    artifactId = maven.id,
                    version = variant.getVersion(),
                    packaging = "aar",
                ),
            )
            println("POM: ${file.absolutePath}")
        }
    }
}

fun assembleSource(variant: BaseVariant) {
    task<Jar>(camelCase("assemble", variant.name, "Source")) {
        archiveBaseName = maven.id
        archiveVersion = variant.getVersion()
        archiveClassifier = "sources"
        val sourceSets = variant.sourceSets.flatMap { it.kotlinDirectories }.distinctBy { it.absolutePath }
        from(sourceSets)
        doLast {
            println("Archive: ${archiveFile.get().asFile.absolutePath}")
        }
    }
}

android {
    namespace = "sp.ax.jc.animations"
    compileSdk = Version.Android.compileSdk

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                // https://stackoverflow.com/a/71834475/4398606
                it.configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    excludes = listOf("jdk.internal.*")
                }
            }
        }
    }

    defaultConfig {
        minSdk = Version.Android.minSdk
        manifestPlaceholders["appName"] = "@string/app_name"
    }

    buildTypes.getByName(testBuildType) {
        isTestCoverageEnabled = true
    }

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Version.Android.compose

    libraryVariants.all {
        val variant = this
        val output = variant.outputs.single()
        check(output is com.android.build.gradle.internal.api.LibraryVariantOutputImpl)
        output.outputFileName = getOutputFileName("aar")
        checkReadme(variant)
        if (buildType.name == testBuildType) {
            checkCoverage(variant)
        }
        checkCodeQuality(variant)
        checkDocumentation(variant)
        assembleDocumentation(variant)
        assemblePom(variant)
        assembleSource(variant)
//        assembleMetadata(variant) // todo
//        assembleMavenMetadata(variant) // todo
        afterEvaluate {
            tasks.getByName<JavaCompile>(camelCase("compile", variant.name, "JavaWithJavac")) {
                targetCompatibility = Version.jvmTarget
            }
            tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(camelCase("compile", variant.name, "Kotlin")) {
                kotlinOptions {
                    jvmTarget = Version.jvmTarget
                    freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
                }
            }
            tasks.getByName<JavaCompile>(camelCase("compile", variant.name, "UnitTestJavaWithJavac")) {
                targetCompatibility = Version.jvmTarget
            }
            tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(camelCase("compile", variant.name, "UnitTestKotlin")) {
                kotlinOptions.jvmTarget = Version.jvmTarget
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:${Version.Android.compose}")
    testImplementation("org.robolectric:robolectric:4.10")
    testImplementation("androidx.compose.ui:ui-test-junit4:${Version.Android.compose}")
    camelCase("test", android.testBuildType, "Implementation")("androidx.compose.ui:ui-test-manifest:${Version.Android.compose}")
}
