package com.mahia.ribot.view.ui.recordtreadment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahia.ribot.domain.data.network.ListRecordRepo
import com.mahia.ribot.model.RecordTreatmentModel

class RecordTreadmentViewModel : ViewModel() {

    private val repo = ListRecordRepo()
    fun fetchPatientData(): LiveData<MutableList<RecordTreatmentModel>> {
        val mutableData = MutableLiveData<MutableList<RecordTreatmentModel>>()
        repo.getPatientData().observeForever{patientList ->
            mutableData.value = patientList
        }

        return mutableData
    }
}