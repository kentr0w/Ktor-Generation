package org.dk.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfigFromWeb(
    val global: List<GlobalFromWeb>,
    val database: List<DatabaseFromWeb>,
    val socket: List<SocketFromWeb>,
    val web: List<WebFromWeb>,
    val dbEntities: List<DBEntitiesFromWeb>,
    val dbFields: List<DBFieldsFromWeb>,
    val dbRoute: List<DBRoutesFromWeb>,
    val webRoutes: List<WebRoutesFromWeb>,
    val globalRoutes: List<GlobalRoutesFromWeb>,
    val localRoutes: List<LocalRoutesFromWeb>,
    val miniRoutes: List<MiniRoutesFromWeb>,
)


@Serializable
data class GlobalFromWeb(val title: String, val value: String)

@Serializable
data class DatabaseFromWeb(val title: String, val value: String)

@Serializable
data class SocketFromWeb(val title: String, val value: String)

@Serializable
data class WebFromWeb(val title: String, val value: String)

@Serializable
data class DBEntitiesFromWeb(
    val id: Long,
    val name: String,
    val tableName: String,
    val file: String,
)

@Serializable
data class DBFieldsFromWeb(
    val id: Long,
    val parentEntityId: Long,
    val name: String,
    val column: String,
    val type: String
    )

@Serializable
data class DBRoutesFromWeb(
    val id: Long,
    val value: String
)

@Serializable
data class WebRoutesFromWeb(val id: Long, val path: String, val resource: String)

@Serializable
data class GlobalRoutesFromWeb(val id: Long, val methodName: String, val file: String)

@Serializable
data class LocalRoutesFromWeb(val id: Long, val parentGlobalRouteId: Long, val path: String)

@Serializable
data class MiniRoutesFromWeb(val id: Long, val parentLocalRouteId: Long, val path: String, val type: String)