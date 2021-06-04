package com.mahia.ribot.view.ui.recordtreadment.docinfo

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahia.ribot.R
import com.mahia.ribot.databinding.ActivityDocterInfoBinding
import com.mahia.ribot.model.RecordTreatmentModel

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DocterInfoActivity : AppCompatActivity() {

    private lateinit var doctorInfoViewModel: DoctorInfoViewModel
    var binding: ActivityDocterInfoBinding? = null

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocterInfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        val extras = intent.getParcelableExtra<RecordTreatmentModel>(EXTRA_DATA)
        Toast.makeText(this, "ini data hasil anda kirim ${extras?.doctorId}", Toast.LENGTH_SHORT)
            .show()

        doctorInfoViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DoctorInfoViewModel::class.java)
        doctorInfoViewModel.setDocterInfo(extras?.doctorId.toString())
        doctorInfoViewModel.docterInfo.observe(this, {
            binding.apply {
                this!!.textViewDocterNameInfo.text = it.name
                textViewFieldDocterInfo.text = it.field
                textViewIcDocterInfo.text = it.doctorId
                textViewLocationInfo.text = it.location
                textViewNameInstantionInfo.text = it.nameInstantion
            }
        })
        takePictureDocter()
    }

    private fun takePictureDocter() {
        Glide.with(this)
            .load(R.drawable.doctor)
            .centerCrop()
            .apply(RequestOptions.overrideOf(600, 600))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(
                        this@DocterInfoActivity,
                        "Gagal mengupload Gambar",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Palette.from(resource?.toBitmap()!!)
                        .generate {
                            val intColor = it?.vibrantSwatch?.rgb ?: 0
                            binding?.imageViewDocter?.setBackgroundColor(intColor)
                        }
                    return false
                }
            })
            .into(binding?.imageViewDocter!!)
    }

}
