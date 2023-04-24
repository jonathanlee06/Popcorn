package com.jonathanlee.popcorn.ui.detail.cast.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.compose.cast
import com.jonathanlee.popcorn.data.compose.castList
import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.ui.theme.BrandContainer
import com.jonathanlee.popcorn.ui.theme.BrandSurface
import com.jonathanlee.popcorn.ui.theme.PopcornTheme


@Preview
@Composable
fun CastListPreview() {
    PopcornTheme {
        CastList(castList = castList, onClick = {})
    }
}

@Preview
@Composable
fun CastItemPreview() {
    PopcornTheme {
        CastListItem(cast = cast, onClick = {})
    }
}

@Composable
fun DetailCastItem(
    cast: Cast,
    onClick: (Cast) -> Unit,
) {
    BaseCastItem(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp),
        cast = cast,
        onClick = { onClick(cast) }
    )
}

@Composable
fun CastListItem(
    cast: Cast,
    onClick: (Cast) -> Unit,
) {
    BaseCastItem(
        modifier = Modifier
            .height(250.dp),
        cast = cast,
        onClick = { onClick(cast) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseCastItem(
    modifier: Modifier,
    cast: Cast,
    onClick: (Cast) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick(cast) }
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BrandSurface)
            ) {
                if (cast.profilePath.isNullOrEmpty()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.logo_white),
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Api.getPosterPath(cast.profilePath))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.popcorn_placeholder),
                    contentDescription = "Poster Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xBF151515),
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(3.dp),
                        text = cast.name,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (cast.character.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            text = cast.character,
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CastList(
    castList: List<CastItem.Item>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (Cast) -> Unit,
) {
    val newTopPadding = contentPadding.calculateTopPadding() + 16.dp
    LazyVerticalGrid(
        modifier = Modifier
            .background(color = BrandContainer)
            .padding(start = 16.dp, end = 16.dp),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(top = newTopPadding, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(castList) { castItem ->
            CastListItem(cast = castItem.cast) {
                onClick(it)
            }
        }
    }
}