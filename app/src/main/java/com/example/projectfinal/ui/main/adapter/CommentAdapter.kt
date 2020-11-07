package com.example.projectfinal.ui.main.adapter

import com.bumptech.glide.Glide
import com.example.projectfinal.model.post.CommentPost
import kotlinx.android.synthetic.main.item_comment.view.*


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_comment.view.txtDelete
import kotlinx.android.synthetic.main.item_comment.view.txtEdit
import kotlinx.android.synthetic.main.item_group.view.*

class CommentAdapter(private val onClickItem: ClickItem) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var list = ArrayList<commentData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: commentData) {
            itemView.tv_username.text = list.createdBy
            itemView.swipe.close(true)
            if(list.status=="active"){
                itemView.active.setImageResource(R.drawable.active_on)
            }

            itemView.tv_count_like.text = list.countLike.toString() ?: "0"
            itemView.tv_des_comment.text = list.description
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val role = pref.getString(ROLE, "")
            val username= pref.getString(USERNAME,"")
            when {
                role == MEMBER -> {
                    itemView.swipe.setLockDrag(true)
                }
                username==list.createdBy -> {
                    itemView.txtDelete_admin.gone()
                }
                else -> {
                    itemView.txtDelete.gone()
                    itemView.txtEdit.gone()

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_comment,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.bind(element)
        val id = element.id
        val des = element.description
        holder.itemView.txtEdit.setOnClickListener {
            onClickItem.onClickItem(id, 1,"",des)
        }
        holder.itemView.txtDelete.setOnClickListener {
            onClickItem.onClickItem(id, 2,"",des)
        }
        holder.itemView.txtDelete_admin.setOnClickListener {
            onClickItem.onClickItem(id, 2,"",des)
        }
        holder.itemView.relative.setOnClickListener {
            onClickItem.onClickItem(id, 4,"",des)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: MutableList<commentData>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}
