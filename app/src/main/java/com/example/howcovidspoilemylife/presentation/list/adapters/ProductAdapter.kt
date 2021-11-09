package com.example.howcovidspoilemylife.presentation.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.howcovidspoilemylife.databinding.BadItemBinding
import com.example.howcovidspoilemylife.databinding.DateItemBinding
import com.example.howcovidspoilemylife.presentation.list.viewModel.Common
import java.text.DateFormat

class ProductAdapter(
    private val dateFormat: DateFormat,
    private val update: (Common) -> Unit,
    private val delete: (Common) -> Unit,
) :
    ListAdapter<Common, RecyclerView.ViewHolder>(DiffBadProductCallback()) {

    companion object {
        private const val DATE_TYPE = 1
        private const val PRODUCT_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATE_TYPE) {
            val binding =
                DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateViewHolder(binding, dateFormat)
        } else {
            val binding = BadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding).apply {
                itemView.setOnClickListener {
                    update(currentList[adapterPosition])
                }
                itemView.setOnLongClickListener {
                    delete(currentList[adapterPosition])
                    false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].type == 1) {
            DATE_TYPE
        } else {
            PRODUCT_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> holder.bind(currentList[position])
            is ViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is DateViewHolder -> holder.unBind()
            is ViewHolder -> holder.unBind()
        }

        super.onViewRecycled(holder)
    }

    class ViewHolder(private val binding: BadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Common) {
            binding.productName.text = product.product.name
        }

        fun unBind() {
            binding.productName.text = ""
        }
    }

    class DateViewHolder(private val binding: DateItemBinding, private val dateFormat: DateFormat) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Common) {
            binding.textView.text = dateFormat.format(product.data)
        }

        fun unBind() {
            binding.textView.text = ""
        }
    }

    class DiffBadProductCallback : DiffUtil.ItemCallback<Common>() {
        override fun areItemsTheSame(oldItem: Common, newItem: Common) =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Common, newItem: Common) = oldItem == newItem

    }
}