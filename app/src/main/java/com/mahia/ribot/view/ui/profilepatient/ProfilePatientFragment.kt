package com.mahia.ribot.view.ui.profilepatient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}