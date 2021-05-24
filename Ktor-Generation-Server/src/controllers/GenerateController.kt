package org.dk.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumesAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.dk.model.ConfigFromWeb
import org.dk.parser.Parser
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

val folderName = AtomicLong(0L)
val pathToCore = "resources/Ktor-Generation-Core-1.0-SNAPSHOT.jar"

fun Application.generateRoute() {
    routing {
        route("/generate") {
            get("/submit") {
                println("Starting")
                val q = "java -jar Ktor-Generation-Core-1.0-SNAPSHOT.jar".runCommand(
                    File("/home/ubuntu/IdeaProjects/Ktor-Generation/Ktor-Generation-Server/resources")
                )
                println(q)
                println("Finish")
            }
            post("/submit") {

                println("Generate folder")
                val pathToFolder = "folders/${folderName.getAndIncrement()}"
                val newFolder = File(pathToFolder)
                if (newFolder.exists()) {
                    newFolder.deleteRecursively()
                }
                newFolder.mkdirs()
                println("Generated")

                val configFromWeb = call.receive<ConfigFromWeb>()

                println(configFromWeb.toString())

                println("Starting to write config from web")
                val yaml = asYaml(Gson().toJson(configFromWeb))
                val file = File(pathToFolder + File.separator + "config2.yaml")
                file.createNewFile()
                yaml?.let { file.writeText(it) }
                println("Wrote config")

                println("Starting to parse")
                val config = Parser(configFromWeb).parse()
                println("Parsed")

                println("Starting to write config")
                val yaml2 = asYaml(Gson().toJson(config))
                val file2 = File(pathToFolder + File.separator + "config.yaml")
                file2.createNewFile()
                yaml2?.let { file2.writeText(it) }
                println("Wrote config")

                println("Starting to copy jar")
                println(File(pathToCore).exists())
                println(File(pathToFolder).exists())
                File(pathToCore).copyTo(File(pathToFolder + File.separator + "Ktor-Generation-Core-1.0-SNAPSHOT.jar"), true)
                println("Copied")

                println("Starting")
                val q = "java -jar Ktor-Generation-Core-1.0-SNAPSHOT.jar".runCommand(
                    File(pathToFolder)
                )
                println(q)
                println("Finish")
            }
        }
    }
}

fun String.runCommand(workingDir: File) {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        proc.waitFor(60, TimeUnit.MINUTES)
        proc.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(JsonProcessingException::class, IOException::class)
fun asYaml(jsonString: String?): String? {
    val jsonNodeTree: JsonNode = ObjectMapper().readTree(jsonString)
    return YAMLMapper().writeValueAsString(jsonNodeTree)
}
