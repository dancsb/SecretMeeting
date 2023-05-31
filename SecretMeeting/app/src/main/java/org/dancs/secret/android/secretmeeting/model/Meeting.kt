package org.dancs.secret.android.secretmeeting.model

import java.io.Serializable
import java.util.*

data class Meeting(
    var _id: String,
    var topic: String,
    var desc: String,
    var date: Date,
    var coord: Coord
) : Serializable