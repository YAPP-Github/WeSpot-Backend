package com.wespot.image

import com.wespot.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "image", uniqueConstraints = [UniqueConstraint(columnNames = ["url"])])
class ImageJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val url: String,

    @Embedded
    val baseEntity: BaseEntity,
) {

}
