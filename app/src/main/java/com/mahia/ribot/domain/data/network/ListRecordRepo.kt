package com.mahia.ribot.domain.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mahia.ribot.model.RecordTreatmentModel

class ListRecordRepo {
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    fun getPatientData(): LiveData<MutableList<RecordTreatmentModel>> {
        val mutableData = MutableLiveData<MutableList<RecordTreatmentModel>>()
        FirebaseFirestore.getInstance().collection("patients")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    Log.d(this.toString(), "ini nik Anda ${it.documents.get(0).id}")
                    val listData = mutableListOf<RecordTreatmentModel>()
                    FirebaseFirestore.getInstance().collection("patients")
                        .document(it.documents[0].id).collection("medicalhistory")
                        .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {
                            for (document in it) {
                                val record = it.documents.size
                                val conclusion = document.getString("conclusion")
                                val date = document.getTimestamp("date")
                                val description = document.getString("description")
                                val doctorId = document.getString("doctors_id")
                                val subject = document.getString("subject")
                                val treatment = document.get("treatment") as ArrayList<String>
                                val recordTreatmentModel = RecordTreatmentModel(
                                    conclusion!!,
                                    date,
                                    description!!,
                                    doctorId!!,
                                    subject!!,
                                    treatment
                                )
                                listData.add(recordTreatmentModel)
                            }
                            mutableData.value = listData

                        }

                }
            }
        return mutableData
    }
}
