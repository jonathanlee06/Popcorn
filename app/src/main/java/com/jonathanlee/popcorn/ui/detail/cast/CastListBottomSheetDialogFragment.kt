package com.jonathanlee.popcorn.ui.detail.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.databinding.FragmentCastBinding
import com.jonathanlee.popcorn.ui.base.BaseBottomSheetDialogFragment
import com.jonathanlee.popcorn.ui.common.GridInternalSpaceItemDecoration
import com.jonathanlee.popcorn.util.OptionItemClickListener
import com.jonathanlee.popcorn.util.extension.dp

class CastListBottomSheetDialogFragment : BaseBottomSheetDialogFragment(), OptionItemClickListener {

    private lateinit var data: ArrayList<CastItem.Item>
    private var _binding: FragmentCastBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "CastListBottomDialog"
        private const val EXTRA_DATA = "data"

        fun show(
            manager: FragmentManager,
            data: ArrayList<CastItem.Item>
        ) {
            val args = Bundle().apply {
                putSerializable(EXTRA_DATA, data)
            }
            CastListBottomSheetDialogFragment().also {
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
        _binding = FragmentCastBinding.inflate(
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
        }
        initExtra()
        initView()
    }

    override fun onOptionItemClicked(position: Int) {
        // TODO("Not yet implemented")
    }

    private fun initExtra() {
        data = arguments?.getSerializable(EXTRA_DATA) as ArrayList<CastItem.Item>
    }

    private fun initView() {
        binding.rvData.apply {
            layoutManager = GridLayoutManager(requireContext(), 2).apply {

            }
            adapter = CastListAdapter().also {
                it.updateListData(data)
                it.setListener(object : OptionItemClickListener {
                    override fun onOptionItemClicked(position: Int) {
                        // TODO("Not yet implemented")
                    }

                })
            }
            addItemDecoration(
                GridInternalSpaceItemDecoration(20.dp, 2)
            )
        }
        binding.btnClose.setOnClickListener { dismiss() }
    }

}