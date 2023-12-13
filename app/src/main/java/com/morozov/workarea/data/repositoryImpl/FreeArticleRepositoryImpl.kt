package com.morozov.workarea.data.repositoryImpl


import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject

class FreeArticleRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    FreeArticleRepository {
    override suspend fun hasFreeArticleAccess(): Boolean {
        val response = apolloClient.query(FreeArticleQuery()).await()
        return response.data?.hasFreeArticleAccess ?: false
    }
}