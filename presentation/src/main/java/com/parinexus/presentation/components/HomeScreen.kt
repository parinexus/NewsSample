package com.parinexus.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parinexus.domain.state.Resource
import com.parinexus.presentation.model.Article
import com.parinexus.presentation.ui.theme.button
import com.parinexus.presentation.viewModel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit
) {
    val context = LocalContext.current
    val articleState by viewModel.articles.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory
    val favoriteCategories by viewModel.favoriteCategoriesFlow.collectAsStateWithLifecycle(emptyList())
    val categories = listOf("General", "Technology", "Sports", "Business", "Entertainment", "Health", "Science")

    var dropdownExpanded by remember { mutableStateOf(false) }

    val articles = when (articleState) {
        is Resource.Success -> (articleState as Resource.Success).data
        else -> emptyList()
    }

    LaunchedEffect(selectedCategory) {
        viewModel.loadArticlesForSelectedCategory()
    }

    LaunchedEffect(articleState) {
        if (articleState is Resource.Failed) {
            Toast.makeText(context, (articleState as Resource.Failed).message ?: "Failed to load", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text("📰 NewsApp", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            },
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { dropdownExpanded = true }
                        .background(button, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = selectedCategory, color = Color.White)
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    val isFav = category in favoriteCategories
                                    Text(if (isFav) "★ $category" else category)
                                },
                                onClick = {
                                    viewModel.refreshCategory(category)
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        )

        when (articleState) {
            is Resource.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Loading news...", fontSize = 16.sp)
                }
            }

            is Resource.Failed -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Failed to load news.", color = Color.Red)
                }
            }

            is Resource.Success -> {
                if (articles.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No articles available.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(articles) { article ->
                            NewsCard(article = article, onClick = { onArticleClick(article) })
                        }
                    }
                }
            }
        }
    }
}
