package edu.co.icesi.claseauth.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import edu.co.icesi.claseauth.ChatActivity
import edu.co.icesi.claseauth.R
import edu.co.icesi.claseauth.model.User
import edu.co.icesi.claseauth.viewholders.UserVH

class UserAdapter: RecyclerView.Adapter<UserVH>() {

    private val users = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.userrow, parent, false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.nameUserTV.text = users[position].username
        holder.emailUserTV.text = users[position].email
        holder.userAction.setOnClickListener{
            goToChat(it.context, position)
        }
    }

    fun goToChat(context:Context, position: Int){
        val intent = Intent(context, ChatActivity::class.java).apply {
            putExtra("friend", users[position].id)
        }
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun add(user: User) {
        users.add(user)
        notifyItemInserted(users.lastIndex)
    }

}

