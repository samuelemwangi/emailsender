package app.emailsender.utils

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlin.random.Random

internal fun randomString(
    length: Int = 5,
    includeUpperCase: Boolean = false,
    includeNumbers: Boolean = false
): String {

    require(length >= 1) { "length must be greater than 0" }

    val chars = ('a'..'z') +
            (if (includeUpperCase) ('A'..'Z') else emptyList()) +
            if (includeNumbers) ('0'..'9') else emptyList()

    return (1..length)
        .map { chars.random() }
        .joinToString("")
}

internal fun randomInt(
    min: Int = 0,
    max: Int = 100
): Int {
    return (min..max).random()
}

internal fun randomLong(
    min: Long = 0,
    max: Long = 100
): Long {
    return (min..max).random()
}

internal fun randomFloat(
    min: Int = 0,
    max: Int = 100,
    precision: Float = 0.01f
): Float {
    require(min < max) { "Invalid range: min must be less than max" }
    require(precision > 0) { "Invalid precision: precision must be greater than zero" }

    val range = max - min
    val randomFloat = Random.nextFloat() * range + min
    val roundedFloat = (randomFloat * (1.0 / precision)).toLong() / (1.0 / precision)
    return roundedFloat.toFloat()
}

internal fun randomSentence(
    wordCount: Int = 3,
    randomizeWordLength: Boolean = false
): String {

    var sentence = ""

    require(wordCount >= 1) { "wordCount must be greater than 0" }

    for (i in 1..wordCount) {
        val wordLength = if (randomizeWordLength) randomInt(3, 10) else 5
        sentence += " " + randomString(wordLength)
    }

    return (sentence[1].uppercase() + sentence.substring(1)).trim()
}

internal fun randomUUID() = UUID.randomUUID().toString()

internal fun randomDateTime(
    deltaVector: Int = 0,
    deltaType: ChronoUnit = ChronoUnit.DAYS,
    deltaValue: Long = randomLong()
) = when (deltaVector) {
    0 -> LocalDateTime.now()
    else -> deltaVector.let {
        if (it > 0) LocalDateTime.now().plus(deltaValue, deltaType)
        else LocalDateTime.now().minus(deltaValue, deltaType)
    }
}
