package com.jonathanlee.popcorn.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.compose.searchList
import com.jonathanlee.popcorn.data.model.network.SearchModel
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.ui.theme.BrandContainer
import com.jonathanlee.popcorn.ui.theme.BrandSurface
import com.jonathanlee.popcorn.ui.theme.PopcornTheme

@Preview
@Composable
private fun SearchItemPreview() {
    PopcornTheme {
        SearchList(searchList = searchList)
    }
}

@Composable
private fun SearchItem(
    searchModel: SearchModel,
    onClick: ((SearchModel) -> Unit)? = null,
) {
    val imagePath = listOfNotNull(searchModel.posterPath, searchModel.profilePath).firstOrNull()
    val searchTitle = listOfNotNull(
        searchModel.name,
        searchModel.originalName,
        searchModel.originalTitle,
        searchModel.title
    ).firstOrNull()
    Card(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(containerColor = BrandContainer),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick?.invoke(searchModel) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(100.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (imagePath.isNullOrEmpty()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.logo_white),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Api.getSearchPath(imagePath))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.logo_white),
                    contentDescription = "Poster Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = searchTitle ?: "",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SearchList(
    searchList: List<SearchModel>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: ((SearchModel) -> Unit)? = null,
) {
    if (searchList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BrandSurface)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "No search result",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    } else {
        val newTopPadding = contentPadding.calculateTopPadding() + 16.dp
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BrandSurface)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(top = newTopPadding, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(searchList) { searchModel ->
                SearchItem(searchModel = searchModel) {
                    onClick?.invoke(it)
                }
            }
        }
    }
}