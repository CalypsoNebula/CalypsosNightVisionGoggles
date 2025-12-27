@file:Suppress("UnstableApiUsage", "INVISIBLE_REFERENCE")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import earth.terrarium.cloche.INCLUDE_TRANSFORMED_OUTPUT_ATTRIBUTE
import earth.terrarium.cloche.REMAPPED_ATTRIBUTE
import earth.terrarium.cloche.api.attributes.MinecraftModLoader
import earth.terrarium.cloche.api.attributes.TargetAttributes
import earth.terrarium.cloche.api.metadata.CommonMetadata
import earth.terrarium.cloche.api.metadata.FabricMetadata
import earth.terrarium.cloche.api.target.FabricTarget
import earth.terrarium.cloche.api.target.ForgeLikeTarget
import earth.terrarium.cloche.api.target.MinecraftTarget
import earth.terrarium.cloche.api.target.NeoforgeTarget
import earth.terrarium.cloche.tasks.GenerateFabricModJson
import groovy.lang.Closure
import net.msrandom.minecraftcodev.core.utils.lowerCamelCaseGradleName
import net.msrandom.minecraftcodev.fabric.MinecraftCodevFabricPlugin
import net.msrandom.minecraftcodev.fabric.task.JarInJar
import net.msrandom.minecraftcodev.forge.task.JarJar
import org.gradle.jvm.tasks.Jar

plugins {
    java
    idea

    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"

    id("com.palantir.git-version") version "3.1.0"

    id("com.gradleup.shadow") version "9.0.2"

    id("earth.terrarium.cloche") version "0.16.26-dust"

    id("org.moddedmc.wiki.toolkit") version "0.3.2"
}

val archive_name: String by rootProject.properties
val id: String by rootProject.properties
val source: String by rootProject.properties

group = "settingdust.calypsos_nightvision_goggles"

val gitVersion: Closure<String> by extra
version = gitVersion()

base { archivesName = archive_name }

wiki {
    docs {
        create(id) {
            root = file("docs")
        }
    }
}

repositories {
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven")
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }

    exclusiveContent {
        forRepository {
            maven("https://cursemaven.com")
        }
        filter {
            includeGroup("curse.maven")
        }
    }

    maven("https://repo.nyon.dev/releases") {
        content {
            includeGroup("dev.nyon")
        }
    }

    maven("https://maven.theillusivec4.top/") {
        content {
            includeGroup("top.theillusivec4.curios")
        }
    }

    maven("https://maven.terraformersmc.com/") {
        content {
            includeGroup("dev.emi")
        }
    }

    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") {
        content {
            includeGroupAndSubgroups("software.bernie")
            includeGroup("com.eliotlash.mclib")
        }
    }

    maven("https://maven.ladysnake.org/releases") {
        content {
            includeGroup("dev.onyxstudios.cardinal-components-api")
            includeGroup("org.ladysnake.cardinal-components-api")
        }
    }

    maven("https://maven.wispforest.io/releases") {
        content {
            includeGroupAndSubgroups("io.wispforest")
        }
    }

    maven("https://maven.shedaniel.me/") {
        content {
            includeGroup("me.shedaniel.cloth")
        }
    }

    maven("https://maven.su5ed.dev/releases") {
        content {
            includeGroup("dev.su5ed.sinytra.fabric-api")
            includeGroupAndSubgroups("org.sinytra")
        }
    }

    maven("https://registry.somethingcatchy.net/repository/maven-releases/") {
        content {
            includeGroup("net.mehvahdjukaar")
        }
    }

    mavenCentral()

    cloche {
        librariesMinecraft()
        main()
        mavenFabric()
        mavenForge()
        mavenNeoforged()
        mavenNeoforgedMeta()
        mavenParchment()
    }

    mavenLocal()
}



class MinecraftVersionCompatibilityRule : AttributeCompatibilityRule<String> {
    override fun execute(details: CompatibilityCheckDetails<String>) {
        details.compatible()
    }
}

