package com.mahia.ribot.view.ui.recordtreadment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahia.ribot.domain.data.network.PatientRepository
import com.mahia.ribot.model.RecordTreatmentModel

class RecordTreadmentViewModel: ViewModel() {

    fun fetchMedicalRecord(): LiveData<List<RecordTreatmentModel>> {
        val recordPatientMedical = MutableLiveData<ArrayList<RecordTreatmentModel>>()
        PatientRepository().getAllMedicalRecord().observeForever{ patientList ->
            recordPatientMedical.value = patientList as ArrayList
        }

        return recordPatientMedical as LiveData<List<RecordTreatmentModel>>
    }


//    fun fetchMedicalRecord(): LiveData<List<RecordTreatmentModel>> =
//        patientRepository.getAllRecordMedical()
//    }
}