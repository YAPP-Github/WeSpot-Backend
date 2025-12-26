package com.wespot.vote

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
@Table(name = "vote")
class VoteJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val schoolId: Long,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val classNumber: Int,

    @field: NotNull
    val voteNumber: Int,

    @field: NotNull
    val date: LocalDate,

    ) {

    override fun toString(): String {
        return "VoteJpaEntity(id=$id, schoolId=$schoolId, grade=$grade, classNumber=$classNumber, voteNumber=$voteNumber, date=$date)"
    }

}
