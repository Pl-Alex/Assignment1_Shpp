package com.alexP.socialnetwork.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexP.socialnetwork.App
import com.alexP.socialnetwork.R
import com.alexP.socialnetwork.databinding.ActivityContactsBinding
import com.alexP.socialnetwork.ui.screens.base.BaseActivity
import com.alexP.socialnetwork.utils.SpacingItemDecorator


class ContactsActivity : BaseActivity<ActivityContactsBinding>() {
    private lateinit var adapter: ContactsAdapter

    private val vm: ContactsViewModel by viewModels {
        ContactsViewModel.createFactory((application as App).contactService)
    }

    override fun inflate(inflater: LayoutInflater): ActivityContactsBinding {
        return ActivityContactsBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = ContactsAdapter()

        vm.contacts.observe(this) {
            adapter.submitList(it.toMutableList())
        }

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