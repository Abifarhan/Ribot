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
        val firestore = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        profilePatientViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        ).get(ProfilePatientViewModel::class.java)
        profilePatientViewModel.setPatientInfo(uid.toString())
        profilePatientViewModel.patientInfo.observe(viewLifecycleOwner, {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                textViewNameProfil.text = it.name
                textViewEmailProfil.text = it.email
                textViewBmiStatus.text = it.condition
                textViewNikProfil.text = it.nik
                textViewPhoneNumberProfil.text = it.phoneNumber
                textViewCityProfil.text = it.city
                textViewProvinceProfil.text = it.province
                textViewBloodProfil.text = it.blood
                textViewWeight.text = it.weight
                textViewHeightProfil.text = it.height
                progressBar.visibility = View.GONE
            }
        })
    }

}