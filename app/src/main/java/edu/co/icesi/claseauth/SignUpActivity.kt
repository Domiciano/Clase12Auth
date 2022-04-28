package edu.co.icesi.claseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.databinding.ActivitySignUpBinding
import edu.co.icesi.claseauth.model.User

class SignUpActivity : AppCompatActivity() {

    private val binding:ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val email = binding.signupEmailET.text.toString()
            val pass = binding.signupPassET.text.toString()
            Firebase.auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                Toast.makeText(this, "Todo bien", Toast.LENGTH_LONG).show()
                registerUserData()
            }.addOnFailureListener {
                Toast.makeText(this, "Algo falló: ${it.message}", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun registerUserData(){
        val uid = Firebase.auth.currentUser?.uid
        uid?.let {
            val user = User(
                it,
                binding.signupUsernameET.text.toString(),
                binding.signupEmailET.text.toString(),
                binding.signupDescET.text.toString()
            )
            Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }

    }

}