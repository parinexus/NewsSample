package com.parinexus.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parinexus.domain.state.Resource
import com.parinexus.domain.usecase.ArticleUseCase
import com.parinexus.domain.usecase.FavoriteCategoryUseCase
import com.parinexus.presentation.mapper.toPresentation
import com.parinexus.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase,
    private val favoriteCategoryUseCase: FavoriteCategoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val nextPageMap = mutableMapOf<String, String?>()
    private val articleMap = mutableMapOf<String, MutableList<Article>>()
    private val isLastPageMap = mutableMapOf<String, Boolean>()

    private val _articles = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val articles: StateFlow<Resource<List<Article>>> = _articles.asStateFlow()

    val favoriteCategoriesFlow = favoriteCategoryUseCase.favorites

    private val _selectedCategory = mutableStateOf(savedStateHandle["selectedCategory"] ?: "Sports")
    val selectedCategory: State<String> = _selectedCategory

    private fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        loadArticlesForSelectedCategory()
    }

    fun loadArticlesForSelectedCategory() {
        val category = selectedCategory.value

        viewModelScope.launch {
            if (articleMap[category]?.isNotEmpty() == true && isLastPageMap[category] == true) {
                _articles.value = Resource.Success(articleMap[category] ?: emptyList())
                return@launch
            }

            val nextPage = nextPageMap[category]
            val currentArticles = articleMap[category]?.toMutableList() ?: mutableListOf()

            _articles.value = Resource.Loading

            articleUseCase.getArticles(category, nextPage).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val presentation = result.data.toPresentation()
                        val newArticles = presentation.results
                        currentArticles += newArticles

                        articleMap[category] = currentArticles
                        nextPageMap[category] = presentation.nextPage
                        isLastPageMap[category] = newArticles.isEmpty()

                        _articles.value = Resource.Success(currentArticles)
                    }

                    is Resource.Failed -> {
                        _articles.value = Resource.Failed("Failed to fetch articles for $category")
                    }
                }
            }
        }
    }

    fun refreshCategory(category: String) {
        nextPageMap.remove(category)
        articleMap.remove(category)
        isLastPageMap.remove(category)
        setSelectedCategory(category)
    }
}