package org.dancs.secret.android.secretmeeting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.dancs.secret.android.secretmeeting.databinding.ActivityMeetingBinding
import org.dancs.secret.android.secretmeeting.fragments.NewMeetingDialogFragment
import org.dancs.secret.android.secretmeeting.model.Meeting
import org.dancs.secret.android.secretmeeting.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetingActivity : AppCompatActivity(), MeetingAdapter.OnMeetingSelectedListener, NewMeetingDialogFragment.NewMeetingDialogListener{

    private lateinit var binding: ActivityMeetingBinding
    private lateinit var adapter: MeetingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            NewMeetingDialogFragment().show(
                supportFragmentManager,
                NewMeetingDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter.clearMeetings()
        loadMeetings()
    }

    private fun loadMeetings() {
        NetworkManager.getMeetings()?.enqueue(object : Callback<List<Meeting?>?> {
            override fun onResponse(
                call: Call<List<Meeting?>?>,
                response: Response<List<Meeting?>?>
            ) {
                Log.d("API", "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayMeetings(response.body())
                } else {
                    Toast.makeText(this@MeetingActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<List<Meeting?>?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@MeetingActivity, "Network request error occurred, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun createMeeting(newMeeting: Meeting) {
        NetworkManager.newMeeting(newMeeting)?.enqueue(object : Callback<Meeting?> {
            override fun onResponse(
                call: Call<Meeting?>,
                response: Response<Meeting?>
            ) {
                Log.d("API", "onResponse: " + response.code())
                if (response.isSuccessful) {
                    response.body()?.let { adapter.addMeeting(it) }
                } else {
                    Toast.makeText(this@MeetingActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<Meeting?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@MeetingActivity, "Network request error occurred, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayMeetings(receivedMeetings: List<Meeting?>?) {
        for (meeting in receivedMeetings!!) {
                adapter.addMeeting(meeting!!)
        }
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MeetingAdapter(this)
        binding.mainRecyclerView.adapter = adapter
    }

    override fun onMeetingSelected(meeting: Meeting?) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@MeetingActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_MEETING, meeting)
        startActivity(showDetailsIntent)
    }

    override fun onMeetingCreated(newMeeting: Meeting) {
        createMeeting(newMeeting)
    }

    override fun onMissingData() {
        Toast.makeText(this@MeetingActivity, "Error: Fill in all fields!", Toast.LENGTH_LONG).show()
    }
}