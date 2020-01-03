package ru.skillbranch.devintensive.models

/*
 * Created by yasina on 2020-01-03
*/
class Chat (
    val id : String,
    val members : MutableList<User> = mutableListOf(),
    val messages : MutableList<BaseMessage> = mutableListOf()
)