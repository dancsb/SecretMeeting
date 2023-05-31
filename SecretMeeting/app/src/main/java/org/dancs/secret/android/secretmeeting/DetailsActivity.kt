package org.dancs.secret.android.secretmeeting

import android.R.id.home
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.dancs.secret.android.secretmeeting.databinding.ActivityDetailsBinding
import org.dancs.secret.android.secretmeeting.model.Meeting
import org.dancs.secret.android.secretmeeting.model.Response
import org.dancs.secret.android.secretmeeting.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailsBinding

    private lateinit var meeting : Meeting

    companion object {
        const val EXTRA_MEETING = "extra.meeting"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        meeting = intent.getSerializableExtra(EXTRA_MEETING) as Meeting

        supportActionBar?.title = meeting.topic
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.MeetingDescTextView.text = meeting.desc
        binding.MeetingDescTextView.movementMethod = ScrollingMovementMethod()
        binding.MeetingDateTextView.text = SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.getDefault()).format(meeting.date)
        binding.MeetingLonTextView.text = "Longitude: " + meeting.coord.lon.toString()
        binding.MeetingLatTextView.text = "Latitude: " + meeting.coord.lat.toString()

        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == home) {
            finish()
            return true
        }
        if (item.itemId == R.id.action_delete) {
            deleteMeeting()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteMeeting(){
        NetworkManager.delMeeting(meeting._id)?.enqueue(object: Callback<Response?>  {
            override fun onResponse(
                call: Call<Response?>,
                response: retrofit2.Response<Response?>
            ) {
                Log.d("API", "onResponse: " + response.code())
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@DetailsActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Response?>, throwable: Throwable) {
                throwable.printStackTrace()
                Toast.makeText(this@DetailsActivity, "Network request error occurred, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(meeting.coord.lat.toDouble(), meeting.coord.lon.toDouble()))
                .title("Marker")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(meeting.coord.lat.toDouble(), meeting.coord.lon.toDouble()), 15F))
    }
}