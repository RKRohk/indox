package com.rorosa.indox

import com.jillesvangurp.eskotlinwrapper.IndexRepository
import com.jillesvangurp.eskotlinwrapper.dsl.match
import org.elasticsearch.action.search.configure
import org.elasticsearch.client.indexRepository
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.websocket.server.PathParam


data class UploadFile(val file: MultipartFile)


@RestController
class UploadController {

    private val esFileRepository: IndexRepository<ElasticFile> = restHighLevelClient.indexRepository("documents")


    @PostMapping("/upload", consumes = ["multipart/form-data"])
    fun upload(@ModelAttribute uploadFile: UploadFile): String {


        val elasticFile = uploadFile.toElasticFile()

        val indexResult = esFileRepository.index(obj = elasticFile, pipeline = ELASTIC_PIPELINE)

        return indexResult.id
    }

    @GetMapping("/file/{id}")
    fun getFile(@PathVariable id: String): Resource? {

        return esFileRepository.get(id)?.let {
            val byteArray = Base64.getDecoder().decode(it.data)
            InputStreamResource(byteArray.inputStream())
        }
    }

    @GetMapping("/search")
    fun search(@PathParam("searchTerm") searchTerm: String): List<ElasticFile> {
        val searchResult = esFileRepository.search {
            configure {
                query = match("attachment.content", query = searchTerm)
            }
        }

        return searchResult.hits.map { it.second }.toList()
    }

    @DeleteMapping
    fun deleteAll() {
        esFileRepository.deleteIndex()
    }


}