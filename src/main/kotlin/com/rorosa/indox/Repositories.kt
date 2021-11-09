package com.rorosa.indox

import org.elasticsearch.client.create
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface DBFileRepository: CrudRepository<DBFile,Long>

val restHighLevelClient = create()