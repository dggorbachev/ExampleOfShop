package com.dev.vapeshop.features.liquids_screen.ui.view

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.vapeshop.R
import com.dev.vapeshop.databinding.ItemLiquidBinding
import com.dev.vapeshop.domain.model.Liquid


class LiquidsViewHolder(
    private val binding: ItemLiquidBinding,
    private val onInfoClicked: (liquid: Liquid) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(liquid: Liquid) {
        bindItem(liquid)
        bindDialog(liquid)
    }

    private fun bindItem(liquid: Liquid) {
        with(binding) {
            Glide.with(itemView)
                .load(liquid.logo)
                .placeholder(R.drawable.image_preload)
                .into(binding.ivLiquid)

            val paint = tvAvailability.paint
            val width = paint.measureText(root.context.getString(R.string.availability_message,
                liquid.count))
            val textShader: Shader = LinearGradient(
                0F,
                0F,
                width,
                tvAvailability.textSize,
                intArrayOf(Color.parseColor("#FF5F6D"), Color.parseColor("#FFC371")),
                null,
                TileMode.CLAMP)
            tvAvailability.paint.shader = textShader

            tvLiquid.text =
                root.context.getString(R.string.title_message, liquid.name, liquid.nicotine)
            tvPrice.text = root.context.getString(R.string.price_message, liquid.price)
            tvAvailability.text =
                root.context.getString(R.string.availability_message, liquid.count)
        }
    }

    private fun bindDialog(liquid: Liquid) {
        with(binding) {
            bInfo.setOnClickListener {
                onInfoClicked(liquid)
            }
        }
    }
}