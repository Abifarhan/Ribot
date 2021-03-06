package com.mahia.ribot.view.ui.recordtreadment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahia.ribot.data.remote.firestore.PatientRepository
import com.mahia.ribot.model.RecordTreatmentModel

class RecordTreatmentsViewModel: ViewModel() {

    fun fetchMedicalRecord(): LiveData<List<RecordTreatmentModel>> {
        val recordPatientMedical = MutableLiveData<ArrayList<RecordTreatmentModel>>()
        PatientRepository().getAllMedicalRecord().observeForever{ patientList ->
            recordPatientMedical.setValue(patientList as ArrayList)
        }
        return recordPatientMedical as LiveData<List<RecordTreatmentModel>>
    }
}