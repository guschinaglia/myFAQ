package com.myfaq.server.service

import com.myfaq.server.core.exception.NotFoundException
import com.myfaq.server.model.Topic
import com.myfaq.server.repository.ITopicRepository
import org.springframework.stereotype.Service

@Service
class TopicService(val topicRepository: ITopicRepository) : IBasicService<Topic> {

    override fun save(entity: Topic): Topic {
        return topicRepository.save(entity)
    }

    override fun saveAll(entities: MutableList<Topic>): MutableList<Topic> {
        return topicRepository.saveAll(entities)
    }

    override fun findAll(): MutableList<Topic> {
        return topicRepository.findAll()
    }

    override fun findById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {
            NotFoundException("Topic", id)
        }
    }
}