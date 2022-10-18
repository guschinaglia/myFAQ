package com.myfaq.server.common.messages

object CommonMessages {
    fun alreadyExists(entity: String, value: String): String {
        return "Already exists an $entity param with name: [ $value ]"
    }

    fun alreadyExists(entity: String, field: String, value: String): String {
        return "Already exists an $entity with field $field = $value"
    }

    fun entityNotFound(entity: String, value: String): String {
        return "Entity $entity with id [ $value ] not found in database"
    }

    fun addNewNotAllowed(entity: String, value: String): String {
        return "Not allowed to add value [ $value ] into $entity"
    }

    fun alreadyExistsInList(
        baseEntity: String,
        secondaryEntity: String,
        baseValue: String,
        secondaryValue: String
    ): String {
        return "Not allowed to add $secondaryEntity: [ $secondaryValue ] into ->  $baseEntity: [ $baseValue ]"
    }

    fun missingMandatory(entity: String, field: String): String {
        return "Missing mandatory field $field in entity $entity"
    }
}