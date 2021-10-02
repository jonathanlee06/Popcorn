package com.jonathanlee.popcorn.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.databinding.ActivityDetailBinding
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.util.binding.viewBinding

class DetailActivity : BaseActivity(), DetailContract.View {

    private val binding: ActivityDetailBinding by viewBinding()
    private var details: Details? = null
    override lateinit var presenter: DetailContract.Presenter

    companion object {
        private const val EXTRA_DETAILS = "details"

        fun getStartIntent(context: Context, details: Details): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(EXTRA_DETAILS, details)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initData()
        initView()
    }

    override fun setBackdropImage(path: String?) {
        if (path != null) {
            Glide.with(this)
                .load(path)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivBackdrop)
        } else {
            Glide.with(this).clear(binding.ivBackdrop)
        }
    }

    private fun initData() {
        details = intent.getParcelableExtra(EXTRA_DETAILS)
        presenter.getBackdropImage(details?.backdropPath)
    }

    private fun initPresenter() {
        presenter = DetailPresenter(this)
    }

    private fun initView() {
        binding.apply {
            root.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            tvDetailDate.text = getString(R.string.page_detail_release_date, details?.releaseDate)
            tvDetailSummary.text = details?.summary
            tvDetailTitle.text = details?.title
            mtbTitle.title = details?.title
            mtbTitle.setNavigationOnClickListener { finish() }
        }
        val toolbar = binding.mtbTitle
        if (toolbar.layoutParams is CollapsingToolbarLayout.LayoutParams) {
            val params = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            toolbar.layoutParams = params.apply {
                topMargin = getStatusBarSize()
            }
        }
    }
}