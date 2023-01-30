package com.example.arlibappstest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.arlibappstest.databinding.LayoutItemBinding
import com.example.arlibappstest.domain.Drug

class DrugAdapter(
    private val onClick: ((Drug) -> Unit),
) : RecyclerView.Adapter<DrugAdapter.ViewHolder>() {
    private lateinit var binding: LayoutItemBinding
    private val differCallback = object : DiffUtil.ItemCallback<Drug>() {
        override fun areItemsTheSame(oldItem: Drug, newItem: Drug): Boolean {
            return newItem.name == oldItem.name && newItem.dose == oldItem.dose && newItem.strength == oldItem.strength
        }

        override fun areContentsTheSame(
            oldItem: Drug,
            newItem: Drug
        ) = oldItem == newItem

    }
    private var mDiffer: AsyncListDiffer<Drug> = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Drug>) = mDiffer.submitList(list)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(drug = mDiffer.currentList[position])
    }

    override fun getItemCount() = mDiffer.currentList.size

    inner class ViewHolder(private val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(drug: Drug) {
            binding.drug = drug
            binding.root.setOnClickListener { onClick(drug) }
        }

    }
}