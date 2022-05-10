package edu.co.icesi.claseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding
import edu.co.icesi.claseauth.databinding.ActivityProfileDetailBinding

class ProfileDetailActivity : AppCompatActivity() {

    private val binding: ActivityProfileDetailBinding by lazy{
        ActivityProfileDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)


        binding.signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}