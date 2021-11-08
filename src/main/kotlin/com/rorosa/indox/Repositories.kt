package com.rorosa.indox

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface DBFileRepository: CrudRepository<DBFile,Long>

interface ElasticFileRepository: ElasticsearchRepository<ElasticFile,String>