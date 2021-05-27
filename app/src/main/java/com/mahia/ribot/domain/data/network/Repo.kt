package com.mahia.ribot.domain.data.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mahia.ribot.model.DocterModel
import com.mahia.ribot.model.RecordTreatmentModel

class Repo {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
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
//                    FirebaseFirestore.getInstance().collection("patients").document("1578963524147523").collection("medicalhistory")
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
//                                Log.d(this.toString(), "ini data riwayat anda ${document.id} => ${document.data}")
//                                Log.d(this.toString(), "ini data riwayat anda $test")
//                                Toast.makeText(this@HomeActivity, "ini data riwayat anda $record", Toast.LENGTH_SHORT).show()
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

//FirebaseFirestore.getInstance().collection("patient")
//.document("UU4jvBYaipabJw3ZXuQL04h89zv1").collection("riwayatberobat")
//.document("ZThZUNAXTkXutHamGD5K")
//.get()
//.addOnSuccessListener {
//    val obj = it.get("doctor") as HashMap<*,*>
//    val name = obj.get("name")
//    Toast.makeText(this, "your value is $name", Toast.LENGTH_SHORT).show()
//}