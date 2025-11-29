package com.rahul.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahul.mynotes.databinding.NoteItemBinding
import com.rahul.mynotes.model.NoteResponse

class NoteAdapter(): ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtils()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAdapter.NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        var note = getItem(position)
        note?.let {
            holder.bind(note)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteResponse){
            binding.txtTitle.text = note.title
            binding.txtDescription.text = note.description
        }
    }

    class ComparatorDiffUtils(): DiffUtil.ItemCallback<NoteResponse>(){
        override fun areItemsTheSame(
            oldItem: NoteResponse,
            newItem: NoteResponse
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: NoteResponse,
            newItem: NoteResponse
        ): Boolean {
            return oldItem == newItem
        }

    }
}