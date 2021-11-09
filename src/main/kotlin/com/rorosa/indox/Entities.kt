package com.rorosa.indox

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob


@Entity
class DBFile (@Id @GeneratedValue var id: Long?, var fileName: String, @Lob var file: ByteArray)

data class ElasticFile(
    val data: String,
    val fileName: String,
    val attachment: Map<String,Any>?
)