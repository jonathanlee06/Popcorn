package com.jonathanlee.popcorn.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.model.Content
import com.jonathanlee.popcorn.data.model.Video
import com.jonathanlee.popcorn.databinding.ActivityDetailBinding
import com.jonathanlee.popcorn.ui.base.BaseActivity
import com.jonathanlee.popcorn.ui.common.HorizontalSpaceItemDecoration
import com.jonathanlee.popcorn.ui.detail.cast.CastDetailBottomSheetDialogFragment
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.Navigator
import com.jonathanlee.popcorn.util.extension.dp
import com.jonathanlee.popcorn.util.extension.getParcelable
import com.jonathanlee.popcorn.util.extension.navigateTo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : BaseActivity(), DetailContract.View {

    private val binding: ActivityDetailBinding by viewBinding()
    private lateinit var contents: Content
    private var entry: Int = 0
    private val videoList = ArrayList<Video>()
    private val castList = ArrayList<CastItem.Item>()
    override lateinit var presenter: DetailContract.Presenter
    private lateinit var castAdapter: DetailCastAdapter
    private lateinit var videoAdapter: DetailVideoAdapter

    @Inject
    lateinit var navigator: Navigator

    companion object {
        const val ENTRY_FROM_MOVIE = 0
        const val ENTRY_FROM_TV = 1
        private const val EXTRA_DETAILS = "details"
        private const val EXTRA_ENTRY_POINT = "entryPoint"

        fun getStartIntent(context: Context, content: Content, entry: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.putExtra(EXTRA_DETAILS, content)
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
        val placeholder = ColorDrawable(ContextCompat.getColor(this, R.color.background900))
        if (!path.isNullOrEmpty()) {
            binding.ivBackdrop.load(path) {
                crossfade(true)
                placeholder(placeholder)
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

    override fun setCasts(cast: List<CastItem.Item>?) {
        castList.apply {
            clear()
            if (cast != null) {
                addAll(cast)
            }
        }
        castAdapter.updateListData(castList)
        castAdapter.setListener(object : DetailCastAdapter.CastOnClickListener {
            override fun onCastClick(position: Int) {
                CastDetailBottomSheetDialogFragment.show(
                    manager = supportFragmentManager,
                    data = castList.getOrNull(position)?.cast,
                    listener = object : CastDetailBottomSheetDialogFragment.Listener {
                        override fun onCreditClick(credit: CastCredit) {
                            navigateTo(
                                getStartIntent(
                                    this@DetailActivity,
                                    DetailUtil.parseToContent(credit),
                                    if (credit.mediaType == "movie") ENTRY_FROM_MOVIE else ENTRY_FROM_TV
                                )
                            )
                        }

                    }
                )
            }

            override fun onMoreClick() {
                clickSeeMore()
            }

        })
        binding.rlSeeMore.visibility = if (castList.size > 5) View.VISIBLE else View.GONE
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

    override fun setContentProducer(name: String?) {
        if (!name.isNullOrEmpty()) {
            binding.viewProducer.apply {
                root.visibility = View.VISIBLE
                tvProducerName.text = name
            }
        } else {
            binding.viewProducer.root.visibility = View.GONE
        }
    }

    override fun setContentClassification(classification: String?) {
        if (!classification.isNullOrEmpty()) {
            binding.viewCertification.apply {
                root.visibility = View.VISIBLE
                tvCertificationContent.text = classification
            }
        } else {
            binding.viewCertification.root.visibility = View.GONE
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

    private fun clickSeeMore() {
        navigator.navigateToCastListPage(this, castList)
        //navigateTo(CastListActivity.getStartIntent(this, castList))
    }

    private fun initData() {
        val localDetails = intent.getParcelable(EXTRA_DETAILS, Content::class.java)
        if (localDetails == null) {
            finish()
            return
        }
        contents = localDetails
        entry = intent.getIntExtra(EXTRA_ENTRY_POINT, entry)
    }

    private fun initPresenter() {
        presenter = DetailPresenter(view = this, scope = lifecycleScope)
    }

    @Suppress("DEPRECATION")
    private fun initView() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        castAdapter = DetailCastAdapter()
        videoAdapter = DetailVideoAdapter()
        binding.apply {
            val contentDetails = contents.contentDetails
            window.statusBarColor = Color.TRANSPARENT
            tvDetailDate.text =
                getString(R.string.page_detail_release_date, contentDetails.releaseDate)
            tvDetailSummary.text = contentDetails.summary
            tvDetailTitleTop.text = contentDetails.title
            mtbTitle.title = contentDetails.title
            mtbTitle.setNavigationOnClickListener { finish() }
            val votePercentage = contents.vote.vote?.times(10)?.toInt().toString()
            viewVote.tvRating.text = "$votePercentage%"
            viewVote.tvRatingCount.text = getString(
                R.string.list_rating_count,
                contents.vote.voteCount
            )
            val popularity = contents.vote.vote?.times(10)?.toInt() ?: 0
            viewVote.progressPop.progress = popularity
            viewVote.tvPopPercent.text =
                String.format(getString(R.string.cast_popularity_percentage), popularity)
            setProgressColor(popularity)
            rlSeeMore.setOnClickListener { clickSeeMore() }
            rvCasts.apply {
                layoutManager =
                    LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                addItemDecoration(
                    HorizontalSpaceItemDecoration(5.dp)
                )
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
        presenter.getDetails(contents.contentDetails, entry)
    }

    private fun setProgressColor(popularity: Int) {
        val drawable =
            ContextCompat.getDrawable(this, R.drawable.circular_progress) as LayerDrawable
        val bar = drawable.findDrawableByLayerId(R.id.inner_ring)
        when {
            popularity < 40 -> {
                DrawableCompat.setTint(bar, ContextCompat.getColor(this, R.color.color_red))
                //bar.setTint(ContextCompat.getColor(this, R.color.color_red))
            }

            popularity in 40..69 -> {
                DrawableCompat.setTint(bar, ContextCompat.getColor(this, R.color.color_yellow))
                //bar.setTint(ContextCompat.getColor(this, R.color.color_yellow))
            }

            popularity >= 70 -> {
                DrawableCompat.setTint(bar, ContextCompat.getColor(this, R.color.color_green))
                //bar.setTint(ContextCompat.getColor(this, R.color.color_green))
            }
        }
    }
}