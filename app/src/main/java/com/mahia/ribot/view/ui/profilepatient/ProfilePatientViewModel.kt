package com.mahia.ribot.view.ui.profilepatient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.model.Const.Companion.patients
import com.mahia.ribot.model.Const.Companion.uidPatient
import com.mahia.ribot.model.PatientModel

class ProfilePatientViewModel : ViewModel() {

    private val oneItem = MutableLiveData<PatientModel>()
    private val firestore = FirebaseFirestore.getInstance()
    fun setPatientInfo(patientUid: String) {
        firestore.collection(patients)
            .whereEqualTo(uidPatient, patientUid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    firestore.collection(patients)
                        .document(it.documents[0].id)
                        .get()
                        .addOnSuccessListener {



                            val address = it.get("address") as HashMap<*, *>
                            val city = "kota : " + address["City"]
                            val province = "Province : " + address["province"]
                            val personal = it.get("bio_profile") as HashMap<*, *>
                            val blood = "Gol Darah : ${personal["blood"]}"
                            val weight = "${personal["weight"]} kg"
                            val height = "${personal["height"]} cm"

                            val patientModel = PatientModel(
                                it.getString("name").toString(),
                                it.getString("email").toString(),
                                "kondisi Anda : " + it.getString("bmi_status"),
                                "nomor NIK Anda : " + it.getString("nik"),
                                it.getString("phone_number").toString(),
                                city,
                                province,
                                blood,
                                weight,
                                height
                            )
                            oneItem.postValue(patientModel)
                        }
                }
            }
    }

    val patientInfo: LiveData<PatientModel>
    get() = oneItem
}