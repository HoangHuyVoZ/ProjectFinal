package com.example.projectfinal.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicAdapter(private val onClickItem: ClickItem) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {
    private var list = ArrayList<TopicData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: TopicData) {
            itemView.swipeLayout_topic.close(true)
            itemView.item_topic.text = list.name
            itemView.item_des_top.text = "Description: ${list.description}"
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val role = pref.getString(ROLE, "")
            val username= pref.getString(USERNAME,"")
            when {
                username==list.createdBy && role == ADMIN->{
                    itemView.txtDelete_topic_admin.gone()
                }
                username==list.createdBy -> {
                    itemView.txtDelete_topic_admin.gone()
                }
                role ==ADMIN -> {
                    itemView.txtDelete_topic.gone()
                    itemView.txtEdit_topic.gone()
                }
                else -> {
                    itemView.swipeLayout_topic.setLockDrag(true)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_topic,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.bind(element)
        val id = element.id
        val name = element.name
        val des = element.description
        holder.itemView.txtEdit_topic.setOnClickListener {
            onClickItem.onClickItem(id, 2,name,des,position)
        }
        holder.itemView.txtDelete_topic.setOnClickListener {
            onClickItem.onRemoveClick(position, id)
        }
        holder.itemView.txtDelete_topic_admin.setOnClickListener {
            onClickItem.onClickItem(id, 2,name,des,position)
        }
        holder.itemView.topic_rela.setOnClickListener {
            onClickItem.onClickItem(id, 4,name,des,position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun getList(): ArrayList<TopicData>{
        return list
    }
    fun addList(items: MutableList<TopicData>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
    fun addItem(item: TopicData) {
        list.add(0, item)
        notifyItemInserted(0)
    }

    fun updateItem(item: TopicData, position: Int) {
        list[position] = item
        notifyItemChanged(position)
    }
    fun remove(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

}
