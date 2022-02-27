package com.jonathanlee.popcorn.ui.common.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.FragmentOptionBinding
import com.jonathanlee.popcorn.ui.base.BaseBottomSheetDialogFragment
import com.jonathanlee.popcorn.util.OptionItemClickListener

class OptionMenuDialogFragment : BaseBottomSheetDialogFragment(), OptionItemClickListener {

    private lateinit var data: ArrayList<Options>
    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!
    private var stylingCallback: ((FragmentOptionBinding) -> Unit)? = null

    companion object {
        private const val TAG = "OptionMenuBottomDialog"
        private const val EXTRA_DATA = "data"

        fun show(
            manager: FragmentManager,
            data: ArrayList<Options>,
            stylingCallback: (FragmentOptionBinding) -> Unit
        ) {
            val args = Bundle().apply {
                putSerializable(EXTRA_DATA, data)
            }
            OptionMenuDialogFragment().also {
                it.arguments = args
                it.stylingCallback = stylingCallback
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
        _binding = FragmentOptionBinding.inflate(
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
        }
        initExtra()
        initView()
    }

    private fun initView() {
        stylingCallback?.invoke(binding)
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = OptionAdapter(data = data).apply {
                setOptionItemClickListener(this@OptionMenuDialogFragment)
            }
            overScrollMode = View.OVER_SCROLL_NEVER
        }

    }

    private fun initExtra() {
        data = arguments?.getSerializable(EXTRA_DATA) as ArrayList<Options>
    }

    interface OptionMenuListener {

    }

    override fun onOptionItemClicked(position: Int) {
        // TODO("Not yet implemented")
        Toast.makeText(requireContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show()
    }

}