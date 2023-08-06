package com.jonathanlee.popcorn.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.ui.theme.BrandContainer
import com.jonathanlee.popcorn.ui.theme.PopcornTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CastListToolbarPreview() {
    PopcornTheme {
        CastListToolbar(
            title = "Cast List",
            onBackClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailToolbarPreview() {
    PopcornTheme {
        MainToolbar(
            onActionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainToolbarPreview() {
    PopcornTheme {
        MainToolbar(
            onActionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastListToolbar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: String,
    onBackClick: () -> Unit,
) {
    BaseToolbar(
        scrollBehavior = scrollBehavior,
        title = title,
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onActionClick: () -> Unit,
) {
    BaseToolbar(
        modifier = Modifier.background(BrandContainer),
        scrollBehavior = scrollBehavior,
        title = "Popcorn",
        logoTitle = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_white),
                contentDescription = "logo title"
            )
        },
        actions = {
            IconButton(onClick = { onActionClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Button"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainToolbar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onActionClick: () -> Unit,
) {
    BaseToolbar(
        modifier = Modifier.background(BrandContainer),
        scrollBehavior = scrollBehavior,
        title = "Popcorn",
        logoTitle = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_white),
                contentDescription = "logo title"
            )
        },
        actions = {
            IconButton(onClick = { onActionClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Button"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseToolbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: String,
    logoTitle: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            if (logoTitle == null) {
                Text(
                    modifier = Modifier.offset(x = 16.dp),
                    text = title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                logoTitle.invoke()
            }
        },
        navigationIcon = { navigationIcon?.invoke() },
        actions = { actions?.invoke() },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseLargeToolbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: String,
    logoTitle: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    LargeTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            if (logoTitle == null) {
                Text(
                    modifier = Modifier.offset(x = 16.dp),
                    text = title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                logoTitle.invoke()
            }
        },
        navigationIcon = { navigationIcon?.invoke() },
        actions = { actions?.invoke() },
        scrollBehavior = scrollBehavior
    )
}