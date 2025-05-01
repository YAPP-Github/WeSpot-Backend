package com.wespot.common.util

import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import java.util.function.Supplier

object RandomGenerator {
    private val RANDOM = Random()
    private const val NULL_VALUE_PROBABILITY = 0.1

    fun generateNonNullString(length: Int): String {
        return RandomStringUtils.randomAlphabetic(length)
    }

    fun generateString(length: Int): String? {
        return generateNullAbleObject {
            RandomStringUtils.randomAlphabetic(
                length
            )
        }
    }

    fun generateNonNullNumeric(length: Int): Int {
        return RandomStringUtils.randomNumeric(length).toInt()
    }

    fun generateNumeric(length: Int): Int? {
        return generateNullAbleObject {
            RandomStringUtils.randomNumeric(
                length
            ).toInt()
        }
    }

    private fun <T> generateNullAbleObject(supplier: Supplier<T>): T? {
        val probability = RANDOM.nextDouble()

        if (probability < NULL_VALUE_PROBABILITY) {
            return null
        }

        return supplier.get()
    }

    fun generateNonNullDouble(numberLength: Int, decimalLength: Int): Double {
        return getRandomDouble(numberLength, decimalLength)
    }

    private fun getRandomDouble(numberLength: Int, decimalLength: Int): Double {
        return (RandomStringUtils.randomNumeric(numberLength) +
            "." + RandomStringUtils.randomNumeric(decimalLength)).toDouble()
    }

    fun generateDouble(numberLength: Int, decimalLength: Int): Double? {
        return generateNullAbleObject {
            getRandomDouble(
                numberLength,
                decimalLength
            )
        }
    }

    fun generateBoolean(): Boolean {
        return RANDOM.nextBoolean()
    }

    fun <ENUM : Enum<*>?> generateEnum(enumClass: Class<ENUM>): ENUM? {
        val enumConstants = enumClass.enumConstants
        return generateNullAbleObject { enumConstants[RANDOM.nextInt(enumConstants.size)] }
    }

    fun <ENUM : Enum<*>?> generateNonNullEnum(enumClass: Class<ENUM>): ENUM {
        val enumConstants = enumClass.enumConstants
        return enumConstants[RANDOM.nextInt(enumConstants.size)]
    }

    fun generateNonNullCharacter(): Char {
        return RandomStringUtils.randomAlphabetic(1)[0]
    }

    fun generateCharacter(): Char? {
        return generateNullAbleObject {
            RandomStringUtils.randomAlphabetic(
                1
            )[0]
        }
    }

    fun generateNonNullUUID(): UUID {
        return UUID.randomUUID()
    }

    fun generateUUID(): UUID? {
        return generateNullAbleObject { UUID.randomUUID() }
    }

}
