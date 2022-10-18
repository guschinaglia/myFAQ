package com.myfaq.server.controller

import com.myfaq.server.model.Topic
import com.myfaq.server.service.TopicService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topic")
class TopicController(var topicService: TopicService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveNewTopic(@RequestBody topic: Topic): ResponseEntity<Topic> {
        val saved = topicService.save(topic)
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllTopics(): ResponseEntity<MutableList<Topic>> {
        val topics = topicService.findAll()
        return ResponseEntity.ok().body(topics)
    }

}