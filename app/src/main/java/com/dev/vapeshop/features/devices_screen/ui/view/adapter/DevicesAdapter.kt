package com.dev.vapeshop.features.devices_screen.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dev.vapeshop.databinding.ItemDeviceBinding
import com.dev.vapeshop.domain.model.Device
import com.dev.vapeshop.features.devices_screen.ui.view.DeviceViewHolder

class DevicesAdapter(
    private val onInfoClicked: (device: Device) -> Unit,
) :
    ListAdapter<Device, DeviceViewHolder>(DiffCallback) {

    private var deviceViewHolder: DeviceViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.root.minimumWidth = parent.measuredWidth / 2
        deviceViewHolder = DeviceViewHolder(binding, onInfoClicked)
        return deviceViewHolder as DeviceViewHolder
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }
    }
}