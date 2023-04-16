package com.jonathanlee.popcorn.ui.detail.cast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.databinding.ActivityCastListBinding
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.ui.common.GridInternalSpaceItemDecoration
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.OptionItemClickListener
import com.jonathanlee.popcorn.util.extension.dp
import com.jonathanlee.popcorn.util.extension.navigateTo

class CastListActivity : BaseActivity() {

    private val binding: ActivityCastListBinding by viewBinding()
    private var castList = ArrayList<CastItem.Item>()

    companion object {
        private const val EXTRA_CAST_LIST = "castList"

        fun getStartIntent(context: Context, castList: ArrayList<CastItem.Item>): Intent {
            val intent = Intent(context, CastListActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putParcelableArrayListExtra(EXTRA_CAST_LIST, castList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.mtbCastTitle)
        initExtra()
        initView()
    }

    private fun initExtra() {
        val localCastList = intent.getParcelableArrayListExtra<CastItem.Item>(EXTRA_CAST_LIST)
        if (localCastList.isNullOrEmpty()) {
            finish()
            return
        }
        castList = localCastList
    }

    private fun initView() {
        binding.rvData.apply {
            layoutManager = GridLayoutManager(this@CastListActivity, 2)
            adapter = CastListAdapter().also {
                it.updateListData(castList)
                it.setListener(object : OptionItemClickListener {
                    override fun onOptionItemClicked(position: Int) {
                        CastDetailBottomSheetDialogFragment.show(
                            manager = supportFragmentManager,
                            data = castList.getOrNull(position)?.cast,
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

                })
            }
            addItemDecoration(
                GridInternalSpaceItemDecoration(20.dp, 2)
            )
        }
        binding.mtbCastTitle.apply {
            setNavigationOnClickListener { finish() }
        }
    }

}