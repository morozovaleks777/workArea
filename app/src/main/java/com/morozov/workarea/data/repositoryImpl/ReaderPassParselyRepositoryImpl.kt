package com.morozov.workarea.data.repositoryImpl


import com.apollographql.apollo3.ApolloClient
import com.morozov.workarea.data.ReaderPassPost
import javax.inject.Inject

class ReaderPassParselyRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    ReaderPassParselyRepository {
    override suspend fun getReaderPassParsely(): List<ReaderPassPost> {
        val response = apolloClient.query(ReaderPassParselyQuery()).await()
        return response.data?.posts?.map { it ->
            ReaderPassPost(
                id = it.id,
                title = it.title,
                url = it.url,
                image = it.image,
                author = it.author,
                date = it.date
            )
        } ?: emptyList()
    }
}