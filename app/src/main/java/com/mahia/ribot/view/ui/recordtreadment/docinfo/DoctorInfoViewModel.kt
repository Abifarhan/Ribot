package com.mahia.ribot.view.ui.recordtreadment.docinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.model.DoctorModel

class DoctorInfoViewModel: ViewModel() {
    private val oneItem = MutableLiveData<DoctorModel>()

    fun setDocterInfo(doctorId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("patients")
            .whereEqualTo("uid",uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    FirebaseFirestore.getInstance().collection("patients")
                        .document(it.documents[0].id).collection("doctorslist")
                        .whereEqualTo("doctors_id",doctorId)
                        .get()
                        .addOnSuccessListener {
                            Log.d(this.toString(),"ini data detail ${it.documents.get(0).getString("doctors_id")}")

                            val workPlace = it.documents.get(0).get("work_place") as HashMap<*,*>
                            val location = workPlace["location"]
                            val nameInstantion = workPlace["name"]

                            val doctorModel = DoctorModel(
                                it.documents.get(0).getString("name").toString(),
                                it.documents.get(0).getString("field").toString(),
                                it.documents.get(0).getString("doctors_id").toString(),
                                location.toString(),
                                nameInstantion.toString()
                            )
                            oneItem.postValue(doctorModel)
                        }
                }
            }
    }

    val docterInfo: LiveData<DoctorModel>
    get() = oneItem
}