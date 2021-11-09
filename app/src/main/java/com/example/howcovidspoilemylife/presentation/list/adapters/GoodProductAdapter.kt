package com.example.howcovidspoilemylife.presentation.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.howcovidspoilemylife.databinding.BadItemBinding
import com.example.howcovidspoilemylife.databinding.DateItemBinding
import com.example.howcovidspoilemylife.presentation.models.Product

class GoodProductAdapter(private val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Product>()

    companion object {
        private const val DATE_HOLDER = 1
        private const val ITEM_HOLDER = 0
    }

    class ItemHolder(private val binding: BadItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
        }
    }

    class DateHolder(private val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product) {
            binding.textView.text = product.time.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position!= list.lastIndex && list[position].time != list[position+1].time){
            DATE_HOLDER
        } else {
            ITEM_HOLDER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = BadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemHolder(binding).apply {
                itemView.setOnClickListener {
                    onClick(list[adapterPosition])
                }
            }
        } else {
            val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            DATE_HOLDER -> (holder as DateHolder).bind(list[position])
            ITEM_HOLDER -> (holder as ItemHolder).bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}