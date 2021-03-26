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
import com.fyp_miscebook.model.TopVenueResponse

class TopVenueAdapter(
    private val context: Context,
    private var list: MutableList<TopVenueResponse>
) :
    RecyclerView.Adapter<TopVenueAdapter.MyViewHolder>() {

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
        val topvenue = list.get(position)

        holder.name?.text = topvenue.name
        holder.address?.text = topvenue.address
        holder.email?.text = topvenue.email
        holder.imagepic?.let { Glide.with(context).load(topvenue.image).into(it) }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FutsalActivity::class.java)
            intent.putExtra("name",topvenue.name)
            intent.putExtra("address",topvenue.address)
            intent.putExtra("email",topvenue.email)
            intent.putExtra("image",topvenue.image)
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