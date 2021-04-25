package com.coolya.daunick

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.coolya.daunick.common.BaseFragment
import com.coolya.daunick.ext.toStringDate
import com.coolya.daunick.ui.DiaryEventViewModel
import kotlinx.android.synthetic.main.fragment_diary_description.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*


class DiaryEventDescriptionFragment : BaseFragment() {


    private val viewModel by viewModel<DiaryEventViewModel> { parametersOf(arguments?.getLong("id")) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.diaryCreation.observe(viewLifecycleOwner, {
            activity?.onBackPressed()
        })

        viewModel.diaryEmoList.observe(viewLifecycleOwner, Observer {
            edt_emo.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line, it
                )
            )
        })
        viewModel.diaryEvent.observe(viewLifecycleOwner, Observer {
            edt_emo.setText(it.emo)
            edt_event.setText(it.event)
            edt_scale.setText(it.scale)
            edt_thoughts.setText(it.thoughts)
            date.setText(it.time.toStringDate())
        })
        date.setOnClickListener { showDateTimePicker() }
        diary_save.setOnClickListener {
            if (checkFields()) {
                viewModel.createOrUpdateDiary(
                    edt_emo.text!!.toString(),
                    edt_event.text!!.toString(),
                    edt_scale.text!!.toString(),
                    edt_thoughts.text!!.toString(),
                    date.text.toString()
                )
            }
        }
    }

    private fun showDateTimePicker() {
        val dateAndTime: Calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(), { p0, year, monthOfYear, dayOfMonth ->
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minuteOfDay ->
                        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateAndTime.set(Calendar.MINUTE, minuteOfDay);
                        date.text = dateAndTime.time.time.toStringDate()
                    }, dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true
                )
                    .show()
            },
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH]
        )
            .show()

    }

    private fun checkFields(): Boolean {
        var isFilled = true
        if (edt_emo.text.isNullOrEmpty()) {
            isFilled = false
            edt_emo.error = "Empty"
        }
        if (edt_event.text.isNullOrEmpty()) {
            isFilled = false
            edt_event.error = "Empty"
        }
        if (edt_scale.text.isNullOrEmpty()) {
            isFilled = false
            edt_scale.error = "Empty"
        }
        if (edt_thoughts.text.isNullOrEmpty()) {
            isFilled = false
            edt_thoughts.error = "Empty"
        }
        return isFilled
    }

    override fun layout(): Int = R.layout.fragment_diary_description

    override fun title(): String? = "Event"


}