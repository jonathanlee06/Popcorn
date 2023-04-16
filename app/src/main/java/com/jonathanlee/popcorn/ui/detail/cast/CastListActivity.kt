package com.jonathanlee.popcorn.ui.detail.cast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.databinding.ActivityCastListBinding
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.ui.common.GridInternalSpaceItemDecoration
import com.jonathanlee.popcorn.util.OptionItemClickListener
import com.jonathanlee.popcorn.util.TAG
import com.jonathanlee.popcorn.util.extension.dp

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
        Log.d(TAG, "initView: id=${castList.getOrNull(0)?.cast?.id}")
        Log.d(TAG, "initView: id=${castList.getOrNull(0)?.cast?.castId}")
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
                                    finish()
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