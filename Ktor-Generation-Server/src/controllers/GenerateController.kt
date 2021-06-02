package org.dk.controllers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.dk.model.ConfigFromWeb
import org.dk.parser.Parser
import org.dk.parser.parseFiles
import java.io.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

val folderName = AtomicLong(0L)
val pathToCore = "resources/Ktor-Generation-Core-1.0-SNAPSHOT.jar"

fun Application.generateRoute() {
    routing {
        route("/generate") {
            post("/submit") {
                val jsonText = call.receive<String>()
                val rootNode = ObjectMapper(JsonFactory()).readTree(jsonText)
                val mapper = jacksonObjectMapper()
                val configFromWeb: ConfigFromWeb = mapper.readValue(rootNode["feature"].toString())
                val files = rootNode["files"]

                val pathToFolder = "folders/${folderName.getAndIncrement()}"
                val newFolder = File(pathToFolder)
                if (newFolder.exists()) {
                    newFolder.deleteRecursively()
                }
                newFolder.mkdirs()

                val yaml = asYaml(Gson().toJson(configFromWeb))
                val file = File(pathToFolder + File.separator + "config2.yaml")
                file.createNewFile()
                yaml?.let { file.writeText(it) }

                val tree = parseFiles(files)
                val config = Parser(configFromWeb, tree).parse()

                val yaml2 = asYaml(Gson().toJson(config))
                val file2 = File(pathToFolder + File.separator + "config.yaml")
                file2.createNewFile()
                yaml2?.let { file2.writeText(it) }


                tree.writeToFile(pathToFolder + File.separator + "project.tr")


                File(pathToCore).copyTo(
                    File(pathToFolder + File.separator + "Ktor-Generation-Core-1.0-SNAPSHOT.jar"),
                    true
                )

                val q = "java -jar Ktor-Generation-Core-1.0-SNAPSHOT.jar".runCommand(
                    File(pathToFolder)
                )

                zipAll(pathToFolder + File.separator + "null", pathToFolder + File.separator + "newZip")
                call.respondText(pathToFolder.split(File.separator).last())
            }

            get("/file/{id}") {
                val id = call.parameters["id"]
                val file = File("folders/$id" + File.separator + "newZip")
                call.respondFile(file)
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

fun zipAll(directory: String, zipFile: String) {
    val sourceFile = File(directory)
    ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use {
        zipFiles(it, sourceFile, "")
    }
}

private fun zipFiles(zipOut: ZipOutputStream, sourceFile: File, parentDirPath: String) {
    val data = ByteArray(2048)
    sourceFile.listFiles()?.forEach { f ->
        if (f.isDirectory) {
            val path = if (parentDirPath == "") {
                f.name
            } else {
                parentDirPath + File.separator + f.name
            }
            val entry = ZipEntry(path + File.separator)
            entry.time = f.lastModified()
            entry.isDirectory
            entry.size = f.length()
            zipOut.putNextEntry(entry)
            //Call recursively to add files within this directory
            zipFiles(zipOut, f, path)
        } else {
            FileInputStream(f).use { fi ->
                BufferedInputStream(fi).use { origin ->
                    val path = parentDirPath + File.separator + f.name
                    val entry = ZipEntry(path)
                    entry.time = f.lastModified()
                    entry.isDirectory
                    entry.size = f.length()
                    zipOut.putNextEntry(entry)
                    while (true) {
                        val readBytes = origin.read(data)
                        if (readBytes == -1) {
                            break
                        }
                        zipOut.write(data, 0, readBytes)
                    }
                }
            }
        }
    }
}

@Throws(JsonProcessingException::class, IOException::class)
fun asYaml(jsonString: String?): String? {
    val jsonNodeTree: JsonNode = ObjectMapper().readTree(jsonString)
    return YAMLMapper().writeValueAsString(jsonNodeTree)
}
