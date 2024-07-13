package com.wespot.vote.port.out

import com.wespot.vote.Vote

interface VotePort {

    fun findBySchoolIdAndGradeAndGroupNumberAndDate(): Vote?

    fun save(vote: Vote);

}