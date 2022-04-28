package edu.co.icesi.claseauth.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.co.icesi.claseauth.databinding.UserrowBinding

class UserVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: UserrowBinding by lazy {
        UserrowBinding.bind(itemView)
    }

    val nameUserTV = binding.nameUserTV
    val emailUserTV = binding.emailUserTV
    val userAction = binding.userAction




}