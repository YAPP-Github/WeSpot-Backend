package com.wespot.vote

import java.time.LocalDateTime

data class Vote(
    val id: Long,
    val schoolName: String,
    val grade: Int,
    val groupNumber: Int,
    val date: LocalDateTime,
    val ballots: List<Ballot>
) {
}