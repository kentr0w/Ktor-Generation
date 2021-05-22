package com.example.controllers

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.Car() {
    routing {
        route("/car") {
    get("/getCar") {
    call.respond("hehe")
}
post("/saveCar") {
    call.respond("hehe")
}

}
route("/electro") {
    get("getCar") {
    call.respond("hehe")
}

}

    }
}

fun Application.Static() {
    routing {
        static {
            resource("/hello", "template/index.html")
        }
    }
}
object UserObject : IdTable<Long>("User") {
        override val id = long("id").entityId().autoIncrement()
        override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
        val name = varchar("name",10).nullable()
val age = integer("age").nullable()

}


class UserEntity(id: EntityID<Long>) : Entity<Long>(id) {
    companion object : EntityClass<Long, UserEntity>(UserObject)

    var name by UserObject.name

var age by UserObject.age



    fun toUserDataClass(): UserDataClass = UserDataClass(id.toString().toLong(), name, age)
}


data class UserDataClass(
    val id: Long,
val name: String?,
val age: Int?,

)
fun Application.UserRoute() {
    routing {
        route("/User") {
            
get("/getUser") {
    call.respond(
        transaction {
            UserEntity.all().map { it.toUserDataClass() }
    })
}

post("/saveUser") {
    val userdataclass = call.receive<UserDataClass>()
    transaction {
        UserEntity.new {
            this.name = userdataclass.name
this.age = userdataclass.age

        }
    }
}

post("/updateUser") {
    val userdataclass = call.receive<UserDataClass>()
    transaction {
        UserEntity.findById(userdataclass.id)?.let {
            it.name = userdataclass.name
it.age = userdataclass.age

        } ?: throw NotFoundException()
    }
}

delete("/User/{id}") {
    val id = call.parameters["id"]?.toLongOrNull() ?: throw NotFoundException()
    transaction {
        UserEntity.findById(id)?.delete() ?: throw NotFoundException()
    }
}

        }
    }
}