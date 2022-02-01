package com.jonathanlee.popcorn.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Video
import com.jonathanlee.popcorn.databinding.ActivityDetailBinding
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.binding.viewBinding
import com.jonathanlee.popcorn.util.extension.navigateTo

class DetailActivity : BaseActivity(), DetailContract.View {

    private val binding: ActivityDetailBinding by viewBinding()
    private lateinit var details: Details
    private var entry: Int = 0
    private val castList = ArrayList<Cast>()
    private val videoList = ArrayList<Video>()
    override lateinit var presenter: DetailContract.Presenter
    private lateinit var castAdapter: DetailCastAdapter
    private lateinit var videoAdapter: DetailVideoAdapter

    companion object {
        const val ENTRY_FROM_MOVIE = 0
        const val ENTRY_FROM_TV = 1
        private const val EXTRA_DETAILS = "details"
        private const val EXTRA_ENTRY_POINT = "entryPoint"

        fun getStartIntent(context: Context, details: Details, entry: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(EXTRA_DETAILS, details)
            intent.putExtra(EXTRA_ENTRY_POINT, entry)
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
        val placeholder = ColorDrawable(ContextCompat.getColor(this, R.color.black))
        if (!path.isNullOrEmpty()) {
            binding.ivBackdrop.load(path) {
                crossfade(true)
                placeholder(placeholder)
                transformations(
                    BlurTransformation(
                        context = this@DetailActivity,
                        radius = 10F,
                        sampling = 1F
                    )
                )
            }
        }
    }

    override fun setBackdropPoster(path: String?) {
        val placeholder = ColorDrawable(ContextCompat.getColor(this, R.color.black))
        if (!path.isNullOrEmpty()) {
            binding.ivPoster.load(path) {
                crossfade(true)
                placeholder(placeholder)
            }
        }
    }

    override fun setCasts(cast: ArrayList<Cast>) {
        castList.apply {
            clear()
            addAll(cast)
        }
        castAdapter.updateListData(castList)
    }

    override fun setGenres(genres: ArrayList<String>) {
        genres.forEach { genre ->
            val chip = Chip(this)
            chip.text = genre
            chip.setTextColor(ContextCompat.getColor(this, R.color.white))
            chip.setChipBackgroundColorResource(R.color.colorPrimary)
            binding.chipGenre.addView(chip)
        }
    }

    override fun setNoVideos() {
        binding.apply {
            tvTrailers.visibility = View.GONE
            rvVideos.visibility = View.GONE
        }
    }

    override fun setVideos(video: ArrayList<Video>) {
        videoList.apply {
            clear()
            addAll(video)
        }
        videoAdapter.updateListData(videoList)
        videoAdapter.setOnItemClickListener(object : AdapterItemClickListener<Video> {
            override fun onItemClicked(position: Int, model: Video) {
                val goToYouTube =
                    Intent(Intent.ACTION_VIEW, Uri.parse(model.videoPath))
                navigateTo(goToYouTube)
            }
        })
        binding.apply {
            tvTrailers.visibility = View.VISIBLE
            rvVideos.visibility = View.VISIBLE
        }
    }

    private fun initData() {
        val localDetails = intent.getParcelableExtra(EXTRA_DETAILS) as? Details
        if (localDetails == null) {
            finish()
            return
        }
        details = localDetails
        entry = intent.getIntExtra(EXTRA_ENTRY_POINT, entry)
    }

    private fun initPresenter() {
        presenter = DetailPresenter(view = this, scope = lifecycleScope)
    }

    private fun initView() {
        castAdapter = DetailCastAdapter()
        videoAdapter = DetailVideoAdapter()
        binding.apply {
            window.statusBarColor = Color.TRANSPARENT
            tvDetailDate.text = getString(R.string.page_detail_release_date, details.releaseDate)
            tvDetailSummary.text = details.summary
            tvDetailTitle.text = details.title
            mtbTitle.title = details.title
            mtbTitle.setNavigationOnClickListener { finish() }
            rvCasts.apply {
                layoutManager =
                    LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
            }
            rvVideos.apply {
                layoutManager =
                    LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = videoAdapter
            }
        }
        //binding.rvCasts.addOnItemTouchListener(verticalScrollListener)
        val toolbar = binding.mtbTitle
        if (toolbar.layoutParams is CollapsingToolbarLayout.LayoutParams) {
            val params = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            toolbar.layoutParams = params.apply {
                topMargin = getStatusBarSize()
            }
        }
        presenter.getDetails(details, entry)
    }
}