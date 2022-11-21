package com.dev.vapeshop.features.cartridges_screen.ui.view

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.vapeshop.R
import com.dev.vapeshop.databinding.ItemCartridgeBinding
import com.dev.vapeshop.domain.model.CartridgeVaporizer


class CartridgesViewHolder(
    private val binding: ItemCartridgeBinding,
    private val onInfoClicked: (cartridge: CartridgeVaporizer) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cartridge: CartridgeVaporizer) {
        bindItem(cartridge)
        bindDialog(cartridge)
    }

    private fun bindItem(cartridge: CartridgeVaporizer) {
        with(binding) {
            Glide.with(itemView)
                .load(cartridge.logo)
                .placeholder(R.drawable.image_preload)
                .into(binding.ivCartridge)

            val paint = tvAvailability.paint
            val width = paint.measureText(root.context.getString(R.string.availability_message,
                cartridge.count))
            val textShader: Shader = LinearGradient(
                0F,
                0F,
                width,
                tvAvailability.textSize,
                intArrayOf(Color.parseColor("#FF5F6D"), Color.parseColor("#FFC371")),
                null,
                TileMode.CLAMP)
            tvAvailability.paint.shader = textShader

            tvCartridge.text = cartridge.name
            tvPrice.text = root.context.getString(R.string.price_message, cartridge.price)
            tvAvailability.text =
                root.context.getString(R.string.availability_message, cartridge.count)
        }
    }

    private fun bindDialog(cartridge: CartridgeVaporizer) {
        with(binding) {
            bInfo.setOnClickListener {
                onInfoClicked(cartridge)
            }
        }
    }
}