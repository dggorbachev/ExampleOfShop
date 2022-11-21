package com.dev.vapeshop.features.cartridges_screen.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dev.vapeshop.databinding.ItemCartridgeBinding
import com.dev.vapeshop.domain.model.CartridgeVaporizer
import com.dev.vapeshop.features.cartridges_screen.ui.view.CartridgesViewHolder

class CartridgesAdapter(
    private val onInfoClicked: (cartridge: CartridgeVaporizer) -> Unit,
) :
    ListAdapter<CartridgeVaporizer, CartridgesViewHolder>(DiffCallback) {

    private var liquidsViewHolder: CartridgesViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartridgesViewHolder {
        val binding = ItemCartridgeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.root.minimumWidth = parent.measuredWidth / 2
        liquidsViewHolder = CartridgesViewHolder(binding, onInfoClicked)
        return liquidsViewHolder as CartridgesViewHolder
    }

    override fun onBindViewHolder(holder: CartridgesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<CartridgeVaporizer>() {
        override fun areItemsTheSame(
            oldItem: CartridgeVaporizer,
            newItem: CartridgeVaporizer,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CartridgeVaporizer,
            newItem: CartridgeVaporizer,
        ): Boolean {
            return oldItem == newItem
        }
    }
}