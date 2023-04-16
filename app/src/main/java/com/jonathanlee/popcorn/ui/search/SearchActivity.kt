package com.jonathanlee.popcorn.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.network.SearchModel
import com.jonathanlee.popcorn.data.model.parseToCast
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.ui.detail.cast.CastDetailBottomSheetDialogFragment
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.extension.navigateTo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            return intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchPage(
                viewModel = viewModel,
                onClick = {
                    onSearchItemClick(it)
                }
            )
        }
        lifecycleScope.launch {
            viewModel.searchQuery.debounce(500).collect {
                viewModel.search(it, 1)
            }
        }
    }

    private fun onSearchItemClick(model: SearchModel) {
        when (model.mediaType) {
            "movie" -> {
                navigateTo(
                    DetailActivity.getStartIntent(
                        this,
                        DetailUtil.parseToContent(model),
                        DetailActivity.ENTRY_FROM_MOVIE
                    )
                )
            }

            "tv" -> {
                navigateTo(
                    DetailActivity.getStartIntent(
                        this,
                        DetailUtil.parseToContent(model),
                        DetailActivity.ENTRY_FROM_TV
                    )
                )
            }

            "person" -> {
                CastDetailBottomSheetDialogFragment.show(
                    manager = supportFragmentManager,
                    data = parseToCast(model),
                    listener = object : CastDetailBottomSheetDialogFragment.Listener {
                        override fun onCreditClick(credit: CastCredit) {
                            navigateTo(
                                DetailActivity.getStartIntent(
                                    this@SearchActivity,
                                    DetailUtil.parseToContent(credit),
                                    if (credit.mediaType == "movie") DetailActivity.ENTRY_FROM_MOVIE else DetailActivity.ENTRY_FROM_TV
                                )
                            )
                        }

                    }
                )
            }
        }
    }
}