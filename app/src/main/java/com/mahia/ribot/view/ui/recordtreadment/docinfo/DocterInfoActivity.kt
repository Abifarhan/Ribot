package com.mahia.ribot.view.ui.recordtreadment.docinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.R
import com.mahia.ribot.databinding.ActivityDocterInfoBinding
import com.mahia.ribot.model.RecordTreatmentModel

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DocterInfoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDocterInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val extras = intent.getParcelableExtra<RecordTreatmentModel>(EXTRA_DATA)
        Toast.makeText(this, "ini data hasil anda kirim ${extras?.doctorId}", Toast.LENGTH_SHORT).show()

        val uid = FirebaseAuth.getInstance().currentUser.uid
        FirebaseFirestore.getInstance().collection("patients")
            .whereEqualTo("uid",uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    FirebaseFirestore.getInstance().collection("patients")
                        .document(it.documents[0].id).collection("doctorslist")
                        .whereEqualTo("doctors_id",extras?.doctorId)
                        .get()
                        .addOnSuccessListener {
                            Log.d(this.toString(),"ini data detail ${it.documents.get(0).getString("doctors_id")}")

                            binding.textViewDocterNameInfo.text = it.documents.get(0).getString("name")
                            binding.textViewFieldDocterInfo.text = it.documents.get(0).getString("field")
                            binding.textViewIcDocterInfo.text = it.documents.get(0).getString("doctors_id")
                            val workPlace = it.documents.get(0).get("work_place") as HashMap<*,*>
                            val location = workPlace["location"]
                            val nameInstantion = workPlace["name"]
                            binding.textViewLocationInfo.text = location.toString()
                            binding.textViewNameInstantionInfo.text = nameInstantion.toString()
                        }
                }
            }
    }

}
