package com.mahia.ribot.domain.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mahia.ribot.model.Const
import com.mahia.ribot.model.RecordTreatmentModel

class PatientRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    fun getPatientData(): LiveData<List<RecordTreatmentModel>> {
        val patientResult = MutableLiveData<List<RecordTreatmentModel>>()
        firestore.collection(Const.patients)
            .whereEqualTo(Const.uidPatient, uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    Log.d(this.toString(), "ini nik Anda ${it.documents.get(0).id}")
                    val listRecordMedical = ArrayList<RecordTreatmentModel>()
                    firestore.collection(Const.patients)
                        .document(it.documents[0].id).collection(Const.medicalHistory)
                        .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {
                            for (document in it) {
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
                                listRecordMedical.add(recordTreatmentModel)
                            }
                            patientResult.value = listRecordMedical

                        }

                }
            }
        return patientResult
    }
}
