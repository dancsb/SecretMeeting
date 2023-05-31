package org.dancs.secret.android.secretmeeting.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.dancs.secret.android.secretmeeting.databinding.DialogNewMeetingBinding
import org.dancs.secret.android.secretmeeting.model.Coord
import org.dancs.secret.android.secretmeeting.model.Meeting
import java.util.*

class NewMeetingDialogFragment : DialogFragment() {
    interface NewMeetingDialogListener {
        fun onMeetingCreated(newMeeting: Meeting)
        fun onMissingData()
    }

    private lateinit var listener: NewMeetingDialogListener

    private lateinit var binding: DialogNewMeetingBinding

    private var cal = Calendar.getInstance()
    private var dateSet: Boolean = false
    private var timeSet: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewMeetingDialogListener
            ?: throw RuntimeException("Activity must implement the NewMeetingDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewMeetingBinding.inflate(LayoutInflater.from(context))

        binding.btnDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            activity?.let { it1 ->
                DatePickerDialog(it1, { _, year, monthOfYear, dayOfMonth ->

                    binding.inDate.setText("" + year + ". " + (monthOfYear + 1) + ". " + dayOfMonth + ".")

                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    dateSet = true

                }, year, month, day)
            }?.show()
        }

        binding.btnTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            activity?.let { it1 ->
                TimePickerDialog(it1, { _, hour, minute ->

                    binding.inTime.setText("" + hour + ":" + minute)

                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)
                    timeSet = true

                }, hour, minute, true)
            }?.show()
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("New Meeting")
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                if (isValid()) {
                    listener.onMeetingCreated(getMeetingItem())
                }
                else {
                    listener.onMissingData()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun isValid() = binding.MeetingTopicEditText.text.isNotEmpty() && binding.MeetingDescEditText.text.isNotEmpty() && binding.MeetingLongitudeEditText.text.isNotEmpty() && binding.MeetingLatitudeEditText.text.isNotEmpty() && dateSet && timeSet

    private fun getMeetingItem() = Meeting(
        _id = "0",
        topic = binding.MeetingTopicEditText.text.toString(),
        desc = binding.MeetingDescEditText.text.toString(),
        date = cal.time,
        coord = Coord(binding.MeetingLongitudeEditText.text.toString().toFloat(), binding.MeetingLatitudeEditText.text.toString().toFloat())
    )

    companion object {
        const val TAG = "NewMeetingDialogFragment"
    }
}