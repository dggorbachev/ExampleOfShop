package com.dev.vapeshop.features.tobacco_screen.ui.view

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.vapeshop.R
import com.dev.vapeshop.databinding.ItemTobaccoBinding
import com.dev.vapeshop.domain.model.Tobacco


class TobaccoViewHolder(
    private val binding: ItemTobaccoBinding,
    private val onInfoClicked: (tobacco: Tobacco) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tobacco: Tobacco) {
        bindItem(tobacco)
        bindDialog(tobacco)
    }

    private fun bindItem(tobacco: Tobacco) {
        with(binding) {
            Glide.with(itemView)
                .load(tobacco.logo)
                .placeholder(R.drawable.image_preload)
                .into(binding.ivTobacco)

            val paint = tvAvailability.paint
            val width = paint.measureText(root.context.getString(R.string.availability_message,
                tobacco.count))
            val textShader: Shader = LinearGradient(
                0F,
                0F,
                width,
                tvAvailability.textSize,
                intArrayOf(Color.parseColor("#FF5F6D"), Color.parseColor("#FFC371")),
                null,
                TileMode.CLAMP)
            tvAvailability.paint.shader = textShader

            tvTobacco.text =
                root.context.getString(R.string.title_message, tobacco.name, tobacco.nicotine)
            tvPrice.text = root.context.getString(R.string.price_message, tobacco.price)
            tvAvailability.text =
                root.context.getString(R.string.availability_message, tobacco.count)
        }
    }

    private fun bindDialog(tobacco: Tobacco) {
        with(binding) {
            bInfo.setOnClickListener {
                onInfoClicked(tobacco)
            }
        }
    }
}