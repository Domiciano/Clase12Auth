package edu.co.icesi.claseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.co.icesi.claseauth.databinding.ActivityChatBinding
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding

class ChatActivity : AppCompatActivity() {

    private val binding: ActivityChatBinding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}