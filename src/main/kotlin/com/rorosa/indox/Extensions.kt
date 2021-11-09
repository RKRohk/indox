package com.rorosa.indox

import java.util.*

/**
 * Extension function to convert Data Class UploadFile to DBFile
 * so that it can be stored in SQL
 *
 * @return DBFile to be stored using JPA
 */
fun UploadFile.toDBFile(): DBFile {
    return DBFile(id = null,fileName = file.originalFilename ?: file.name ,file = file.bytes)
}

fun UploadFile.toElasticFile(): ElasticFile {
    return ElasticFile(fileName = file.originalFilename ?: file.name, data = file.bytes.toBase64(),attachment = null)
}

fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)