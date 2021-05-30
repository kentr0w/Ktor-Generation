package org.dk.model

import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class Config(
    val global: Global,
    val features: Features?
)

/*
    Global Information ------------------------------------------
 */

@Serializable
data class Global(
    val projectName: String?,
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
    val routes: List<Routes>?,
    val web: Web?,
    val database: DataBase?,
    val socket: Socket?,
)


/*
    Route Feature ------------------------------------------
 */
@Serializable
data class Routes(
    val name: String?,
    val path: String?,
    val routeDetail: List<RouteDetail>?
)

@Serializable
data class RouteDetail(
    val url: String?,
    val requests: List<Requests>?
)

@Serializable
data class Requests(
    val type: String?,
    val requestUrl: String?
)

/*
    Web Feature ------------------------------------------
 */

@Serializable
data class Web(
    val name: String?,
    val path: String?,
    val template: String?,
    val resources: List<Resources>
)

@Serializable
data class Resources(
    val remotePath: String?,
    val resource: String?
)


/*
    DataBase Feature ------------------------------------------
 */

@Serializable
data class DataBase(
    val type: String?,
    val path: String?,
    val port: String?,
    val host: String?,
    val dbName: String?,
    val username: String?,
    val password: String,
    val entities: List<Entities>?,
)

@Serializable
data class Entities(
    val name: String?,
    val file: String?,
    val tableName: String?,
    val route: Route?,
    val primaryKey: PrimaryKey?,
    val entityFields: List<EntityFields>?
)

@Serializable
data class Route(
    val standardRoutes: List<String>?
)

@Serializable
data class PrimaryKey(
    val idName: String,
    val type: String
)

@Serializable
data class EntityFields(
    val variableName: String?,
    val columnName: String?,
    val type: String?,
    val length: String?,
    val fieldDetail: List<String>?
)

/*
    Socket Feature ------------------------------------------
 */

@Serializable
data class Socket(
    val path: String?,
    val name: String?,
    val webPath: String?,
    val answer: String?,
    val closeWord: String?,
    val closeMessage: String?,
)


class Tree(val root: Node) {

    fun prettyPrint(node: Node, index: Int, text: String): String {
        var name = node.name
        if (node.children.isEmpty()) {
            if (!node.name.endsWith(".kt"))
                name += ".kt"
        }
        val type = if (node.children.isEmpty()) " file" else " dir"
        var resultText = " ".repeat(index * 4) + name + type + "\n"
        node.children.forEach {
            resultText += prettyPrint(it, index + 1, resultText)
        }
        return resultText
    }

    fun writeToFile(path: String) {
        val file = File(path)
        file.createNewFile()
        file.writeText(prettyPrint(root, 0, ""))
    }

}

class Node(
    val name: String,
    val parent: Node?
) {
    var children = mutableListOf<Node>()
    fun addChild(node: Node) {
        children.add(node)
    }
}