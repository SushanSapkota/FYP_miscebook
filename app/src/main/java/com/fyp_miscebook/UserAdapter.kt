package com.fyp_miscebook


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = list.get(position)
        holder.name?.text = user.firstName + " " + user.middleName + " " + user.lastName

    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView? = null
        var info1: TextView? = null
        var info2: TextView? = null
        var address: TextView? = null

        init {
            name = view.findViewById(R.id.txt_user_name)
            info1 = view.findViewById(R.id.txt_user_info1)
            info2 = view.findViewById(R.id.txt_user_info2)
            address = view.findViewById(R.id.txt_user_address)
        }

    }

}