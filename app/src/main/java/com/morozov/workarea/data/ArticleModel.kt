package com.morozov.workarea.data


// Tag.kt
data class Tag(val name: String)

// FreeArticle.kt
data class FreeArticleResponse(val hasFreeArticleAccess: Boolean)

// AccessToShopWeb.kt
data class AccessToShopWebResponse(val hasAccessToShopWeb: Boolean)

// ReaderPassParsely.kt
data class ReaderPassParselyResponse(val posts: List<ReaderPassPost>)
data class ReaderPassPost(val id: String, val title: String, val url: String, val image: String, val author: String, val date: String)
