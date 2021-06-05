package com.mahia.ribot.view.ui.recordtreadment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahia.ribot.databinding.FragmentRecordTreatmentBinding
import com.mahia.ribot.model.RecordTreatmentModel
import com.mahia.ribot.view.ui.recordtreadment.docinfo.DocterInfoActivity

class RecordTreatmentFragment : Fragment() {
    private lateinit var recordTreatmentsViewModel: RecordTreatmentsViewModel
    private lateinit var _binding: FragmentRecordTreatmentBinding
    private lateinit var adapter: RecordAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recordTreatmentsViewModel =
            ViewModelProvider(this).get(RecordTreatmentsViewModel::class.java)
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
        _binding.progressBar.visibility = View.VISIBLE
        _binding.rvRecordTreatment.layoutManager = LinearLayoutManager(activity)
        _binding.rvRecordTreatment.adapter = adapter
        recordTreatmentsViewModel.fetchMedicalRecord().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            _binding.progressBar.visibility = View.GONE
            adapter.notifyDataSetChanged()
        })
    }
}