package com.mahia.ribot.view.ui.recordtreadment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahia.ribot.R
import com.mahia.ribot.databinding.ItemPatientBinding
import com.mahia.ribot.model.RecordTreatmentModel
import java.text.SimpleDateFormat
import java.util.*


class RecordAdapter(private val context: Context) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    private var dataList = listOf<RecordTreatmentModel>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setListData(data: List<RecordTreatmentModel>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false))
//        return ViewHolder(view)


    override fun onBindViewHolder(holder: RecordAdapter.ViewHolder, position: Int) {
        val user = dataList[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPatientBinding.bind(itemView)
        fun bindView(docterList: RecordTreatmentModel) {
//            Glide.with(context).load(patient.)
            binding.textViewConclusionRecord.text = docterList.conclusion
//            binding.textViewDateRecord.text = docterList.date.toString()
            binding.textViewDescriptionRecord.text = docterList.description
            binding.textViewDocterIdRecord.text = docterList.doctorId
            binding.textViewSubjectRecord.text = docterList.subject
//            if (docterList.treatment?.size != null) {
//                binding.textViewTreatmentRecord.text = docterList.treatment?.get(position).toString()
//            }
            binding.textViewTreatmentRecord.text = docterList.treatment?.toString()?.replace("[","")?.replace("]","")

//            val output = StringBuilder()
//            for ()
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(docterList)
            }

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = docterList.date?.toDate()?.getTime()!!

            val localeID = Locale("in", "ID")
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", localeID)
            val timeFormat = SimpleDateFormat("h:mm a", localeID)

            val dateValue: String = dateFormat.format(calendar.time)
            val timeValue: String = timeFormat.format(calendar.time)
                        binding.textViewDateRecord.text = dateValue

            binding.buttonDetailDocter.setOnClickListener {
                onItemClickCallback?.onItemClicked(docterList)
            }
        }

//        init {
//            binding.root.setOnClickListener {
//                onItemClick?.invoke(dataList[adapterPosition])
//            }
//        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: RecordTreatmentModel)
    }
}