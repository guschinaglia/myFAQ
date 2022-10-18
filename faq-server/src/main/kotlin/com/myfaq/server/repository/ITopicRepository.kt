package com.myfaq.server.repository

import com.myfaq.server.model.Topic
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ITopicRepository : MongoRepository<Topic, Long> {
}