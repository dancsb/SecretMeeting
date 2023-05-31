package org.dancs.secret.android.secretmeeting.network

import org.dancs.secret.android.secretmeeting.model.Meeting
import org.dancs.secret.android.secretmeeting.model.Response
import retrofit2.Call
import retrofit2.http.*

interface MeetingApi {
    @GET("/meeting")
    fun getMeetings(
        @Query("apikey") apikey: String?
    ): Call<List<Meeting?>?>?

    @POST("/meeting")
    fun newMeeting(
        @Body newMeeting: Meeting?,
        @Query("apikey") apikey: String?
    ): Call<Meeting?>?

    @DELETE("/meeting/{id}")
    fun delMeeting(
        @Path("id") meeting_id: String?,
        @Query("apikey") apikey: String?
    ): Call<Response?>?
}