package com.example.projectfinal.ui.feed.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import kotlinx.android.synthetic.main.item_image.view.*

class FeedCreateAdapter : RecyclerView.Adapter<FeedCreateAdapter.ViewHolder>() {
    private var list = ArrayList<Uri>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(list: Uri) {
            Glide.with(itemView)
                .load(list)
                .into(itemView.image_feed)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_image,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.bind(element)
//        onClick.onClickItem("",position,name,"")
        holder.itemView.btn_del.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: ArrayList<Uri>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(items: ArrayList<Uri>) {
        list.removeAll(items)
        notifyDataSetChanged()
    }


    fun getList(): ArrayList<Uri> {
        val checkList = arrayListOf<Uri>()
        for (item in list) {
            checkList.add(item)

        }
        return checkList
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}