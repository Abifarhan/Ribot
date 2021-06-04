package com.mahia.ribot.view.ui.recordtreadment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahia.ribot.domain.data.network.PatientRepository
import com.mahia.ribot.model.RecordTreatmentModel

class RecordTreadmentViewModel : ViewModel() {

    fun fetchPatientData(): LiveData<List<RecordTreatmentModel>> {
        val recordPatientMedical = MutableLiveData<List<RecordTreatmentModel>>()
        PatientRepository().getPatientData().observeForever{ patientList ->
            recordPatientMedical.value = patientList
        }

        return recordPatientMedical
    }
}