class MinecraftModLoaderCompatibilityRule : AttributeCompatibilityRule<MinecraftModLoader> {
    override fun execute(details: CompatibilityCheckDetails<MinecraftModLoader>) {
        if (details.producerValue == MinecraftModLoader.common) {
            details.compatible()
        }
    }
}

dependencies {
    attributesSchema {
        attribute(TargetAttributes.MINECRAFT_VERSION) {
            compatibilityRules.add(MinecraftVersionCompatibilityRule::class)
        }
        attribute(TargetAttributes.MOD_LOADER) {
            compatibilityRules.add(MinecraftModLoaderCompatibilityRule::class)
        }
        attribute(TargetAttributes.CLOCHE_MINECRAFT_VERSION) {
            compatibilityRules.add(MinecraftVersionCompatibilityRule::class)
        }
        attribute(TargetAttributes.CLOCHE_MOD_LOADER) {
            compatibilityRules.add(MinecraftModLoaderCompatibilityRule::class)
        }
    }
}

val containerTasks = mutableSetOf<TaskProvider<out Jar>>()

cloche {
    metadata {
        modId = id
        name = rootProject.property("name").toString()
        description = rootProject.property("description").toString()
        license = "ARR"
        icon = "assets/$id/icon.png"
        sources = source
        issues = "$source/issues"
        author("SettingDust")

        dependency {
            modId = "minecraft"
            type = CommonMetadata.Dependency.Type.Required
            version {
                start = "1.20.1"
            }
        }

        dependency {
            modId = "geckolib"
            type = CommonMetadata.Dependency.Type.Required
        }
    }

    mappings {
        official()
    }

    common {
        mixins.from(file("src/common/main/resources/$id.mixins.json"))
        accessWideners.from(file("src/common/main/resources/$id.accessWidener"))

        dependencies {
            compileOnly("org.spongepowered:mixin:0.8.7")
        }
    }

    val commons = mapOf(
        "1.20.1" to common("common:1.20.1") {
            mixins.from("src/common/1.20.1/main/resources/$id.1_20.mixins.json")
        },
        "1.21.1" to common("common:1.21.1") {
            mixins.from("src/common/1.21.1/main/resources/$id.1_21.mixins.json")
        },
    )

    run fabric@{
        val fabricCommon = common("fabric:common") {
            mixins.from(file("src/fabric/common/main/resources/$id.fabric.mixins.json"))
        }

        val fabric1201 = fabric("fabric:1.20.1") {
            minecraftVersion = "1.20.1"

            metadata {
                dependency {
                    modId = "minecraft"
                    type = CommonMetadata.Dependency.Type.Required
                    version {
                        start = "1.20.1"
                        end = "1.21"
                    }
                }
            }

            dependencies {
                fabricApi("0.92.6")

                modImplementation(catalog.accessories.get1().get20().get1().fabric)
                modImplementation(catalog.trinkets.get1().get20().get1())

                modImplementation(catalog.cardinal.components.get1().get20().get1().base)
                modImplementation(catalog.cardinal.components.get1().get20().get1().entity)

                modImplementation(catalog.geckolib.get1().get20().get1().fabric)
                implementation(catalog.mclib)

                modRuntimeOnly(catalog.jade.get1().get20().get1().fabric)
            }

            tasks.named<GenerateFabricModJson>(generateModsManifestTaskName) {
                modId = "${id}_1_20"
            }
        }

        val fabric121 = fabric("fabric:1.21") {
            minecraftVersion = "1.21.1"

            metadata {
                dependency {
                    modId = "minecraft"
                    type = CommonMetadata.Dependency.Type.Required
                    version {
                        start = "1.21"
                    }
                }
            }

            dependencies {
                fabricApi("0.116.6")

                modImplementation(catalog.accessories.get1().get21().get1().fabric)
                modImplementation(catalog.trinkets.get1().get21().get1())

                modImplementation(catalog.cardinal.components.get1().get21().get1().base)
                modImplementation(catalog.cardinal.components.get1().get21().get1().entity)

                modImplementation(catalog.geckolib.get1().get21().get1().fabric)

                modImplementation(catalog.vista.fabric)
                modImplementation(catalog.moonlight.fabric)

                modRuntimeOnly(catalog.jade.get1().get21().get1().fabric)
            }

            tasks.named<GenerateFabricModJson>(generateModsManifestTaskName) {
                modId = "${id}_1_21"
            }
        }

        run container@{
            val featureName = "containerFabric"
            val metadataDirectory = project.layout.buildDirectory.dir("generated")
                .map { it.dir("metadata").dir(featureName) }
            val include = configurations.register(lowerCamelCaseGradleName(featureName, "include")) {
                isCanBeResolved = true
                isTransitive = false

                attributes {
                    attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
                    attribute(REMAPPED_ATTRIBUTE, false)
                    attribute(INCLUDE_TRANSFORMED_OUTPUT_ATTRIBUTE, false)
                }
            }
            val targets = setOf(fabric1201, fabric121)

            dependencies {
                for (target in targets) {
                    include(project(":")) {
                        capabilities {
                            requireFeature(target.capabilitySuffix!!)
                        }
                    }
                }
            }

            tasks {
                val generateModJson =
                    register<GenerateFabricModJson>(lowerCamelCaseGradleName(featureName, "generateModJson")) {
                        modId = id
                        metadata = objects.newInstance(FabricMetadata::class.java, fabric1201).apply {
                            license.value(cloche.metadata.license)
                            dependencies.value(cloche.metadata.dependencies)
                        }
                        loaderDependencyVersion = "0.18"
                        output.set(metadataDirectory.map { it.file("fabric.mod.json") })
                    }

                val jar = register<Jar>(lowerCamelCaseGradleName(featureName, "jar")) {
                    group = "build"
                    archiveClassifier = "fabric"
                    destinationDirectory = intermediateOutputsDirectory
                    dependsOn(generateModJson)
                    from(metadataDirectory)
                }

                val includesJar = register<JarInJar>(lowerCamelCaseGradleName(featureName, "includeJar")) {
                    dependsOn(targets.map { it.includeJarTaskName })

                    archiveClassifier = "fabric"
                    input = jar.flatMap { it.archiveFile }
                    fromResolutionResults(include)
                }

                containerTasks += includesJar

                build {
                    dependsOn(includesJar)
                }
            }
        }

        targets.withType<FabricTarget> {
            loaderVersion = "0.16.14"

            includedClient()

            dependsOn(fabricCommon)

            metadata {
                entrypoint("main") {
                    adapter = "kotlin"
                    value = "$group.fabric.Entrypoint::init"
                }

                entrypoint("client") {
                    adapter = "kotlin"
                    value = "$group.fabric.Entrypoint::clientInit"
                }

                dependency {
                    modId = "fabric-api"
                    type = CommonMetadata.Dependency.Type.Required
                }

                dependency {
                    modId = "fabric-language-kotlin"
                    type = CommonMetadata.Dependency.Type.Required
                }

                dependency {
                    modId = "trinkets"
                    type = CommonMetadata.Dependency.Type.Recommended
                }
            }

            dependencies {
                modImplementation("net.fabricmc:fabric-language-kotlin:1.13.1+kotlin.2.1.10")
            }
        }
    }

    run forge@{
        val forge1201 = forge("forge:1.20.1") {
            minecraftVersion = "1.20.1"
            loaderVersion = "47.4.4"

            metadata {
                modLoader = "klf"
                loaderVersion {
                    start = "1"
                }

                dependency {
                    modId = "minecraft"
                    type = CommonMetadata.Dependency.Type.Required
                    version {
                        start = "1.20.1"
                        end = "1.21"
                    }
                }
            }

            repositories {
                maven("https://repo.spongepowered.org/maven") {
                    content {
                        includeGroup("org.spongepowered")
                    }
                }
            }

            dependencies {
                implementation("org.spongepowered:mixin:0.8.7")
                compileOnly(catalog.mixinextras.common)
                implementation(catalog.mixinextras.forge)

                modImplementation(catalog.klf.get1().get20().get1().forge)

                modImplementation(catalog.accessories.get1().get20().get1().neoforge)
                modImplementation(catalog.curios.get1().get20().get1().forge)

                modImplementation(catalog.geckolib.get1().get20().get1().forge)
                implementation(catalog.mclib)

                modRuntimeOnly(catalog.jade.get1().get20().get1().forge)
            }
        }
    }

    run neoforge@{
        val neoforge121 = neoforge("neoforge:1.21") {
            minecraftVersion = "1.21.1"
            loaderVersion = "21.1.215"

            metadata {
                modLoader = "klf"
                loaderVersion {
                    start = "1"
                }

                dependency {
                    modId = "minecraft"
                    type = CommonMetadata.Dependency.Type.Required
                    version {
                        start = "1.21"
                    }
                }
            }

            dependencies {
                modImplementation(catalog.klf.get1().get21().get1().neoforge)

                modImplementation(catalog.accessories.get1().get21().get1().neoforge)
                modImplementation(catalog.curios.get1().get21().get1().neoforge)

                modImplementation(catalog.geckolib.get1().get21().get1().neoforge)

                modImplementation("curse.maven:bytebuddies-1366195:7174647")

                modImplementation(catalog.vista.neoforge)
                modImplementation(catalog.moonlight.neoforge)

                modRuntimeOnly(catalog.jade.get1().get21().get1().neoforge)
            }
        }

        run container@{
            val featureName = "containerNeoforge"
            val include = configurations.register(lowerCamelCaseGradleName(featureName, "include")) {
                isCanBeResolved = true
                isTransitive = false

                attributes {
                    attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
                    attribute(REMAPPED_ATTRIBUTE, false)
                    attribute(INCLUDE_TRANSFORMED_OUTPUT_ATTRIBUTE, false)
                }
            }

            val targets = setOf(neoforge121)

            dependencies {
                for (target in targets) {
                    include(project(":")) {
                        capabilities {
                            requireFeature(target.capabilitySuffix!!)
                        }
                    }
                }
            }

            tasks {
                val jar = register<Jar>(lowerCamelCaseGradleName(featureName, "jar")) {
                    group = "build"
                    archiveBaseName = "$id-${featureName.camelToKebabCase()}"
                    archiveClassifier = "neoforge"
                    destinationDirectory = intermediateOutputsDirectory
                }

                val includesJar = register<JarJar>(lowerCamelCaseGradleName(featureName, "includeJar")) {
                    dependsOn(targets.map { it.includeJarTaskName })

                    archiveClassifier = "neoforge"
                    input = jar.flatMap { it.archiveFile }
                    fromResolutionResults(include)
                }

                containerTasks += includesJar

                build {
                    dependsOn(includesJar)
                }
            }
        }

        targets.withType<NeoforgeTarget> {
            metadata {
                modLoader = "klf"
                loaderVersion {
                    start = "1"
                }
            }
        }
    }

    targets.withType<ForgeLikeTarget> {
        metadata {
            dependency {
                modId = "curios"
                type = CommonMetadata.Dependency.Type.Recommended
            }

            dependency {
                modId = "accessories"
                type = CommonMetadata.Dependency.Type.Recommended
            }
        }
    }

    val mcVersionToJavaVersion = mapOf(
        "1.20.1" to JavaVersion.VERSION_17,
        "1.21.1" to JavaVersion.VERSION_21,
    )

    targets.all {
        dependsOn(commons.getValue(minecraftVersion.get()))

        runs {
            client {
                jvmVersion = minecraftVersion.map { mcVersionToJavaVersion[it]!!.majorVersion.toInt() }
                jvmArguments("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
            }
        }

        mappings {
            parchment(minecraftVersion.map {
                when (it) {
                    "1.20.1" -> "2023.09.03"
                    "1.21.1" -> "2024.11.17"
                    else -> throw IllegalArgumentException("Unsupported minecraft version $it")
                }
            })
        }

        tasks.named<JavaCompile>(sourceSet.compileJavaTaskName) {
            val javaVersion = mcVersionToJavaVersion[minecraftVersion.get()] ?: return@named
            sourceCompatibility = javaVersion.majorVersion
            targetCompatibility = javaVersion.majorVersion
        }
    }
}

val SourceSet.includeJarTaskName: String
    get() = lowerCamelCaseGradleName(takeUnless(SourceSet::isMain)?.name, "includeJar")

val MinecraftTarget.includeJarTaskName: String
    get() = when (this) {
        is FabricTarget -> sourceSet.includeJarTaskName
        is ForgeLikeTarget -> sourceSet.includeJarTaskName
        else -> throw IllegalArgumentException("Unsupported target $this")
    }

val FabricTarget.generateModsJsonTaskName: String
    get() = lowerCamelCaseGradleName("generate", featureName, "ModJson")

val ForgeLikeTarget.generateModsTomlTaskName: String
    get() = lowerCamelCaseGradleName("generate", featureName, "modsToml")

val MinecraftTarget.generateModsManifestTaskName: String
    get() = when (this) {
        is FabricTarget -> generateModsJsonTaskName
        is ForgeLikeTarget -> generateModsTomlTaskName
        else -> throw IllegalArgumentException("Unsupported target $this")
    }

fun String.camelToKebabCase(): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "-$0").lowercase()
}

