package com.morozov.workarea.presentation.screens.homeScreen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozov.workarea.data.ReaderPassPost
import com.morozov.workarea.domain.useCases.AccessToShopWebUseCase
import com.morozov.workarea.domain.useCases.FreeArticleUseCase
import com.morozov.workarea.domain.useCases.GetTagsUseCase
import com.morozov.workarea.domain.useCases.ReaderPassParselyUseCase
import com.morozov.workarea.presentation.screens.auth.AuthNone
import com.morozov.workarea.presentation.screens.auth.AuthStepState
import com.morozov.workarea.presentation.screens.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val freeArticleUseCase: FreeArticleUseCase,
    private val accessToShopWebUseCase: AccessToShopWebUseCase,
    private val readerPassParselyUseCase: ReaderPassParselyUseCase
) : ViewModel() {


    private val _error = MutableStateFlow<Throwable?>(null)
    private val _stepState = MutableStateFlow<AuthStepState>(AuthNone)
    private val _action = MutableStateFlow<AuthViewModel.AuthUiState.AuthAction>(AuthViewModel.AuthUiState.None)
    private val _direction = MutableStateFlow<AuthViewModel.AuthUiState.Direction>(AuthViewModel.AuthUiState.Next())
    private val _requireAction = MutableStateFlow<HomeUiState.HomeAction>(HomeUiState.None)
    private val _state = MutableStateFlow(HomeUiState())
   // val state: StateFlow<HomeUiState> = _state

    private val _isLoading = MutableStateFlow(false)

    init{fetchData()
    }

    val homeUiState: StateFlow<HomeUiState> = combine(
        _state,
        _isLoading,
        _requireAction,

    ) { homeScreenState, loading, action ->
        HomeUiState(
            tags = homeScreenState.tags,
            hasFreeArticleAccess = homeScreenState.hasFreeArticleAccess,
            hasAccessToShopWeb = homeScreenState.hasAccessToShopWeb,
            readerPassPosts = homeScreenState.readerPassPosts,
            isLoading = loading,
            error = homeScreenState.error,
            action = action
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true


            //    val freeArticleAccess = freeArticleUseCase.execute().take(1).firstOrNull()
//                val accessToShopWeb = accessToShopWebUseCase.execute().take(1).firstOrNull()
                val readerPassPosts = readerPassParselyUseCase.execute().first()

                val tagsResponse = getTagsUseCase.execute().first()

                // Обробка різних типів даних
                _state.value =  HomeUiState(
                        readerPassPosts = readerPassPosts.data!!.posts?.mapNotNull {
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
                    tags = tagsResponse.data!!.tags?.mapNotNull { it?.name } ?: emptyList(),
                        isLoading = false,
                        error = null
                    )





//                    freeArticleAccess?.data != null -> HomeUiState(
//                        hasFreeArticleAccess = freeArticleAccess.data!!.hasFreeArticleAccess ?: false,
//                        isLoading = false,
//                        error = null
//                    )
//                    accessToShopWeb?.data != null -> HomeUiState(
//                        hasAccessToShopWeb = accessToShopWeb.data!!.hasAccessToShopWeb ?: false,
//                        isLoading = false,
//                        error = null
//                    )

//                    else -> {
//                        Log.d("home", "fetchData: ${tagsResponse.data!!.tags?.mapNotNull { it?.name } ?: emptyList()}")
//                        _state.value.copy(tags   = listOf<String>("pisdec"))}// Якщо нічого не отримано, залиште поточний стан
//                }
//
//                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }
    fun onActionHandled() {
        _requireAction.value = HomeUiState.None
    }
}

@Immutable
data class HomeUiState(
    val tags: List<String> = emptyList(),
    val hasFreeArticleAccess: Boolean = false,
    val hasAccessToShopWeb: Boolean = false,
    val readerPassPosts: List<ReaderPassPost> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,


    val action: HomeAction = None,
){
    sealed class HomeAction
    object OpenProfilePage : HomeAction()


    object OpenPrivacy : HomeAction()
    object None : HomeAction()

    data class RequestPaywall(val isUserAuthorized: Boolean) : HomeAction()
}


