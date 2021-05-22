package com.example

import com.example.controllers.*
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun dataBaseConnection() {
    val dataBaseUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC"
    val dataBaseDrive = "com.mysql.cj.jdbc.Driver"
    val dataBaseUser = "root"
    val dataBasePassword = "" // TODO should be secret
    Database.connect(dataBaseUrl, dataBaseDrive, dataBaseUser, dataBasePassword)
    transaction {
        SchemaUtils.create(UserObject)
    }
}
fun Application.websocket() {
    install(WebSockets)
    routing {
        webSocket("/web") {
            while (true) {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Ping -> {

                        }
                        is Frame.Pong -> {

                        }
                        is Frame.Binary -> {

                        }
                        is Frame.Close -> {

                        }
                        is Frame.Text -> {
                            val text = frame.readText()
                            outgoing.send(Frame.Text("You said: $text"))
                            if (text.equals("bye", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Bye"))
                            }
                        }
                    }
                }
            }
        }
    }
}