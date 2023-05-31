package org.dancs.secret.android.secretmeeting.network

import okhttp3.OkHttpClient
import org.dancs.secret.android.secretmeeting.model.Meeting
import org.dancs.secret.android.secretmeeting.model.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkManager {
    private val retrofit: Retrofit
    private val meetingApi: MeetingApi

    private const val SERVICE_URL = "https://secret.dancs.org"
    private const val API_KEY = "c1f01bd8e9c940498271ba57967ae7a5"

    init {

        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        meetingApi = retrofit.create(MeetingApi::class.java)
    }

    fun getMeetings(): Call<List<Meeting?>?>? {
        return meetingApi.getMeetings(API_KEY)
    }

    fun newMeeting(newMeeting: Meeting?): Call<Meeting?>? {
        return meetingApi.newMeeting(newMeeting, API_KEY)
    }

    fun delMeeting(meeting_id: String?): Call<Response?>? {
        return meetingApi.delMeeting(meeting_id, API_KEY)
    }
}