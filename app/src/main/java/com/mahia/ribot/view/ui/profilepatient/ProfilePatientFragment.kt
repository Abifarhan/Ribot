package com.mahia.ribot.view.ui.profilepatient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.databinding.FragmentProfilePatientBinding

class ProfilePatientFragment : Fragment() {

    private lateinit var profilePatientViewModel: ProfilePatientViewModel
    private lateinit var _binding: FragmentProfilePatientBinding

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profilePatientViewModel =
            ViewModelProvider(this).get(ProfilePatientViewModel::class.java)

        _binding = FragmentProfilePatientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("patients")
            .whereEqualTo("uid",uid)
            .get()
            .addOnSuccessListener {
                if (it.size() != 0) {
                    FirebaseFirestore.getInstance().collection("patients")
                        .document(it.documents[0].id)
                        .get()
                        .addOnSuccessListener {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                textViewNameProfil.text = it.getString("name")
                                textViewEmailProfil.text = it.getString("email")
                                textViewBmiStatus.text = "kondisi Anda : " + it.getString("bmi_status")
                                textViewNikProfil.text = "nomor NIK Anda : "+ it.getString("nik")
                                textViewPhoneNumberProfil.text = it.getString("phone_number")

                                val address = it.get("address") as HashMap<*,*>
                                val city = "kota : "+ address["City"]
                                val province = "Province : " + address["province"]

                                textViewCityProfil.text = city
                                textViewProvinceProfil.text = province

                                val personal = it.get("bio_profile") as HashMap<*,*>
                                val blood = "Gol Darah : ${personal["blood"]}"
                                val weight = "${personal["weight"]} kg"
                                val height = "${personal["height"]} cm"

                                textViewBloodProfil.text = blood
                                textViewWeight.text = weight
                                textViewHeightProfil.text = height
                                progressBar.visibility = View.GONE
                            }

                        }
                }
            }
    }

}