package com.fyp_miscebook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fyp_miscebook.dataModel.FutsalDataModel
import com.fyp_miscebook.R
import kotlinx.android.synthetic.main.recyclerview_list_item.view.*


class RecyclerViewAdapter(
    private var context: Context,
    private val list: ArrayList<FutsalDataModel>
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(context)
            .inflate(R.layout.recyclerview_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.name.text = list.get(position).name
        holder.itemView.address.text = list.get(position).address
        holder.itemView.profile.setImageDrawable(list.get(position).image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}