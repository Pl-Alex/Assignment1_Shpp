package com.alexP.assignment1.ui.contactsActivity

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexP.assignment1.App
import com.alexP.assignment1.R
import com.alexP.assignment1.adapters.ContactsAdapter
import com.alexP.assignment1.adapters.IContactActionListener
import com.alexP.assignment1.databinding.ActivityContactsBinding
import com.alexP.assignment1.ui.BaseActivity
import com.alexP.assignment1.ui.addContactFragment.AddContactFragment
import com.alexP.assignment1.ui.addContactFragment.MyFragmentFactory
import com.alexP.assignment1.ui.authActivity.AuthActivity
import com.alexP.assignment1.ui.contactsActivity.ContactsViewModel
import com.alexP.assignment1.ui.utils.SpacingItemDecorator
import com.alexp.contactsprovider.data.Contact
import com.google.android.material.snackbar.Snackbar


class ContactsActivity : BaseActivity<ActivityContactsBinding>() {
    private lateinit var adapter: ContactsAdapter

    private lateinit var viewModel: ContactsViewModel

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

    private val onSaveAction: (Contact) -> Unit =
        { contact: Contact -> viewModel.addContact(contact) }

    override fun inflate(inflater: LayoutInflater): ActivityContactsBinding {
        return ActivityContactsBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MyFragmentFactory(onSaveAction)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ContactsViewModel.createFactory(application as App)
        )[ContactsViewModel::class.java]

        setRecyclerView()
        setListeners()
        tryToLoadContactsFromDevice()
    }

    private fun setListeners() {
        binding.addContactButton.setOnClickListener {
            showAddContactDialog()
        }
        binding.topAppBar.setNavigationOnClickListener {
            startActivity(
                Intent(this, AuthActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
            finish()
        }
    }

    private fun setRecyclerView() {
        adapter = ContactsAdapter(object : IContactActionListener {
            override fun onContactDelete(contact: Contact) {
                deleteContact(contact)
            }
        })

        adapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0)
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
                target: RecyclerView.ViewHolder,
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
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction
            .add(android.R.id.content, AddContactFragment::class.java, null)
            .addToBackStack(null)
            .commit()
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

    private fun loadContactsFromDevice() {
        viewModel.addContacts(contentResolver)
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