package com.morozov.workarea.domain.useCases


import com.apollographql.apollo3.api.Response
import com.morozov.workarea.data.ReaderPassPost
import com.morozov.workarea.domain.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// UseCase.kt
class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    fun execute(): Flow<Response<GetTagsQuery.Data>> {
        return tagRepository.getTags()
    }
}


// FreeArticleUseCase.kt
class FreeArticleUseCase @Inject constructor(private val freeArticleRepository: FreeArticleRepository) {
    suspend operator fun invoke(): Boolean {
        return freeArticleRepository.hasFreeArticleAccess()
    }
}

// AccessToShopWebUseCase.kt
class AccessToShopWebUseCase @Inject constructor(private val accessToShopWebRepository: AccessToShopWebRepository) {
    suspend operator fun invoke(): Boolean {
        return accessToShopWebRepository.hasAccessToShopWeb()
    }
}

// ReaderPassParselyUseCase.kt
class ReaderPassParselyUseCase @Inject constructor(private val readerPassParselyRepository: ReaderPassParselyRepository) {
    suspend operator fun invoke(): List<ReaderPassPost> {
        return readerPassParselyRepository.getReaderPassParsely()
    }
}
