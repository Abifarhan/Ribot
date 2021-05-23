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
import com.mahia.ribot.R
import com.mahia.ribot.databinding.ActivityHomeBinding
import com.mahia.ribot.model.DocterModel
import com.mahia.ribot.model.RecordTreatmentModel
import com.mahia.ribot.view.auth.SignInActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser.uid


//        Log.d(this.toString(),"your uid is $user")
////        if (user == null) {
////            startActivity(Intent(this,SignInActivity::class.java))
////        }
//
//        FirebaseFirestore.getInstance().collection("patient")
//            .document(user).collection("riwayatberobat")
//            .document("ZThZUNAXTkXutHamGD5K")
//            .get()
//            .addOnSuccessListener {
//                val doctor = it.get("doctor") as HashMap<*,*>
//                val nameDoctor = doctor["name"]
//                val specialty = doctor["speciality"]
//
//                val record = it.get("record") as HashMap<*,*>
//                val conclusion = record["conclusion"]
//                val date = record["date"]
//
//                val treatment = record.get("treatment") as HashMap<*,*>
////                val drugs = treatment
//                Log.d(this.toString(),"ini nama doctor : $nameDoctor\n ini conclusion: $conclusion\n ini treatment yang diberikan: $treatment")
//            }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile_patient,
                R.id.navigation_treatment_present,
                R.id.navigation_record_treatment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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