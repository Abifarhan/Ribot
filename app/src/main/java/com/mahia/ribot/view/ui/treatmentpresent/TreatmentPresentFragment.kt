package com.mahia.ribot.view.ui.treatmentpresent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahia.ribot.databinding.FragmentTreatmentPresentBinding

class TreatmentPresentFragment : Fragment() {

    private lateinit var treatmentPresentViewModel: TreatmentPresentViewModel
    private var _binding: FragmentTreatmentPresentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        treatmentPresentViewModel =
            ViewModelProvider(this).get(TreatmentPresentViewModel::class.java)

        _binding = FragmentTreatmentPresentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        treatmentPresentViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}