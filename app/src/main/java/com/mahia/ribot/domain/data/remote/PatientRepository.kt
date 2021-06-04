package com.mahia.ribot.domain.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mahia.ribot.model.Const
import com.mahia.ribot.model.Const.Companion.conclusion
import com.mahia.ribot.model.Const.Companion.date
import com.mahia.ribot.model.Const.Companion.description
import com.mahia.ribot.model.Const.Companion.doctorId
import com.mahia.ribot.model.Const.Companion.subject
import com.mahia.ribot.model.Const.Companion.treatment
import com.mahia.ribot.model.RecordTreatmentModel

class PatientRepository : PatientDataSource{
    private val firestore = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    fun getAllMedicalRecord(): LiveData<List<RecordTreatmentModel>> {
        val patientResult = MutableLiveData<List<RecordTreatmentModel>>()
        firestore.collection(Const.patients)
            .whereEqualTo(Const.uidPatient, uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    val listRecordMedical = ArrayList<RecordTreatmentModel>()
                    firestore.collection(Const.patients)
                        .document(it.documents[0].id).collection(Const.medicalHistory)
                        .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {
                            for (document in it) {
                                val recordTreatmentModel = RecordTreatmentModel(
                                    document.getString(conclusion)!!,
                                    document.getTimestamp(date),
                                    document.getString(description)!!,
                                    document.getString(doctorId)!!,
                                    document.getString(subject)!!,
                                    document.get(treatment) as ArrayList<String>
                                )
                                listRecordMedical.add(recordTreatmentModel)
                            }
                            patientResult.postValue(listRecordMedical)

                        }
                        .addOnFailureListener{
                            Log.d(this.toString(), "Proses mengambil data gagal, mohon periksa kembali koneksi internet Anda")
                        }
                }else{
//                    Toast.makeText("Tidak ada data", "Anda tidak memiliki riwayat pengobatan", Toast.LENGTH_SHORT).show()
                }
            }
        return patientResult
    }

    override fun getAllRecordMedical(): LiveData<List<RecordTreatmentModel>> {
        val patientResult = MutableLiveData<List<RecordTreatmentModel>>()
        firestore.collection(Const.patients)
            .whereEqualTo(Const.uidPatient, uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    val listRecordMedical = ArrayList<RecordTreatmentModel>()
                    firestore.collection(Const.patients)
                        .document(it.documents[0].id).collection(Const.medicalHistory)
                        .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {
                            for (document in it) {
                                val recordTreatmentModel = RecordTreatmentModel(
                                    document.getString(conclusion)!!,
                                    document.getTimestamp(date),
                                    document.getString(description)!!,
                                    document.getString(doctorId)!!,
                                    document.getString(subject)!!,
                                    document.get(treatment) as ArrayList<String>
                                )
                                listRecordMedical.add(recordTreatmentModel)
                            }
                            patientResult.postValue(listRecordMedical)

                        }
                        .addOnFailureListener{
                            Log.d(this.toString(), "Proses mengambil data gagal, mohon periksa kembali koneksi internet Anda")
                        }
                }else{
//                    Toast.makeText("Tidak ada data", "Anda tidak memiliki riwayat pengobatan", Toast.LENGTH_SHORT).show()
                }
            }
        return patientResult
    }
}
