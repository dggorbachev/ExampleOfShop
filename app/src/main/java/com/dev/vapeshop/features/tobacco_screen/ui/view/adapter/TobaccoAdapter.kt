package com.dev.vapeshop.features.tobacco_screen.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dev.vapeshop.databinding.ItemLiquidBinding
import com.dev.vapeshop.databinding.ItemTobaccoBinding
import com.dev.vapeshop.domain.model.Liquid
import com.dev.vapeshop.domain.model.Tobacco
import com.dev.vapeshop.features.liquids_screen.ui.view.LiquidsViewHolder
import com.dev.vapeshop.features.tobacco_screen.ui.view.TobaccoViewHolder

class TobaccoAdapter(
    private val onInfoClicked: (tobacco: Tobacco) -> Unit,
) :
    ListAdapter<Tobacco, TobaccoViewHolder>(DiffCallback) {

    private var tobaccoViewHolder: TobaccoViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TobaccoViewHolder {
        val binding = ItemTobaccoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.root.minimumWidth = parent.measuredWidth / 2
        tobaccoViewHolder = TobaccoViewHolder(binding, onInfoClicked)
        return tobaccoViewHolder as TobaccoViewHolder
    }

    override fun onBindViewHolder(holder: TobaccoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Tobacco>() {
        override fun areItemsTheSame(oldItem: Tobacco, newItem: Tobacco): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Tobacco, newItem: Tobacco): Boolean {
            return oldItem == newItem
        }
    }
}