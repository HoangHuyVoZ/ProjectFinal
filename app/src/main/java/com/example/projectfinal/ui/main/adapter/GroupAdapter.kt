package com.example.projectfinal.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.model.Group.Groupdata
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_group.view.*

class GroupAdapter(private val onClickItem: ClickItem) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    var list = ArrayList<Groupdata>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: Groupdata) {
            itemView.item_group.text = list.name
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE)
            when (pref.getString(ROLE, "")) {
                MEMBER -> {
                    itemView.swipelayout.setLockDrag(true)
                }
                MOD -> {
                    itemView.txtDelete.gone()
                    itemView.txtEdit.gone()
                }
                else -> {
                    itemView.txtEdit_mod.gone()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_group,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list?.get(position)
        holder.bind(element as Groupdata)
        var id =element.id
        var name= element.name
        holder.itemView.txtEdit.setOnClickListener {
            onClickItem.onClickItem(id,1,name,"")
        }
        holder.itemView.txtDelete.setOnClickListener {
            onClickItem.onClickItem(id,2,name,"")
        }
        holder.itemView.txtEdit_mod.setOnClickListener {
            onClickItem.onClickItem(id,3,name,"")
        }
        holder.itemView.item_group_re.setOnClickListener {
            onClickItem.onClickItem(id,4,name,"")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: MutableList<Groupdata>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}