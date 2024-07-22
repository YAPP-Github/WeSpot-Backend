package com.wespot.vote

data class Rate(
    val value: Int
) {

    companion object {

        fun from(value: Int): Rate {
            validate(value)
            return Rate(value)
        }

        private fun validate(value: Int) {
            if (0 < value) {
                return
            }
            throw IllegalArgumentException("등수는 0 이하일 수 없습니다.")
        }

        fun createMeaningLessRate(): Rate {
            return Rate(Int.MAX_VALUE)
        }

    }

}
