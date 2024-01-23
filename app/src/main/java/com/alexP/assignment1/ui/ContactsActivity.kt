package com.alexP.assignment1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexP.assignment1.App
import com.alexP.assignment1.R
import com.alexP.assignment1.adapters.ContactsAdapter
import com.alexP.assignment1.databinding.ActivityContactsBinding
import com.alexP.assignment1.model.ContactsService
import com.alexP.assignment1.model.ContactsListener
import com.alexP.assignment1.ui.utils.SpacingItemDecorator
import com.alexP.assignment1.ui.utils.factory
import com.alexP.assignment1.viewModels.ContactsViewModel

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsAdapter

    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory())[ContactsViewModel::class.java]

        adapter = ContactsAdapter()

        viewModel.contacts.observe(this, Observer {
            adapter.contacts = it
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            SpacingItemDecorator(
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_horizontal_spacing),
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_vertical_spacing)
            )
        )

    }
}