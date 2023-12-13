package com.morozov.workarea.data.repositoryImpl


import com.apollographql.apollo3.ApolloClient

import javax.inject.Inject

class AccessToShopWebRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    AccessToShopWebRepository {
    override suspend fun hasAccessToShopWeb(): Boolean {
        val response = apolloClient.query(AccessToShopWebRequest()).await()
        return response.data?.hasAccessToShopWeb ?: false
    }
}