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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahia.ribot.databinding.FragmentRecordTreatmentBinding
import com.mahia.ribot.model.RecordTreatmentModel
import com.mahia.ribot.view.ui.recordtreadment.docinfo.DocterInfoActivity

class RecordTreatmentFragment : Fragment() {

    private lateinit var recordTreadmentViewModel: RecordTreadmentViewModel
    private lateinit var _binding: FragmentRecordTreatmentBinding

    private val viewModel by lazy{
        ViewModelProviders.of(requireActivity()).get(RecordTreadmentViewModel::class.java)
    }

    private lateinit var adapter:RecordAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

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

        adapter = RecordAdapter(requireContext())
//        val docterAdapter = RecordAdapter(requireContext())
//        docterAdapter.onItemClick = { selectedData ->
//            val intent = Intent(activity, ListRecordActivity::class.java)
//            startActivity(intent)
//            Toast.makeText(activity, "anda klik $selectedData", Toast.LENGTH_SHORT).show()
//        }
        
        adapter.setOnItemClickCallback(object : RecordAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RecordTreatmentModel) {
                Toast.makeText(activity, "anda men klik id docter${data.doctorId}", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, DocterInfoActivity::class.java)
                intent.putExtra(DocterInfoActivity.EXTRA_DATA, data)
                startActivity(intent)
            }

        })
        _binding.rvRecordTreatment.layoutManager = LinearLayoutManager(activity)
        _binding.rvRecordTreatment.adapter = adapter
        observeData()
//        val uid = FirebaseAuth.getInstance().currentUser.uid
//        FirebaseFirestore.getInstance().collection("patient").whereEqualTo("uid",uid)
//            .get()
//            .addOnSuccessListener {
//                if (it.size() != 0) {
//                    Log.d(this.requireActivity().toString(), "ini uid Anda dari filter ${it.documents.get(0).getString("email")}")
//                    Log.d(this.requireActivity().toString(), "ini nik Anda dari filter ${it.documents.get(0).getString("nik")}")
//                    Log.d(this.requireActivity().toString(), "ini nak Anda dari filter ${it.documents.get(0).id}")
////                    Log.d(this.requireActivity().toString(), "ini nik Anda dari filter ${it.documents.get()}")
////                    it.documents.get(0).getDocumentReference(it.documents.get(0).id)?.collection("riwayatberobat")?.get()
////                        ?.addOnSuccessListener {
////                            Log.d(this.toString(),"ini isi dokumen anda ${it.documents}")
////                        }
////                    it.documents.get(0).getDocumentReference(it.documents.get(0).id).collection("riwayatberobat").get()
////                        .addOnSuccessListener {
////                            val doctor = it.documents.get(0).get("doctor") as HashMap<*, *>
////                            val nameDoctor = doctor["name"]
////                            val specialty = doctor["speciality"]
////                            Log.d(this.requireActivity().toString(), "ini data pasien dari filter nama docter $nameDoctor")
////                        }
//
//                    FirebaseFirestore.getInstance().collection("patient").document(it.documents.get(0).id).collection("riwayatberobat").get()
//                        .addOnSuccessListener {
//                            Log.d(this.toString(),"ini isi dokumen anda ${it.documents}")
//                            val doctor = it.documents.get(0).get("doctor") as HashMap<*, *>
//                            val nameDoctor = doctor["name"]
//                            val specialty = doctor["speciality"]
//                            Log.d(this.requireActivity().toString(), "ini data pasien dari filter nama docter $nameDoctor")
//                        }
//                }
//            }
    }

    private fun observeData() {
        viewModel.fetchPatientData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            Log.d(requireActivity().toString(),"ini data anda untuk daftar docter $it")
        })
    }


}