package com.alexP.socialnetwork.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexP.socialnetwork.R
import com.alexP.socialnetwork.databinding.ActivityContactsBinding
import com.alexP.socialnetwork.ui.screens.base.BaseActivity
import com.alexP.socialnetwork.utils.SpacingItemDecorator


class ContactsActivity : BaseActivity<ActivityContactsBinding>() {
    private lateinit var adapter: ContactsAdapter

    override fun inflate(inflater: LayoutInflater): ActivityContactsBinding {
        return ActivityContactsBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = ContactsAdapter()

        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            SpacingItemDecorator(
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_horizontal_spacing),
                resources.getDimensionPixelSize(R.dimen.contacts_recyclerView_vertical_spacing)
            )
        )

        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }

}