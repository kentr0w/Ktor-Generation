package org.dk.parser

import com.fasterxml.jackson.databind.JsonNode
import org.dk.model.*

fun parseFiles(rootNode: JsonNode): Tree {
    val name = rootNode["name"].toString().replace("\"", "")
    val root = Node(name, null)
    rootNode["children"].forEach {
        DFSTree(root, it)
    }
    return Tree(root)
}

private fun DFSTree(parent: Node, jsonNode: JsonNode) {
    val name = jsonNode["name"].toString().replace("\"", "")
    val node = Node(name, parent)
    parent.addChild(node)
    jsonNode["children"].forEach {
        DFSTree(node, it)
    }
}

/**
 *
 */
class Parser(private val config: ConfigFromWeb) {


    /**
     *
     */
    fun parse(): Config =
        Config(
            global = parseGlobalFields(),
            features = parseFeatures()
        )

    private fun parseGlobalFields(): Global {
        return Global(
            projectName = config.global.find { it.title == "projectName" }?.value,
            buildType = config.global.find { it.title == "buildType" }?.value ?: "",
            group = config.global.find { it.title == "group" }?.value ?: "",
            version = config.global.find { it.title == "version" }?.value ?: "",
            ktorVersion = config.global.find { it.title == "ktorVersion" }?.value ?: "",
            kotlinVersion = config.global.find { it.title == "kotlinVersion" }?.value ?: "",
            port = config.global.find { it.title == "port" }?.value ?: ""
        )
    }

    private fun parseFeatures(): Features {
        return Features(
            routes = parseRoutes(),
            web = parseWeb(),
            database = parseDataBase(),
            socket = parseSocket()
        )
    }

    private fun parseRoutes() =
        config.globalRoutes.map {
            Routes(
                name = it.methodName,
                path = it.file,
                routeDetail = it.id?.let { parseToRouteDetail(it) }
            )
        }

    private fun parseToRouteDetail(id: Long) =
        config.localRoutes
            .filter { it.parentGlobalRouteId == id }
            .map {
                RouteDetail(
                    url = it.path,
                    requests = it.id?.let { parseToRequest(it) }
                )
            }

    private fun parseToRequest(id: Long) =
        config.miniRoutes
            .filter { it.parentLocalRouteId == id }
            .map {
                Requests(
                    it.type,
                    it.path
                )
            }

    private fun parseWeb(): Web? {
        val web = Web(
            name = config.web.find { it.title == "name" }?.value,
            path = config.web.find { it.title == "name" }?.value,
            template = "FB",
            resources = parseResources()
        )
        return if (listOf(web.name, web.path, web.template, web.resources).any { it == null || it == "" })
            null
        else
            web
    }

    private fun parseResources() =
        config.webRoutes.map {
            Resources(
                remotePath = it.path,
                resource = it.resource
            )
        }

    private fun parseDataBase(): DataBase? {
        val dataBase = DataBase(
            type = config.database.find { it.title == "type" }?.value,
            path = config.database.find { it.title == "file" }?.value ?: "Application.kt",
            port = config.database.find { it.title == "port" }?.value,
            host = config.database.find { it.title == "host" }?.value,
            dbName = config.database.find { it.title == "dbName" }?.value,
            username = config.database.find { it.title == "username" }?.value,
            password = config.database.find { it.title == "password" }?.value ?: "",
            entities = parseDataBaseEntities()
        )
        return if (listOf(
                dataBase.type,
                dataBase.path,
                dataBase.port,
                dataBase.host,
                dataBase.dbName,
                dataBase.username
            ).any { it == null || it == "" }
        )
            null
        else
            dataBase
    }

    private fun parseDataBaseEntities() =
        config.dbEntities.map {
            Entities(
                name = it.name,
                file = it.file,
                tableName = it.tableName,
                route = it.id?.let { parseRoutes(it) },
                primaryKey = PrimaryKey(idName = "id", type = "LONG"),
                entityFields = it.id?.let { parseEntityFields(it) }
            )
        }

    private fun parseRoutes(id: Long) =
        Route(
            standardRoutes = config.dbRoute
                .filter { it.id == id }
                .mapNotNull { it.value }
        )

    private fun parseEntityFields(id: Long) =
        config.dbFields
            .filter { it.id == id }
            .map {
                EntityFields(
                    variableName = it.name,
                    columnName = it.column,
                    type = it.type,
                    length = "1000",
                    null
                )
            }

    private fun parseSocket(): Socket? {
        val socket = Socket(
            path = "",
            name = config.socket.find { it.title == "name" }?.value,
            webPath = config.socket.find { it.title == "path" }?.value,
            answer = config.socket.find { it.title == "answer" }?.value,
            closeWord = config.socket.find { it.title == "closeWord" }?.value,
            closeMessage = config.socket.find { it.title == "closeMessage" }?.value,
        )
        return if (listOf(
                socket.path,
                socket.name,
                socket.webPath,
                socket.answer,
                socket.closeWord,
                socket.closeMessage
            ).any { it == null }
        )
            null
        else
            socket
    }
}