package org.dk.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.dk.model.ConfigFromWeb
import org.dk.parser.Parser
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 *
 */
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
                val q = call.receive<ConfigFromWeb>()
                val yaml = asYaml(Gson().toJson(q))
                val file = File("test.yaml")
                file.createNewFile()
                yaml?.let { file.writeText(it) }
                val w = Parser(q).parse()



                val yaml2 = asYaml(Gson().toJson(w))
                val file2 = File("test2.yaml")
                file2.createNewFile()
                yaml2?.let { file.writeText(it) }
            }
        }
    }
}

fun String.runCommand(workingDir: File): String? {
    return try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        proc.waitFor(60, TimeUnit.MINUTES)
        proc.inputStream.bufferedReader().readText()
    } catch(e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(JsonProcessingException::class, IOException::class)
fun asYaml(jsonString: String?): String? {
    val jsonNodeTree: JsonNode = ObjectMapper().readTree(jsonString)
    return YAMLMapper().writeValueAsString(jsonNodeTree)
}