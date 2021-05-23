package org.dk.model

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val global: Global,
    val features: Features
)

/*
    Global Information ------------------------------------------
 */

@Serializable
data class Global(
    val projectName: String,
    val buildType: String,
    val group: String,
    val version: String,
    val ktorVersion: String,
    val kotlinVersion: String,
    val port: String
)

/*
    Features ------------------------------------------
 */

@Serializable
data class Features(
    val routes: List<Routes>,
    val web: Web,
    val database: DataBase,
    val socket: Socket,
)


/*
    Route Feature ------------------------------------------
 */
@Serializable
data class Routes(
    val name: String,
    val file: String,
    val routeDetail: List<RouteDetail>
)

@Serializable
data class RouteDetail(
    val path: String,
    val requests: List<Requests>
)

@Serializable
data class Requests(
    val type: String,
    val path: String
)

/*
    Web Feature ------------------------------------------
 */

@Serializable
data class Web(
    val name: String,
    val file: String,
    val template: String,
    val resources: List<Resources>
)

@Serializable
data class Resources(
    val remotePath: String,
    val resource: String
)


/*
    DataBase Feature ------------------------------------------
 */

@Serializable
data class DataBase(
    val type: String,
    val file: String,
    val port: String,
    val host: String,
    val dbName: String,
    val username: String,
    val password: String,
    val entities: List<Entities>?,
)

@Serializable
data class Entities(
    val name: String,
    val file: String,
    val tableName: String,
    val route: Route?,
    val primaryKey: PrimaryKey?,
    val entityFields: List<EntityFields>?
)

@Serializable
data class Route(
    val standardRoutes: List<String>
)

@Serializable
data class PrimaryKey(
    val idName: String,
    val type: String
)

@Serializable
data class EntityFields(
    val variableName: String,
    val columnName: String,
    val type: String,
    val length: String?,
    val fieldDetail: List<String>?
)

/*
    Socket Feature ------------------------------------------
 */

@Serializable
data class Socket(
    val file: String?,
    val name: String,
    val path: String,
    val answer: String,
    val closeWord: String,
    val closeMessage: String,
)
