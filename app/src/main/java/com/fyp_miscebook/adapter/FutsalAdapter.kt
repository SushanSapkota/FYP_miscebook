package com.fyp_miscebook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fyp_miscebook.R
import com.fyp_miscebook.activities.FutsalActivity
import com.fyp_miscebook.model.FutsalResponse

class FutsalAdapter(private val context: Context, private var list: MutableList<FutsalResponse>) :
    RecyclerView.Adapter<FutsalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.futsal_item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val futsal = list.get(position)

        holder.name?.text = futsal.name
        holder.address?.text = futsal.address
        holder.email?.text = futsal.email
        holder.imagepic?.let { Glide.with(context).load(futsal.image).into(it) }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FutsalActivity::class.java)
            intent.putExtra("name",futsal.name)
            intent.putExtra("address",futsal.address)
            intent.putExtra("email",futsal.email)
            intent.putExtra("image",futsal.image)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var imagepic: ImageView? = null
        var name: TextView? = null
        var address: TextView? = null
        var email: TextView? = null

        init {
            imagepic = view.findViewById(R.id.futsalpic)
            name = view.findViewById(R.id.futsalname_rv)
            address = view.findViewById(R.id.futsaladdress_rv)
            email = view.findViewById(R.id.futsalemail_rv)
        }

    }

}