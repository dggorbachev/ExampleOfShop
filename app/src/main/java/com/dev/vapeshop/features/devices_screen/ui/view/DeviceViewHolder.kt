package com.dev.vapeshop.features.devices_screen.ui.view

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.vapeshop.R
import com.dev.vapeshop.databinding.ItemDeviceBinding
import com.dev.vapeshop.domain.model.Device


class DeviceViewHolder(
    private val binding: ItemDeviceBinding,
    private val onInfoClicked: (device: Device) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(device: Device) {
        bindItem(device)
        bindDialog(device)
    }

    private fun bindItem(device: Device) {
        with(binding) {
            Glide.with(itemView)
                .load(device.logo)
                .placeholder(R.drawable.image_preload)
                .into(binding.ivDevice)

            val paint = tvDevice.paint
            val width = paint.measureText(root.context.getString(R.string.availability_message,
                device.count))
            val textShader: Shader = LinearGradient(
                0F,
                0F,
                width,
                tvAvailability.textSize,
                intArrayOf(Color.parseColor("#FF5F6D"), Color.parseColor("#FFC371")),
                null,
                TileMode.CLAMP)
            tvAvailability.paint.shader = textShader

            tvDevice.text = device.name
            tvPrice.text = root.context.getString(R.string.price_message, device.price)
            tvAvailability.text =
                root.context.getString(R.string.availability_message, device.count)
        }
    }

    private fun bindDialog(device: Device) {
        with(binding) {
            bInfo.setOnClickListener {
                onInfoClicked(device)
            }
        }
    }
}