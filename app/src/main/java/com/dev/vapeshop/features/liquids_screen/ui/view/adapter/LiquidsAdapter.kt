package com.dev.vapeshop.features.liquids_screen.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dev.vapeshop.databinding.ItemLiquidBinding
import com.dev.vapeshop.domain.model.Liquid
import com.dev.vapeshop.features.liquids_screen.ui.view.LiquidsViewHolder

class LiquidsAdapter(
    private val onInfoClicked: (liquid: Liquid) -> Unit,
) :
    ListAdapter<Liquid, LiquidsViewHolder>(DiffCallback) {

    private var liquidsViewHolder: LiquidsViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiquidsViewHolder {
        val binding = ItemLiquidBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.root.minimumWidth = parent.measuredWidth / 2
        liquidsViewHolder = LiquidsViewHolder(binding, onInfoClicked)
        return liquidsViewHolder as LiquidsViewHolder
    }

    override fun onBindViewHolder(holder: LiquidsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Liquid>() {
        override fun areItemsTheSame(oldItem: Liquid, newItem: Liquid): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Liquid, newItem: Liquid): Boolean {
            return oldItem == newItem
        }
    }
}