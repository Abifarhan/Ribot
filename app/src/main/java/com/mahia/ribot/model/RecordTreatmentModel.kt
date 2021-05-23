package com.mahia.ribot.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Parcelize
data class RecordTreatmentModel(
    val conclusion: String = "default",
    val date: Timestamp? = null,
    val description: String = "default",
    val doctorId: String = "default",
    val subject: String = "default",
//    val Location: String = "default",
//    val subject: String = "default",
//    val subject: String = "default",
    val treatment: ArrayList<String>? = null,
) : Parcelable
