package com.coolya.daunick.ui

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.coolya.daunick.R
import com.coolya.daunick.common.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_diary_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DiaryEventListFragment : BaseFragment() {

    private val viewModel by viewModel<DiaryEventViewModel> { parametersOf(null)}

    private val inputAdapter = DiaryEventAdapter().apply {
        onSelectClick = {
            findNavController().navigate(
                R.id.action_DiaryListFragment_to_DiaryEventDescriptionFragment,
                Bundle().apply { putLong("id", it.dairyId) })
        }
        onDeleteClick = {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setCancelable(true)
                .setPositiveButton("yes") { p0, p1 -> viewModel.deleteEvent(it) }
                .setNegativeButton("no") { p0, p1 -> }
                .show()


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener {
        when(it.itemId){
            R.id.action_save -> viewModel.getSharedPdf()
        }
            true
        }
        event_list_recycler.adapter = inputAdapter
        viewModel.diaryPagedList.observe(viewLifecycleOwner, Observer {
            inputAdapter.submitList(it)
        })
        viewModel.diaryPdfPath.observe(viewLifecycleOwner, Observer {
            val file = File(it)
            FileProvider.getUriForFile(
                requireContext(),
                "com.coolya.provider",
                file
            )

            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.coolya.provider",
                    file
                )
            } else {
                Uri.fromFile(file);
            }

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "application/pdf"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        })
        view.findViewById<FloatingActionButton>(R.id.diary_create_new).setOnClickListener {
            findNavController().navigate(R.id.action_DiaryListFragment_to_DiaryEventDescriptionFragment)
        }
    }

    override fun layout(): Int = R.layout.fragment_diary_list
    override fun title(): String? = "Events"

}