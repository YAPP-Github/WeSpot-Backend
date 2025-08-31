package com.wespot.report

object ReportReasonMapper {

    fun toDomain(entity: ReportReasonJpaEntity): ReportReason {
        return ReportReason(
            id = entity.id,
            content = entity.content,
            canReceiveReason = entity.canReceiveReason
        )
    }

    fun toEntity(domain: ReportReason): ReportReasonJpaEntity {
        return ReportReasonJpaEntity(
            id = domain.id,
            content = domain.content,
            canReceiveReason = domain.canReceiveReason
        )
    }

}
