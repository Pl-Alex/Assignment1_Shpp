package com.alexP.assignment1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.ItemContactBinding
import com.alexP.assignment1.model.Contact

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    var contacts: List<Contact> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
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
}

fun ImageView.loadCircularImage(imageLink: String) {
    Glide.with(this)
        .load(imageLink)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.default_contact_image)
        .error(R.drawable.default_contact_image)
        .into(this)
}
