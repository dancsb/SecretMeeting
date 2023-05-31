package org.dancs.secret.android.secretmeeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dancs.secret.android.secretmeeting.databinding.ItemMeetingBinding
import org.dancs.secret.android.secretmeeting.model.Meeting
import java.text.SimpleDateFormat
import java.util.*

class MeetingAdapter(private val listener: OnMeetingSelectedListener) : RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>() {
    private val meetings: MutableList<Meeting> = ArrayList()

    interface OnMeetingSelectedListener {
        fun onMeetingSelected(meeting: Meeting?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meeting, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val item = meetings[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = meetings.size

    fun clearMeetings() {
        val temp = meetings.size
        meetings.clear()
        notifyItemRangeRemoved(0, temp)
    }

    fun addMeeting(meeting: Meeting) {
        meetings.add(meeting)
        notifyItemInserted(meetings.size - 1)
    }

    inner class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemMeetingBinding.bind(itemView)
        private var item: Meeting? = null

        init {
            binding.root.setOnClickListener { listener.onMeetingSelected(item) }
        }

        fun bind(newMeeting: Meeting) {
            item = newMeeting
            binding.MeetingItemTopicTextView.text = item!!.topic.substring(0, item!!.topic.length.coerceAtMost(30)) + if(item!!.topic.length > 30) ".." else ""
            binding.MeetingItemDescTextView.text = item!!.desc.substring(0, item!!.desc.length.coerceAtMost(80)) + if(item!!.desc.length > 80) ".." else ""
            binding.MeetingItemDateTextView.text = SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.getDefault()).format(item!!.date)
        }
    }
}