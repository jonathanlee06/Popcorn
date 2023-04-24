package com.jonathanlee.popcorn.ui.detail.cast.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.ui.common.CastListToolbar
import com.jonathanlee.popcorn.ui.theme.PopcornTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CastListPage(
    castList: List<CastItem.Item> = emptyList(),
    onBackClick: (() -> Unit)? = null,
    onItemClick: ((Cast) -> Unit)? = null,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    PopcornTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CastListToolbar(
                    title = "Cast List",
                    onBackClick = { onBackClick?.invoke() }
                )
            },
            content = { contentPadding ->
                CastList(
                    castList = castList,
                    onClick = { onItemClick?.invoke(it) },
                    contentPadding = contentPadding
                )
            }
        )
    }
}