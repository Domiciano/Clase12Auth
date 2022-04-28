package edu.co.icesi.claseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.databinding.ActivityMainBinding
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginNoAccountTV.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginSignInBtn.setOnClickListener {
            Firebase.auth.signInWithEmailAndPassword(
                binding.loginEmailET.text.toString(),
                binding.loginPassET.text.toString()
            ).addOnSuccessListener {
                startActivity(Intent(this, ProfileActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}