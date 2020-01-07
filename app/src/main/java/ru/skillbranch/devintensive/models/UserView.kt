package ru.skillbranch.devintensive.models

/*
 * Created by yasina on 2020-01-03
*/
class UserView(

    val id: String,
    val fullName: String,
    var nickName: String,
    var avatar: String? = null,
    var status: String = "offline",
    val initials: String?

) {

    fun printMe() {
        println("""
            id: $id
            fullName: $fullName
            nickName: $nickName
            avatar: $avatar
            status: $status
            initials: $initials
        """.trimIndent()
        )
    }

}