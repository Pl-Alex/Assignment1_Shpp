package com.alexP.assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.ItemContactBinding
import com.alexP.assignment1.model.Contact
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

interface ContactActionListener {

    fun onContactDelete(contact: Contact)
}

class ContactsDiffCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}

class ContactsAdapter(
    private val userActionListener: ContactActionListener
) : ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(ContactsDiffCallback()), View.OnClickListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.buttonTrash.setOnClickListener(this)

        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = getItem(position)
        with(holder.binding) {
            holder.itemView.tag = contact
            buttonTrash.tag = contact

            textViewContactFullName.text = contact.fullName
            textViewContactCareer.text = contact.career
            if (contact.photo.isNotBlank()) {
                imageViewContactImage.loadCircularImage(contact.photo)
            } else {
                imageViewContactImage.setImageResource(R.drawable.default_contact_image)
            }
        }
    }

    class ContactsViewHolder(
        val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val contact = v.tag as Contact
        when (v.id) {
            R.id.button_trash -> {
                userActionListener.onContactDelete(contact)
            }
        }
    }
}

fun ImageView.loadCircularImage(imageLink: String) {
    Glide.with(this).load(imageLink).apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.default_contact_image).error(R.drawable.default_contact_image)
        .into(this)
}