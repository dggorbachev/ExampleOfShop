package com.dev.vapeshop.features.bottom_dialog.ui.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.vapeshop.R
import com.dev.vapeshop.databinding.FragmentDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomDialogFragment : BottomSheetDialogFragment(R.layout.fragment_dialog) {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!
    private var title: String = ""
    private var description: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        title = arguments?.getString("title")!!
        description = arguments?.getString("description")!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            bInst.setOnClickListener {
                openInst()
            }
            tvTitle.text = title

            tvDescription.text = description
        }
    }

    private fun openInst() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://instagram.com/_u/madeinrusssiaa")
            intent.setPackage("com.instagram.android")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/madeinrusssiaa")))
        }
    }
}