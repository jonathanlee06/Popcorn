package com.jonathanlee.popcorn.ui.detail.cast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.ApiResultState
import com.jonathanlee.popcorn.databinding.FragmentCastDetailBinding
import com.jonathanlee.popcorn.ui.base.BaseBottomSheetDialogFragment
import com.jonathanlee.popcorn.ui.common.HorizontalSpaceItemDecoration
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.extension.dp
import com.jonathanlee.popcorn.util.extension.navigateTo

class CastDetailBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {
    private lateinit var cast: Cast
    private var _binding: FragmentCastDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CastDetailViewModel by viewModels()
    private var listener: Listener? = null
    private lateinit var creditAdapter: CastContentAdapter
    private lateinit var imagesAdapter: CastImageAdapter
    private lateinit var viewpager: ViewPager2

    companion object {
        private const val TAG = "CastListBottomDialog"
        private const val EXTRA_DATA = "data"

        fun show(
            manager: FragmentManager,
            data: Cast?,
            listener: Listener
        ) {
            val args = Bundle().apply {
                putParcelable(EXTRA_DATA, data)
            }
            CastDetailBottomSheetDialogFragment().also {
                it.listener = listener
                it.arguments = args
                if (!it.isActive) {
                    it.show(manager, TAG)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setOnShowListener {
            if (dialog == null) {
                return@setOnShowListener
            }
            val bottomSheet = dialog!!.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
            behavior.isDraggable = false
        }
        initExtra()
        initView()
    }

    private fun initExtra() {
        cast = arguments?.getParcelable<Cast>(EXTRA_DATA) as Cast
        viewModel.getCastInfo(cast.id)
    }

    private fun initView() {
        toggleIndicator(false)
        val path = Api.getPosterPath(cast.profilePath)
        creditAdapter = CastContentAdapter()
        binding.ivBackdrop.apply {
            visibility = View.VISIBLE
            load(path) {
                crossfade(true)
            }
        }
        binding.mtbTitle.title = cast.name
        binding.mtbTitle.setNavigationOnClickListener { dismiss() }
        binding.tvDetailTitleTop.text = cast.name
        binding.rvCredits.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = creditAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            addItemDecoration(
                HorizontalSpaceItemDecoration(5.dp)
            )
        }
        binding.vpImages.visibility = View.GONE
        imagesAdapter = CastImageAdapter()
        viewpager = binding.vpImages
        viewpager.adapter = imagesAdapter
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toggleIndicator(show = true, withMultiple = true)
            }
        })
        val tabLayout = binding.tabImages
        tabLayout.attachToRecyclerView(viewpager.getChildAt(0) as RecyclerView)
        viewModel.castDetail.observe(this) {
            when (it) {
                is ApiResultState.Loading -> {
                    // do loading
                }
                is ApiResultState.Success -> {
                    val castDetail = it.data
                    val profilePath = castDetail?.homepage
                    binding.tvBiographyContent.text = castDetail?.biography
                    binding.ivHomeLink.apply {
                        visibility = if (profilePath.isNullOrEmpty()) View.GONE else View.VISIBLE
                        setOnClickListener {
                            profilePath?.let {
                                val goToCastProfile =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(profilePath))
                                navigateTo(goToCastProfile)
                            }
                        }
                    }
                    binding.ivImdb.apply {
                        val imdbPath = castDetail?.imdbId
                        visibility = if (imdbPath.isNullOrEmpty()) View.GONE else View.VISIBLE
                        setOnClickListener {
                            val goToImdb =
                                Intent(Intent.ACTION_VIEW, Uri.parse(Api.getImdbPath(imdbPath)))
                            navigateTo(goToImdb)
                        }
                    }
                    binding.tvBirthdayContent.text = castDetail?.birthday
                    binding.tvAlsoContent.text =
                        castDetail?.alsoKnownAs?.let { it1 -> TextUtils.join(", ", it1) }
                }
                is ApiResultState.Failure -> {
                    Log.d(TAG, "response error=${it.exception}")
                }
            }
        }
        viewModel.castCredit.observe(this) {
            creditAdapter.updateListData(it)
            creditAdapter.setOnItemClickListener(object : AdapterItemClickListener<CastCredit> {
                override fun onItemClicked(position: Int, model: CastCredit) {
                    dismiss()
                    listener?.onCreditClick(model)
                }

            })
        }
        viewModel.castPhoto.observe(this) {
            val listSize = it.size
            viewpager.offscreenPageLimit = if (listSize > 0) listSize else 1
            imagesAdapter.updateListData(it)
            toggleIndicator(true, listSize > 1)
            binding.vpImages.visibility = View.VISIBLE
            binding.ivBackdrop.visibility = View.GONE
        }
    }

    private fun toggleIndicator(show: Boolean, withMultiple: Boolean = false) {
        val tabIndicator = binding.tabImages
        val parent = tabIndicator.parent as ViewGroup
        val transition = Slide(Gravity.BOTTOM).apply {
            duration = 500
            addTarget(tabIndicator)
            addTarget(binding.tvDetailTitleTop)
        }
        TransitionManager.beginDelayedTransition(parent, transition)
        binding.tvDetailTitleTop.visibility = if (show) View.VISIBLE else View.GONE
        tabIndicator.visibility = if (show && withMultiple) View.VISIBLE else View.GONE
    }

    interface Listener {
        fun onCreditClick(credit: CastCredit)
    }

}