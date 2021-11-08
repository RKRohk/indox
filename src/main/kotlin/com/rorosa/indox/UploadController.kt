package com.rorosa.indox

import com.fasterxml.jackson.core.util.ByteArrayBuilder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


data class UploadFile(val file: MultipartFile)


@RestController
class UploadController(
    private val dbFileRepository: DBFileRepository,
    private val elasticFileRepository: ElasticFileRepository
    ) {

    @PostMapping("/upload",consumes = ["multipart/form-data"])
    fun upload(@ModelAttribute uploadFile: UploadFile) : Long {

        val dbFile = uploadFile.toDBFile()

        val elasticFile = uploadFile.toElasticFile()
        dbFileRepository.save(dbFile)

        elasticFileRepository.save(elasticFile)

        println(elasticFile.id)
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
        elasticFileRepository.deleteAll()
    }



}