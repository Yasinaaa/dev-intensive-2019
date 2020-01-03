package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/*
 * Created by yasina on 2020-01-03
*/

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}


fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val timeDiff = abs(this.time - date.time)
    val isFuture = this.time > date.time

    val text =  when {
        (timeDiff >= 0L) and (timeDiff <= SECOND) -> "только что"
        (timeDiff > SECOND) and (timeDiff <= 45 * SECOND) -> "несколько секунд"
        (timeDiff > 45 * SECOND) and (timeDiff <= 75 * SECOND) -> "минуту"
        (timeDiff > 75 * SECOND) and (timeDiff <= 45 * MINUTE) -> humanizeText(timeDiff / MINUTE, TimeUnits.MINUTE)
        (timeDiff > 45 * MINUTE) and (timeDiff <= 75 * MINUTE) -> "час"
        (timeDiff > 75 * MINUTE) and (timeDiff <= 22 * HOUR) -> humanizeText(timeDiff / HOUR, TimeUnits.HOUR)
        (timeDiff > 22 * HOUR) and (timeDiff <= 26 * HOUR) -> "день"
        (timeDiff > 26 * HOUR) and (timeDiff <= 360 * DAY) -> humanizeText(timeDiff / DAY, TimeUnits.DAY)
        else -> ""
    }

    return when(text) {
        "только что" -> text
        "" -> if (isFuture) "более чем через год" else "более года назад"
        else -> if (isFuture) "через $text" else "$text назад"
    }
}



private fun humanizeText(amount: Long, type: TimeUnits) : String{

    when (type) {

        TimeUnits.MINUTE -> return when {
            (amount % 10 == 1L) and (amount != 11L) -> "$amount минуту"
            (amount % 10 in 2L..4L) and (amount !in 12L..14L) -> "$amount минуты"
            else -> "$amount минут"
        }

        TimeUnits.HOUR -> return when {
            (amount % 10 == 1L) and (amount != 11L) -> "$amount час"
            (amount % 10 in 2L..4L) and (amount !in 12L..14L) -> "$amount часа"
            else -> "$amount часов"

        }

        else -> return if (amount <= 100)
            when {
                (amount % 10 == 1L) and (amount != 11L) -> "$amount день"
                (amount % 10 in 2L..4L) and (amount !in 12L..14L) -> "$amount дня"
                else -> "$amount дней"
            }
        else
            when {
                (amount % 10 == 1L) and (amount % 100 != 11L) -> "$amount день"
                (amount % 10 in 2L..4L) and (amount % 100 !in 12L..14L) -> "$amount дня"
                else -> "$amount дней"
            }
    }

}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String{
        val strValues = mapOf(
            SECOND to "секунд секунду секунды",
            MINUTE to "минут минуту минуты",
            HOUR to "часов час часа",
            DAY to "дней день дня"
        )
        var variant = 0
        when(value.toString().substring(value.toString().length-1).toInt()){
            1 -> variant = 1
            in (2..4) -> variant = 2
        }
        if(value in (11..14)) variant = 0
        return value.toString() + " " + strValues[this]!!.split(" ")[variant]
    }
}
