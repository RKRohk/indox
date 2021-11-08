package com.rorosa.indox

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
    return ElasticFile(fileName = file.originalFilename ?: file.name, file = file.bytes)
}