package com.rorosa.indox

import com.fasterxml.jackson.core.util.ByteArrayBuilder
import com.jillesvangurp.eskotlinwrapper.dsl.match
import com.jillesvangurp.eskotlinwrapper.dsl.queryString
import org.elasticsearch.action.search.configure
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.search.SearchHit
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.websocket.server.PathParam


data class UploadFile(val file: MultipartFile)


@RestController
class UploadController(
    private val dbFileRepository: DBFileRepository,
    ) {

    @PostMapping("/upload",consumes = ["multipart/form-data"])
    fun upload(@ModelAttribute uploadFile: UploadFile) : Long {

        val dbFile = uploadFile.toDBFile()

        val elasticFile = uploadFile.toElasticFile()
        dbFileRepository.save(dbFile)

        esFileRepository.index(obj = elasticFile,pipeline = ELASTIC_PIPELINE).runCatching {
            this.result?.let {
                println(this.id)
            }
        }
        return dbFile.id!!
    }

    @GetMapping("/file/{id}")
    fun getFile(@PathVariable id: Long): ByteArray? {

        return dbFileRepository.findById(id).map {
            it.file
        }.orElse(null)
    }

    @GetMapping("/search")
    fun search(@PathParam("searchTerm") searchTerm: String): List<ElasticFile> {
        val searchResult = esFileRepository.search {
            configure {
                query = match( "attachment.content", query = searchTerm)
            }
        }

        return searchResult.hits.map { it.second }.toList()
    }

    @DeleteMapping
    fun deleteAll() {
        esFileRepository.deleteIndex()
    }



}