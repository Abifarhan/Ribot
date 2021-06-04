package com.mahia.ribot.domain.data.network

import androidx.lifecycle.LiveData
import com.mahia.ribot.model.RecordTreatmentModel

interface PatientDataSource {

    fun getAllRecordMedical(): LiveData<List<RecordTreatmentModel>>
}