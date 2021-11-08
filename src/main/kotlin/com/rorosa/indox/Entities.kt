package com.rorosa.indox

import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob


@Entity
class DBFile (@Id @GeneratedValue var id: Long?, var fileName: String, @Lob var file: ByteArray)

@Document(indexName = "documents")
data class ElasticFile(
    @org.springframework.data.annotation.Id val id: String? = null,
    val file: ByteArray,
    val fileName: String
)