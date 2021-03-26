package com.fyp_miscebook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fyp_miscebook.R
import com.fyp_miscebook.activities.FutsalActivity
import com.fyp_miscebook.model.UserResponse

class UsersAdapter(private val context: Context, private var list: MutableList<UserResponse>) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = list.get(position)
        holder.name?.text = user.firstName + " " + user.middleName + " " + user.lastName
        holder.email?.text = user.email
        holder.imagepic?.let { Glide.with(context).load(user.image).into(it) }
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var imagepic: ImageView? = null
        var name: TextView? = null
        var email: TextView? = null

        init {
            imagepic = view.findViewById(R.id.futsalpic)
            name = view.findViewById(R.id.futsalname_rv)
            email = view.findViewById(R.id.futsalemail_rv)
        }

    }

}