package edu.co.icesi.claseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.adapters.UserAdapter
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding
import edu.co.icesi.claseauth.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ProfileActivity : AppCompatActivity() {

    private val binding:ActivityProfileBinding by lazy{
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        UserAdapter()
    }
    private lateinit var docs:List<DocumentSnapshot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.userList.adapter = adapter
        binding.userList.layoutManager = LinearLayoutManager(this)
        binding.userList.setHasFixedSize(true)

        binding.imageProfile.setOnClickListener {
            val intent = Intent(this, ProfileDetailActivity::class.java)
            startActivity(intent)
        }


        //Saber si estoy loggeado
        if(Firebase.auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }



        //Pedir mis propios datos
        /*
        Firebase.firestore
            .collection("users").document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)!!
                binding.profileWelcomeTV.text = "Bienvenido, ${user.username}"
            }


         */

        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid).get().await().toObject(User::class.java)!!

            withContext(Dispatchers.Main){
                binding.profileWelcomeTV.text = "${user.username}"
            }

            val users = Firebase.firestore.collection("users").get().await().documents
            withContext(Dispatchers.Main){
                for(user in users){
                    val obj = user.toObject(User::class.java)!!
                    adapter.add(obj)
                }
            }


        }










    }


}