package com.alexP.assignment1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.R
import com.alexP.assignment1.adapters.ContactActionListener
import com.alexP.assignment1.adapters.ContactsAdapter
import com.alexP.assignment1.databinding.ActivityContactsBinding
import com.alexP.assignment1.model.Contact
import com.alexP.assignment1.ui.utils.SpacingItemDecorator
import com.alexP.assignment1.ui.utils.factory
import com.alexP.assignment1.viewModels.ContactsViewModel
import com.google.android.material.snackbar.Snackbar

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsAdapter

    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory())[ContactsViewModel::class.java]

        adapter = ContactsAdapter(object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                deleteContact(contact)
            }
        })

        viewModel.contacts.observe(this) {
            adapter.contacts = it
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteContact(viewHolder.itemView.tag as Contact)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

            }
        }).attachToRecyclerView(binding.recyclerView)


        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            SpacingItemDecorator(
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_horizontal_spacing),
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_vertical_spacing)
            )
        )

        binding.addContactButton.setOnClickListener(
            null
        )

    }

    private fun deleteContact(contact: Contact) {
        viewModel.deleteContact(contact)

        val snackbar = Snackbar.make(
            binding.root,
            "Contact deleted",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("UNDO") {
            viewModel.recoverContacts()
        }
        snackbar.show()
    }

}