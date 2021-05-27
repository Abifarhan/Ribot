package com.mahia.ribot.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.mahia.ribot.R
import com.mahia.ribot.databinding.ActivityHomeBinding
import com.mahia.ribot.model.DocterModel
import com.mahia.ribot.model.RecordTreatmentModel
import com.mahia.ribot.view.auth.SignInActivity
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile_patient,
                R.id.navigation_record_treatment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        val conditions = CustomModelDownloadConditions.Builder()
//            .requireWifi()
//            .build()
//        FirebaseModelDownloader.getInstance()
//            .getModel("model.tflite", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
//            conditions)
//            .addOnSuccessListener { model: CustomModel? ->
//
//                val modelFile = model?.file
//                if (modelFile != null) {
//                    val interpreter = Interpreter(modelFile)
//                    Log.d(this.toString(),"ini hasil ml anda $interpreter")
//
//                    val bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE
//                    val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
//                    interpreter?.run(input, modelOutput)
//                    modelOutput.rewind()
//                    val probabilities = modelOutput.asFloatBuffer()
//                    try {
//                        val reader = BufferedReader(
//                            InputStreamReader(assets.open("custom_labels.txt")))
//                        for (i in probabilities.capacity()) {
//                            val label: String = reader.readLine()
//                            val probability = probabilities.get(i)
//                            println("$label: $probability")
//                        }
//                    } catch (e: IOException) {
//                        // File not found?
//                    }
//                }
//            }


        val remoteModel = FirebaseCustomRemoteModel.Builder("model.tflite").build()
        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
            .addOnCompleteListener {
                // Success.
                Toast.makeText(this@HomeActivity, "anda berhasil download $it", Toast.LENGTH_SHORT).show()
                Log.d(this.toString(),"anda berhasil download $it")
            }

        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
            .addOnSuccessListener { isDownloaded ->
                val options =
//                    if (isDownloaded) {
                        FirebaseModelInterpreterOptions.Builder(remoteModel).build()
//                    } else {
//                        FirebaseModelInterpreterOptions.Builder(localModel).build()
//                    }
                val interpreter = FirebaseModelInterpreter.getInstance(options)
            }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}