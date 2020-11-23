package com.example.projectfinal.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicAdapter(private var onClick: (select: Int, position: Int, item: TopicData) -> Unit) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {
    private var list = ArrayList<TopicData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(list: TopicData) {
            itemView.swipeLayout_topic.close(true)
            itemView.item_topic.text = list.name
            itemView.item_des_top.text = "Description: ${list.description}"
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val role = pref.getString(ROLE, "")
            val username = pref.getString(USERNAME, "")
            when {
                username == list.createdBy && role == ADMIN -> {
                    itemView.txtDelete_topic_admin.gone()
                }
                username == list.createdBy -> {
                    itemView.txtDelete_topic_admin.gone()
                }
                role == ADMIN -> {
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

        holder.itemView.txtEdit_topic.setOnClickListener {
            onClick.invoke(1, position, element)
        }
        holder.itemView.txtDelete_topic.setOnClickListener {
            onClick.invoke(2, position, element)
        }
        holder.itemView.txtDelete_topic_admin.setOnClickListener {
            onClick.invoke(2, position, element)
        }
        holder.itemView.topic_rela.setOnClickListener {
            onClick.invoke(3, position, element)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getList(): ArrayList<TopicData> {
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
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

}
