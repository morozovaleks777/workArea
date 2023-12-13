package com.morozov.workarea.data.repositoryImpl

import com.apollographql.apollo3.api.Response
import com.morozov.workarea.api.GraphQLQueries
import com.morozov.workarea.domain.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TagRepositoryImpl @Inject constructor(
    private val graphQLQueries: GraphQLQueries
) : TagRepository {
    override fun getTags(): Flow<Response<GetTagsQuery.Data>> {
        return graphQLQueries.getTags()
    }
}