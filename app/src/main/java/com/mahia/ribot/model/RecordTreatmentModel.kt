package com.mahia.ribot.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList

@Parcelize
data class RecordTreatmentModel(
    val conclusion: String = "",
    val date: Timestamp? = null,
    val description: String = "",
    val doctorId: String = "",
    val subject: String = "",
    val treatment: ArrayList<String>? = null,
) : Parcelable