tasks {
    withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    shadowJar {
        enabled = false
    }

    val shadowContainersJar by registering(ShadowJar::class) {
        archiveClassifier = ""

        for (task in containerTasks) {
            from(task.map { zipTree(it.archiveFile) })
            manifest.inheritFrom(task.get().manifest)
        }

        manifest {
            attributes(
                "FMLModType" to "GAMELIBRARY"
            )
        }

        append("META-INF/accesstransformer.cfg")
    }

    val shadowSourcesJar by registering(ShadowJar::class) {
        dependsOn(cloche.targets.map { it.generateModsManifestTaskName })

        mergeServiceFiles()
        archiveClassifier.set("sources")
        from(sourceSets.map { it.allSource })

        doFirst {
            manifest {
                from(source.filter { it.name.equals("MANIFEST.MF") }.toList())
            }
        }
    }

    build {
        dependsOn(shadowContainersJar, shadowSourcesJar)
    }

    for (target in cloche.targets.filterIsInstance<FabricTarget>()) {
        named(lowerCamelCaseGradleName("accessWiden", target.featureName, "commonMinecraft")) {
            dependsOn(
                lowerCamelCaseGradleName(
                    "remap",
                    target.featureName,
                    "commonMinecraft",
                    MinecraftCodevFabricPlugin.INTERMEDIARY_MAPPINGS_NAMESPACE,
                ), lowerCamelCaseGradleName(
                    "remap",
                    target.featureName,
                    "clientMinecraft",
                    MinecraftCodevFabricPlugin.INTERMEDIARY_MAPPINGS_NAMESPACE,
                ), lowerCamelCaseGradleName("generate", target.featureName, "MappingsArtifact")
            )
        }

        named(lowerCamelCaseGradleName("accessWiden", target.featureName, "Minecraft")) {
            dependsOn(
                lowerCamelCaseGradleName(
                    "remap",
                    target.featureName,
                    "clientMinecraft",
                    MinecraftCodevFabricPlugin.INTERMEDIARY_MAPPINGS_NAMESPACE,
                ), lowerCamelCaseGradleName("generate", target.featureName, "MappingsArtifact")
            )
        }
    }
}