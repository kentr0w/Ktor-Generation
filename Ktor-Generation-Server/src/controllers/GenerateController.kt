package org.dk.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.dk.model.GlobalConfig
import org.dk.model.Titles
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
                val q = call.receive<GlobalConfig>()
                println(q)
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