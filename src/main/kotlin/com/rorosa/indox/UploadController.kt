package com.rorosa.indox

import com.fasterxml.jackson.core.util.ByteArrayBuilder
import org.elasticsearch.client.RequestOptions
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


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

    @DeleteMapping
    fun deleteAll() {
        esFileRepository.deleteIndex()
    }



}