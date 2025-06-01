package com.parinexus.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parinexus.domain.ArticleUseCase
import com.parinexus.domain.model.DomainResultApi
import com.parinexus.domain.state.Resource
import com.parinexus.presentation.mapper.toPresentation
import com.parinexus.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase,
) : ViewModel() {

    private val nextPageMap = mutableMapOf<String, String?>()
    private val articleMap = mutableMapOf<String, MutableList<Article>>()
    private val isLastPageMap = mutableMapOf<String, Boolean>()

    private val _articles = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val articles: StateFlow<Resource<List<Article>>> = _articles.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds

    fun getArticles(category: String) {
        if (articleMap[category]?.isNotEmpty() == true && isLastPageMap[category] == true) {
            _articles.value = Resource.Success(articleMap[category] ?: emptyList())
            return
        }

        viewModelScope.launch {
            val nextPage = nextPageMap[category]
            val currentArticles = articleMap[category] ?: mutableListOf()

            _articles.value = Resource.Loading

            articleUseCase.getArticles(category, nextPage).collect { rs:Resource<DomainResultApi> ->
                when (rs) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val presentation = rs.data.toPresentation()
                        val newArticles = presentation.results
                        val updatedList = currentArticles.toMutableList().apply {
                            addAll(newArticles)
                        }

                        articleMap[category] = updatedList
                        nextPageMap[category] = presentation.nextPage
                        isLastPageMap[category] = newArticles.isEmpty()

                        _articles.value = Resource.Success(updatedList)
                    }
                    is Resource.Failed -> {
                        _articles.value = Resource.Failed(rs.message)
                    }
                }
            }
        }
    }

    fun refreshCategory(category: String) {
        nextPageMap.remove(category)
        articleMap.remove(category)
        isLastPageMap.remove(category)
        getArticles(category)
    }

    fun getCurrentData(category: String): List<Article> {
        return articleMap[category] ?: emptyList()
    }
}