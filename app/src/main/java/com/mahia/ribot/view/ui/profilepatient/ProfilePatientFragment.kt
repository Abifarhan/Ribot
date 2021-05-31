package com.mahia.ribot.view.ui.profilepatient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.databinding.FragmentProfilePatientBinding

class ProfilePatientFragment : Fragment() {

    private lateinit var profilePatientViewModel: ProfilePatientViewModel
    private var _binding: FragmentProfilePatientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profilePatientViewModel =
            ViewModelProvider(this).get(ProfilePatientViewModel::class.java)

        _binding = FragmentProfilePatientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

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
                            Log.d(this.toString(),"ini data profil Anda ${it.getString("email")}")

                            binding.textViewNameProfil.text = it.getString("name")
                            binding.textViewEmailProfil.text = it.getString("email")
                            binding.textViewBmiStatus.text = it.getString("bmi_status")
                            binding.textViewNikProfil.text = it.getString("nik")
                            binding.textViewPhoneNumberProfil.text = it.getString("phone_number")

                            val address = it.get("address") as HashMap<*,*>
                            val city = "kota : "+ address["City"]
                            val province = "Province : " + address["province"]

                            binding.textViewCityProfil.text = city.toString()
                            binding.textViewProvinceProfil.text = province.toString()

                            val personal = it.get("bio_profile") as HashMap<*,*>
                            val blood = "Gol Darah : ${personal["blood"]}"
                            val weight = "${personal["weight"]} kg"
                            val height = "${personal["height"]} cm"

                            binding.textViewBloodProfil.text = blood.toString()
                            binding.textViewWeight.text = weight.toString()
                            binding.textViewHeightProfil.text = height.toString()
                        }
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}