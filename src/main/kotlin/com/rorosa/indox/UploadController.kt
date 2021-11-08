package com.rorosa.indox

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


data class UploadFile(val file: MultipartFile)


@RestController
class UploadController(private val dbFileRepository: DBFileRepository) {

    @PostMapping("/upload",consumes = ["multipart/form-data"])
    fun upload(@ModelAttribute uploadFile: UploadFile) : Long {

        val dbFile = uploadFile.toDBFile()

        dbFileRepository.save(dbFile)

        return dbFile.id!!
    }

    @GetMapping("/file/{id}")
    fun getFile(@PathVariable id: Long): DBFile? {

        return dbFileRepository.findById(id).orElse(null)
    }

}