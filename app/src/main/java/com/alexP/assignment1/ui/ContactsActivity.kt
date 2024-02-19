package com.alexP.assignment1.ui

import android.content.pm.PackageManager
import com.alexP.assignment1.dataProviders.ContactsLoader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
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

class ContactsActivity : AppCompatActivity(), AddContactFragment.OnContactSavedListener {

    companion object {
        const val PERMISSION_REQ_READ_CONTACTS = 100
    }

    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsAdapter

    private lateinit var viewModel: ContactsViewModel

    private var isXLargeLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isXLargeLayout = resources.getBoolean(R.bool.xLarge_layout)

        viewModel = ViewModelProvider(this, factory())[ContactsViewModel::class.java]

        setRecyclerView()
        setListeners()

        ContactsLoader(this, viewModel).loadContacts()
    }

    private fun setListeners() {
        binding.addContactButton.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun setRecyclerView() {
        adapter = ContactsAdapter(object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                deleteContact(contact)
            }
        })

        adapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(positionStart == 0)
                    binding.recyclerView.scrollToPosition(positionStart)
            }
        })

        viewModel.contacts.observe(this) {
            adapter.submitList(it.toMutableList())
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

    private fun showAddContactDialog() {
        val fragmentManager = supportFragmentManager
        val fragment = AddContactFragment()
        fragment.setOnContactSavedListener(this)
        if (isXLargeLayout) {
            fragment.show(fragmentManager, "dialog")
        } else {
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction
                .add(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModel.deleteContact(contact)

        val snackbar = Snackbar.make(
            binding.root,
            getString(R.string.contact_deleted),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(getString(R.string.undo)) {
            viewModel.recoverContacts()
        }
        snackbar.show()
    }

    override fun onContactSaved(contact: Contact) {
        viewModel.addContact(contact)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQ_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContactsLoader(this, viewModel).loadContacts()
            } else {
                Snackbar.make(
                    this,
                    binding.root,
                    getString(R.string.permission_not_granted),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}