package edu.co.icesi.claseauth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun add(user: User) {
        users.add(user)
        notifyItemInserted(users.lastIndex)
    }

}

