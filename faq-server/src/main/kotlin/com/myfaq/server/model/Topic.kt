package com.myfaq.server.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Calendar

@Document
data class Topic(
    var title: String,
    var answer: String
) {
    @Id
    var id: String? = null

    var tags: List<String>? = null

    val createdDate: LocalDateTime = LocalDateTime.now()
}