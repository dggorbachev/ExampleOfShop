package com.dev.vapeshop.features.cartridges_screen.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.vapeshop.R
import com.dev.vapeshop.base.BaseFragment
import com.dev.vapeshop.base.LambdaFactory
import com.dev.vapeshop.databinding.FragmentCartridgesBinding
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.CartridgeVaporizer
import com.dev.vapeshop.features.bottom_dialog.ui.view.BottomDialogFragment
import com.dev.vapeshop.features.cartridges_screen.stateholders.CartridgesViewModel
import com.dev.vapeshop.features.cartridges_screen.ui.view.adapter.CartridgesAdapter
import javax.inject.Inject

class CartridgesFragment :
    BaseFragment<FragmentCartridgesBinding>(FragmentCartridgesBinding::inflate) {

    private val adapter: CartridgesAdapter by lazy {
        CartridgesAdapter(onInfoClicked = {
            openInfo(it)
        })
    }

    @Inject
    lateinit var factory: CartridgesViewModel.Factory

    private val viewModel: CartridgesViewModel by viewModels {
        LambdaFactory(this) { stateHandle ->
            factory.create(
                stateHandle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.categoriesRv
        recyclerview.adapter = adapter
        recyclerview.layoutManager = GridLayoutManager(context, 2)
        getData()

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private val ivNotification by lazy {
        requireActivity().findViewById<CoordinatorLayout>(R.id.app_bar_main)
            .findViewById<ConstraintLayout>(R.id.content_main)
            .findViewById<ImageView>(R.id.ivNotification)
    }
    private val tvNotification by lazy {
        requireActivity().findViewById<CoordinatorLayout>(R.id.app_bar_main)
            .findViewById<ConstraintLayout>(R.id.content_main)
            .findViewById<TextView>(R.id.tvNotification)
    }

    private fun getData() {
        viewModel.getCartridges()

        viewModel.currentCartridges.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AsyncState.Error -> {
                    bindError(state.message)
                }
                is AsyncState.Loading -> {
                    bindLoading()
                }
                is AsyncState.Loaded -> {
                    bindLoaded(state.data)
                }
            }
        }
    }

    private fun openInfo(cartridge: CartridgeVaporizer) {
        val bottomDialogFragment = BottomDialogFragment()
        val bundle = Bundle()

        bundle.putString("title", cartridge.name)
        bundle.putString("description", "Цена: " + cartridge.price + " ₽")

        bottomDialogFragment.arguments = bundle
        bottomDialogFragment.show(requireActivity().supportFragmentManager, "bottomDialogFragment")
    }

    private fun bindError(message: String) {
        with(binding) {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            } else {
                progressBar.visibility = View.GONE
            }
        }
        ivNotification.visibility = View.VISIBLE
        tvNotification.visibility = View.VISIBLE
        tvNotification.text = message
    }

    private fun bindLoading() {
        with(binding) {
            if (!swipeRefresh.isRefreshing) {
                progressBar.visibility = View.VISIBLE
            }
        }
        ivNotification.visibility = View.GONE
        tvNotification.visibility = View.GONE
    }

    private fun bindLoaded(data: List<CartridgeVaporizer>) {
        with(binding) {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            } else {
                progressBar.visibility = View.GONE
            }
        }
        ivNotification.visibility = View.GONE
        tvNotification.visibility = View.GONE
        adapter.submitList(data)
    }
}