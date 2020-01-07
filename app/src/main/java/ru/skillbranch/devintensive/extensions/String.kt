package ru.skillbranch.devintensive.extensions

/*
 * Created by yasina on 2020-01-03
*/
fun String.truncate(length: Int = 16)
        = if (this.trim().length <= length) this.trim() else this.trim().substring(0, length).trim() + "..."

fun String.stripHtml()
        = this.replace(Regex("(<.*?>)|(&[^ а-я]{1,4}?;)"), "").replace(Regex(" {2,}"), " ")