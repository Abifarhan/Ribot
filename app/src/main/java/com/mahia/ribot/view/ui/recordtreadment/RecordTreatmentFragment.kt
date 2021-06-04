package com.mahia.ribot.view.ui.recordtreadment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahia.ribot.databinding.FragmentRecordTreatmentBinding
import com.mahia.ribot.model.RecordTreatmentModel
import com.mahia.ribot.view.ui.recordtreadment.docinfo.DocterInfoActivity

class RecordTreatmentFragment : Fragment() {

    private lateinit var recordTreadmentViewModel: RecordTreadmentViewModel
    private lateinit var _binding: FragmentRecordTreatmentBinding
    private lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recordTreadmentViewModel =
            ViewModelProvider(this).get(RecordTreadmentViewModel::class.java)

        _binding = FragmentRecordTreatmentBinding.inflate(
            inflater,
            container, false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecordAdapter()
        adapter.setOnItemClickCallback(object : RecordAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RecordTreatmentModel) {
                val intent = Intent(activity, DocterInfoActivity::class.java)
                intent.putExtra(DocterInfoActivity.EXTRA_DATA, data)
                startActivity(intent)
            }

        })
        _binding.rvRecordTreatment.layoutManager = LinearLayoutManager(activity)
        _binding.rvRecordTreatment.adapter = adapter
        recordTreadmentViewModel.fetchMedicalRecord().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }


}