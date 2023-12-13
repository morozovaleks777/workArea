package com.morozov.workarea.presentation.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozov.workarea.data.ReaderPassPost
import com.morozov.workarea.domain.useCases.AccessToShopWebUseCase
import com.morozov.workarea.domain.useCases.FreeArticleUseCase
import com.morozov.workarea.domain.useCases.GetTagsUseCase
import com.morozov.workarea.domain.useCases.ReaderPassParselyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val freeArticleUseCase: FreeArticleUseCase,
    private val accessToShopWebUseCase: AccessToShopWebUseCase,
    private val readerPassParselyUseCase: ReaderPassParselyUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val tagsResponse = getTagsUseCase.execute().first()
                val freeArticleAccess = freeArticleUseCase.execute().first()
                val accessToShopWeb = accessToShopWebUseCase.execute().first()
                val readerPassPosts = readerPassParselyUseCase.execute().first()

                _state.value = HomeScreenState(
                    tags = tagsResponse.data?.tags?.mapNotNull { it?.name} ?: emptyList(),
                    hasFreeArticleAccess = freeArticleAccess.data?.hasFreeArticleAccess ?: false,
                    hasAccessToShopWeb = accessToShopWeb.data?.hasAccessToShopWeb ?: false,
                    readerPassPosts = readerPassPosts.data?.posts?.mapNotNull {
                        it?.let { post ->
                            ReaderPassPost(
                                id = post.id ?: "",
                                title = post.title ?: "",
                                url = post.url ?: "",
                                image = post.image ?: "",
                                author = post.author ?: "",
                                date = post.date ?: ""
                            )
                        }
                    } ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }



}

data class HomeScreenState(
    val tags: List<String> = emptyList(),
    val hasFreeArticleAccess: Boolean = false,
    val hasAccessToShopWeb: Boolean = false,
    val readerPassPosts: List<ReaderPassPost> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)