package com.alexP.socialnetwork.ui.screens.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexP.socialnetwork.App
import com.alexP.socialnetwork.R
import com.alexP.socialnetwork.databinding.ActivityContactsBinding
import com.alexP.socialnetwork.ui.screens.base.BaseActivity
import com.alexP.socialnetwork.utils.SpacingItemDecorator
import com.alexp.contactsprovider.Contact
import com.google.android.material.snackbar.Snackbar


class ContactsActivity : BaseActivity<ActivityContactsBinding>() {
    private lateinit var adapter: ContactsAdapter

    private val vm: ContactsViewModel by viewModels {
        ContactsViewModel.createFactory((application as App).contactService)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                loadContactsFromDevice()
            } else {
                Snackbar.make(
                    this,
                    binding.root,
                    getString(R.string.permission_not_granted),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    override fun inflate(inflater: LayoutInflater): ActivityContactsBinding {
        return ActivityContactsBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        tryToLoadContactsFromDevice()
    }

    private fun setRecyclerView() {
        adapter = ContactsAdapter(object : IContactActionListener {
            override fun onContactDelete(contact: Contact) {
                deleteContact(contact)
            }
        })

        vm.contacts.observe(this) {
            adapter.submitList(it.toMutableList())
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val contact = vm.contacts.value?.get(viewHolder.adapterPosition)
                contact?.let { deleteContact(contact) }
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

        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }

    private fun deleteContact(contact: Contact) {
        vm.deleteContact(contact)
    }

    private fun loadContactsFromDevice() {
        vm.addContacts(contentResolver)
    }

    private fun tryToLoadContactsFromDevice() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ),
            -> {
                loadContactsFromDevice()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }
        }
    }

}