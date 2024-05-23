package com.example.davidretakeexam.presenter.screen.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.davidretakeexam.R
import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.databinding.ListPersoItemBinding
import com.example.davidretakeexam.presenter.screen.homescreen.PersonListAdapter.PersonViewHolder

class PersonListAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<PersonEntity, PersonViewHolder>(DiffCallback()) {
    class PersonViewHolder(private val binding: ListPersoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(person: PersonEntity, clickListener: (String) -> Unit) {
            binding.apply {
                fullname.text = "${person.title} ${person.firstName} ${person.lastName}"

                Glide.with(root.context)
                    .load(person.profileImg)
                    .placeholder(R.drawable.default_profile)
                    .into(profileImg)

                root.setOnClickListener { clickListener(person.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding =
            ListPersoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(person, clickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<PersonEntity>() {
        override fun areItemsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem == newItem
        }
    }
}