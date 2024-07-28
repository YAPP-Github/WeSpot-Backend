package com.wespot

object CursorUtils {

    fun getEffectiveCursorId(cursorId: Long?): Long {
        return cursorId?.takeIf { it != 0L } ?: Long.MAX_VALUE
    }

}
