package com.example

import io.ktor.application.*
import com.example.*
import com.example.*
import com.example.controllers.*
import com.example.controllers.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.gson.*

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
websocket()
dataBaseConnection()
install(ContentNegotiation) { gson { } }
UserRoute()
Car()
}