package com.alexP.assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.ItemContactBinding
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.utils.loadCircularImage


class ContactsDiffCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}

class ContactsAdapter(
    private val userActionListener: IContactActionListener
) : ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(ContactsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)

        return ContactsViewHolder(binding, userActionListener)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactsViewHolder(
        private val binding: ItemContactBinding,
        private val userActionListener: IContactActionListener
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact) {
            with(binding) {
                itemView.tag = contact
                buttonTrash.tag = contact

                textViewContactFullName.text = contact.fullName
                textViewContactCareer.text = contact.career
                if (contact.photo.isNotBlank()) {
                    imageViewContactImage.loadCircularImage(contact.photo)
                } else {
                    imageViewContactImage.setImageResource(R.drawable.default_contact_image)
                }
            }
            binding.root.setOnClickListener{
                onDeleteContactClick(it)
            }
            binding.buttonTrash.setOnClickListener{
                onDeleteContactClick(it)
            }
        }

        private fun onDeleteContactClick(v: View) {
            val contact = v.tag as Contact
            when (v.id) {
                R.id.button_trash -> {
                    userActionListener.onContactDelete(contact)
                }
            }

        }
    }
}