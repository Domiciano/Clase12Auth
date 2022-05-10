package edu.co.icesi.claseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.co.icesi.claseauth.databinding.ActivityChatBinding
import edu.co.icesi.claseauth.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ChatActivity : AppCompatActivity() {

    private val binding: ActivityChatBinding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }

    private val friendID:String by lazy {
        intent.extras?.getString("friend", "NONE")!!
    }

    private var chatID:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.e(">>>", friendID)
        Log.e(">>>", Firebase.auth.currentUser!!.uid)



        //Escribir mensajes
        binding.sendBtn.setOnClickListener {
            val msg = Message(
                UUID.randomUUID().toString(),
                Firebase.auth.currentUser!!.uid,
                Date().time,
                binding.messageET.text.toString()
            )

            Firebase.firestore
                .collection("chats")
                .document(chatID)
                .collection("messages")
                .document(msg.id)
                .set(msg)

            binding.messageET.setText("")


        }



        lifecycleScope.launch(Dispatchers.IO){

            //Consultar si ya hay chat
            val chatResults = Firebase.firestore
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid)
                .collection("chats")
                .whereArrayContains("members", friendID)
                .get().await().documents

            if(chatResults.size == 0){
                createChat()


            }
            else if(chatResults.size == 1){
                val rel = chatResults[0].toObject(ChatRelationship::class.java)!!

                chatID = rel.chatID

                Log.e(">>>","${rel.chatID}")

                val docs = Firebase.firestore.collection("chats")
                    .document(rel.chatID)
                    .collection("messages")
                    .orderBy("date").addSnapshotListener(this@ChatActivity){ result, error->
                        binding.messagesTV.text = ""
                        for(doc in result!!.documents){
                            val message = doc.toObject(Message::class.java)!!
                            binding.messagesTV.append("${message.text}\n\n")
                        }
                    }



            }
            Log.e(">>>","Salas encontradas: ${chatResults.size}")
        }
    }

    suspend fun createChat(){
        //Escribir el objeto de relación en los dos usuarios
        val chatRel = ChatRelationship(UUID.randomUUID().toString(), arrayListOf(
            Firebase.auth.currentUser!!.uid,
            friendID
        ))
        Firebase.firestore.batch()
            .set(Firebase.firestore
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid)
                .collection("chats")
                .document(chatRel.chatID), chatRel)

            .set(Firebase.firestore
                .collection("users")
                .document(friendID)
                .collection("chats")
                .document(chatRel.chatID), chatRel)

            .set(Firebase.firestore
                .collection("chats")
                .document(chatRel.chatID),
                chatRel
            )

            .commit().await()
    }


    data class ChatRelationship(
        var chatID:String = "",
        var members: List<String> = arrayListOf()
    )

    data class Message(
        var id:String = "",
        var author:String = "",
        var date:Long = 0,
        var text:String = ""
    )

}