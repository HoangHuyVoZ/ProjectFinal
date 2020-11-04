package com.example.projectfinal.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(private val onClickItem: ClickItem) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private var list = ArrayList<PostData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: PostData) {
            itemView.swipeLayout_post.close(true)
            itemView.item_profile.text = "Created by: ${list.createdBy}"
            itemView.tv_countComment.text = list.countCommentPost.toString() ?: "0"
            itemView.tv_countLike.text = list.countLike.toString() ?: "0"
            itemView.item_post.text = list.title
            itemView.item_des_post.text = "Description: ${list.description}"
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val role = pref.getString(ROLE, "")
            val username= pref.getString(USERNAME,"")
            when {
                role == ADMIN -> {
                    itemView.txtDelete_post.gone()
                    itemView.txtEdit_post.gone()
                }
                username==list.createdBy -> {
                    itemView.txtDelete_post_admin.gone()
                }
                else -> {
                    itemView.swipeLayout_post.setLockDrag(true)

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_post,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.bind(element)
        val id = element.id
        val title = element.title
        val des = element.description
        holder.itemView.txtEdit_post.setOnClickListener {
            onClickItem.onClickItem(id, 1,title,des)
        }
        holder.itemView.txtDelete_post.setOnClickListener {
            onClickItem.onClickItem(id, 2,title,des)
        }
        holder.itemView.txtDelete_post_admin.setOnClickListener {
            onClickItem.onClickItem(id, 2,title,des)
        }
        holder.itemView.post_rela.setOnClickListener {
            onClickItem.onClickItem(id, 4,title,des)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: MutableList<PostData>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}
