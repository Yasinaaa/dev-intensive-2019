package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.utils.LatinAlphabet.transliterationMap

/*
 * Created by yasina on 2020-01-03
*/
object Utils {

    fun parseFullName(fullName: String?):Pair<String?, String?>{
        val parts : List<String>? = fullName?.trim()?.split(" ")
        var firstName = parts?.getOrNull(0)
        if(firstName.isNullOrEmpty() || firstName.isNullOrBlank()) {
            firstName = null
        }
        var lastName = parts?.getOrNull(1)
        if(lastName.isNullOrEmpty() || lastName.isNullOrBlank()) {
            lastName = null
        }
        return firstName to lastName // Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?) : String? {
        if (firstName?.trim().isNullOrBlank() && lastName?.trim().isNullOrBlank()) return null
        val firstInitial = firstName?.trim()?.get(0)?.toUpperCase() ?: ""
        val secondInitial = lastName?.trim()?.get(0)?.toUpperCase() ?: ""
        return "$firstInitial$secondInitial"
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var processedString = payload.trim()
        val charSet = processedString.toSet()
        for (char in charSet) {
            if (transliterationMap.containsKey(char.toLowerCase())) {
                processedString =
                    if (char.isUpperCase())
                        processedString.replace("$char".toRegex(), transliterationMap.getValue(char.toLowerCase()).capitalize())
                    else
                        processedString.replace("$char".toRegex(), transliterationMap.getValue(char))

            }
        }
        return processedString.replace(" ", divider)

    }
}