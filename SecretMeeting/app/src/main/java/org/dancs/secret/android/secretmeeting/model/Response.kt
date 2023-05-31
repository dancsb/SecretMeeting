package org.dancs.secret.android.secretmeeting.model

import java.io.Serializable

data class Response(
    var code: Int,
    var message: String
) : Serializable