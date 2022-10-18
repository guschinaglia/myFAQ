package com.myfaq.server.service

interface IBasicService<T> {
    fun save(entity: T): T
    fun saveAll(entities: MutableList<T>): MutableList<T>
    fun findAll(): MutableList<T>
    fun findById(id: Long): T
}
