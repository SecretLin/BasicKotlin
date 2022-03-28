package com.example.basickotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basickotlin.data.Data
import com.example.basickotlin.databinding.ItemWordListBinding

class WordListAdapter(val clickListener: WordListClickListener) :
    ListAdapter<Data, WordListAdapter.WordListHolder>(WordDiffCallback()) {

    class WordListClickListener(
        val handleClick: (data: Data) -> Unit,
        val handleLongClick: (data: Data) -> Boolean
    ) {
        fun onClick(data: Data) = handleClick(data)
        fun onLongClick(data: Data):Boolean = handleLongClick(data)
    }

    class WordDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }


    }

    class WordListHolder private constructor(val databingding: ItemWordListBinding) :
        RecyclerView.ViewHolder(databingding.root) {
        val tv_name = databingding.tvName
        val tv_desc = databingding.tvDesc
        fun bind(word: Data, clickListener: WordListClickListener) {
//            tv_name.text = word.name
//            tv_desc.text = word.desc
            databingding.word = word
            databingding.clickListener = clickListener
            databingding.tvDesc.setOnClickListener {

            }
        }

        companion object {
            fun from(parent: ViewGroup): WordListHolder {
                return WordListHolder(
                    ItemWordListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListHolder {
        return WordListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordListHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, clickListener)
    }



//    override fun getItemCount(): Int {
//        return dataList.size
//    }
}