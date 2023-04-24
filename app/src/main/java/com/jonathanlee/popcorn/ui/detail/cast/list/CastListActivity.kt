package com.jonathanlee.popcorn.ui.detail.cast.list

import android.os.Bundle
import androidx.activity.compose.setContent
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.ui.detail.cast.CastDetailBottomSheetDialogFragment
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.EXTRA_CAST_LIST
import com.jonathanlee.popcorn.util.extension.getParcelableArrayList
import com.jonathanlee.popcorn.util.extension.navigateTo

class CastListActivity : BaseActivity() {

    private var castList = ArrayList<CastItem.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initExtra()
        setContent {
            CastListPage(
                castList = castList,
                onBackClick = { finish() },
                onItemClick = {
                    CastDetailBottomSheetDialogFragment.show(
                        manager = supportFragmentManager,
                        data = it,
                        listener = object : CastDetailBottomSheetDialogFragment.Listener {
                            override fun onCreditClick(credit: CastCredit) {
                                navigateTo(
                                    DetailActivity.getStartIntent(
                                        this@CastListActivity,
                                        DetailUtil.parseToContent(credit),
                                        if (credit.mediaType == "movie") DetailActivity.ENTRY_FROM_MOVIE else DetailActivity.ENTRY_FROM_TV
                                    )
                                )
                            }

                        }
                    )
                }
            )
        }
    }

    private fun initExtra() {
        val localCastList =
            intent.getParcelableArrayList(EXTRA_CAST_LIST, CastItem.Item::class.java)
        if (localCastList.isNullOrEmpty()) {
            finish()
            return
        }
        castList = localCastList
    }

}