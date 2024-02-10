package com.alexP.assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.R
import com.alexP.assignment1.databinding.ItemContactBinding
import com.alexP.assignment1.model.Contact
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

interface ContactActionListener {

    fun onContactDelete(contact: Contact)
}

class ContactsDiffCallback(
    private val oldList: List<Contact>, private val newList: List<Contact>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact.id == newContact.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact == newContact
    }

}

class ContactsAdapter(
    private val userActionListener: ContactActionListener
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), View.OnClickListener {

    var contacts: List<Contact> = emptyList()
        set(newValue) {
            val diffCallback = ContactsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.buttonTrash.setOnClickListener(this)

        return ContactsViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
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