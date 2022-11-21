package com.dev.vapeshop.features.devices_screen.ui.view

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
import com.dev.vapeshop.databinding.FragmentDevicesBinding
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Device
import com.dev.vapeshop.features.bottom_dialog.ui.view.BottomDialogFragment
import com.dev.vapeshop.features.devices_screen.stateholders.DevicesViewModel
import com.dev.vapeshop.features.devices_screen.ui.view.adapter.DevicesAdapter
import javax.inject.Inject

class DevicesFragment :
    BaseFragment<FragmentDevicesBinding>(FragmentDevicesBinding::inflate) {

    private val adapter: DevicesAdapter by lazy {
        DevicesAdapter(onInfoClicked = {
            openInfo(it)
        })
    }

    @Inject
    lateinit var factory: DevicesViewModel.Factory

    private val viewModel: DevicesViewModel by viewModels {
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
        viewModel.getDevices()

        viewModel.currentDevices.observe(viewLifecycleOwner) { state ->
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

    private fun openInfo(device: Device) {
        val bottomDialogFragment = BottomDialogFragment()
        val bundle = Bundle()

        bundle.putString("title", device.name)
        bundle.putString("description", "Габариты: " + device.dimensions +
                "\nВыходная мощность: " + device.power +
                "\nДисплей: " + device.display +
                "\nАккумулятор: " + device.battery +
                "\nЗарядка: " + device.charger +
                "\nВместительность: " + device.capacity + "мл")

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

    private fun bindLoaded(data: List<Device>) {
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