package com.jonathanlee.popcorn.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jonathanlee.popcorn.data.model.network.SearchModel
import com.jonathanlee.popcorn.ui.theme.BrandContainer
import com.jonathanlee.popcorn.ui.theme.BrandPrimary
import com.jonathanlee.popcorn.ui.theme.PopcornTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchPage(
    viewModel: SearchViewModel = viewModel(),
    onClick: ((SearchModel) -> Unit)? = null,
) {
    val searchResult: List<SearchModel> by viewModel.searchResult.observeAsState(listOf())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    PopcornTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                SearchBar(
                    hint = "Search artists, movie, TV show...",
                    onSearch = {
                        viewModel.setQuery(it)
                    },
                    modifier = Modifier.padding(10.dp)
                )
            },
            content = { contentPadding ->
                SearchList(
                    searchList = searchResult,
                    contentPadding = contentPadding,
                    onClick = { onClick?.invoke(it) }
                )
            }
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint.isNotEmpty())
    }

    Box(modifier = modifier.background(Color.Transparent)) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            cursorBrush = SolidColor(BrandPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isHintDisplayed = it.isFocused.not()
                },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = BrandContainer,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(all = 16.dp), // inner padding
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                            .background(
                                color = BrandContainer,
                                shape = RoundedCornerShape(size = 16.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Favorite icon",
                            tint = Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        if (isHintDisplayed) {
                            Text(
                                text = hint,
                                color = Color.LightGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        innerTextField()
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .clickable(
                                onClick = { text = "" },
                                role = Role.Button
                            ),
                        horizontalAlignment = Alignment.End
                    ) {
                        if (text.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear icon",
                                tint = Color.LightGray,
                            )
                        }
                    }
                }
            }
        )
    }
}