package edu.co.icesi.claseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding
import edu.co.icesi.claseauth.model.User

class ProfileActivity : AppCompatActivity() {

    private val binding:ActivityProfileBinding by lazy{
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        //Saber si estoy loggeado
        if(Firebase.auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        //Pedir mi documento
        val uid = Firebase.auth.currentUser?.uid
        Firebase.firestore.collection("users")
            .document(uid!!).get().addOnSuccessListener {
                val user = it.toObject(User::class.java)
                binding.profileWelcomeTV.text = "Bienvenido, ${user?.username}"
        }

        binding.profileSignoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }



    }
}