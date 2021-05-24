package org.dk.parser

import org.dk.model.*

class Parser(private val config: ConfigFromWeb) {

    fun parse() =
        Config(
            global = parseGlobalFields(),
            features = parseFeatures()
        )

    private fun parseGlobalFields() =
        Global(
            projectName = config.global.find { it.title == "projectName" }?.value ?: "",
            buildType = config.global.find { it.title == "buildType" }?.value ?: "",
            group = config.global.find { it.title == "group" }?.value ?: "",
            version = config.global.find { it.title == "version" }?.value ?: "",
            ktorVersion = config.global.find { it.title == "ktorVersion" }?.value ?: "",
            kotlinVersion = config.global.find { it.title == "kotlinVersion" }?.value ?: "",
            port = config.global.find { it.title == "port" }?.value ?: ""
        )

    private fun parseFeatures() =
        Features(
            routes = parseRoutes(),
            web = parseWeb(),
            database = parseDataBase(),
            socket = parseSocket()
        )

    private fun parseRoutes() =
        config.globalRoutes.map {
            Routes(
                name = it.methodName,
                file = it.file,
                routeDetail = parseToRouteDetail(it.id)
            )
        }

    private fun parseToRouteDetail(id: Long) =
        config.localRoutes
            .filter { it.parentGlobalRouteId == id }
            .map {
                RouteDetail(
                    path = it.path,
                    requests = parseToRequest(it.id)
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

    private fun parseWeb() =
        Web(
            name = config.database.find { it.title == "name" }?.value ?: "",
            path = config.database.find { it.title == "name" }?.value ?: "",
            template = "FB",
            resources = parseResources()
        )

    private fun parseResources() =
        config.webRoutes.map {
            Resources(
                remotePath = it.path,
                resource = it.resource
            )
        }

    private fun parseDataBase() =
        DataBase(
            type = config.database.find { it.title == "type" }?.value ?: "",
            path = config.database.find { it.title == "file" }?.value ?: "",
            port = config.database.find { it.title == "port" }?.value ?: "",
            host = config.database.find { it.title == "host" }?.value ?: "",
            dbName = config.database.find { it.title == "dbName" }?.value ?: "",
            username = config.database.find { it.title == "username" }?.value ?: "",
            password = config.database.find { it.title == "password" }?.value ?: "",
            entities = parseDataBaseEntities()
        )

    private fun parseDataBaseEntities() =
        config.dbEntities.map {
            Entities(
                name = it.name,
                file = it.file,
                tableName = it.tableName,
                route = parseRoutes(it.id),
                primaryKey = PrimaryKey(idName = "id", type = "LONG"),
                entityFields = parseEntityFields(it.id)
            )
        }

    private fun parseRoutes(id: Long) =
        Route(
            standardRoutes = config.dbRoute
                .filter { it.id == id}
                .map { it.value }
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

    private fun parseSocket() =
        Socket(
            path = null,
            name = config.database.find { it.title == "name" }?.value ?: "",
            webPath = config.database.find { it.title == "path" }?.value ?: "",
            answer = config.database.find { it.title == "answer" }?.value ?: "",
            closeWord = config.database.find { it.title == "closeWord" }?.value ?: "",
            closeMessage = config.database.find { it.title == "closeMessage" }?.value ?: "",
        )
}