package com.morozov.workarea.api




// GraphQLQueries.kt


import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse


import kotlinx.coroutines.flow.Flow

//class GraphQLQueries(
//    private val apolloClient: ApolloClient
//) {
//    fun getTags(): Flow<ApolloResponse<GetTagsQuery.Data>> {
//        return apolloClient.query(GetTagsQuery()).toFlow()
//    }
//
//  fun hasFreeArticleAccess(): Flow<ApolloResponse<HasFreeArticleAccessQuery.Data>> {
//        return apolloClient.query(HasFreeArticleAccessQuery()).toFlow()
//    }
//
//   fun hasAccessToShopWeb(): Flow<ApolloResponse<HasAccessToShopWebQuery.Data>> {
//        return apolloClient.query(HasAccessToShopWebQuery()).toFlow()
//    }
//
//   fun getReaderPassPosts(): Flow<ApolloResponse<GetParselyTopPostsQuery.Data>> {
//        return apolloClient.query(GetParselyTopPostsQuery()).toFlow()
//    }
//}

