package com.example.projectfinal.ui.main.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.comment.CommentData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(private var onClick: (select: Int, position: Int, item: CommentData) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var list = ArrayList<CommentData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: CommentData) {
            itemView.tv_username.text = list.createdBy
            itemView.swipe.close(true)


            itemView.tv_count_like.text = list.countLike.toString() ?: "0"
            itemView.tv_des_comment.text = list.description
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val role = pref.getString(ROLE, "")
            val username = pref.getString(USERNAME, "")
            when {
                username == list.createdBy -> {
                    itemView.txtDelete_admin.gone()
                }
                role == ADMIN -> {
                    itemView.txtDelete.gone()
                    itemView.txtEdit.gone()
                }

                else -> {
                    itemView.swipe.setLockDrag(true)

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
        holder.itemView.txtEdit.setOnClickListener {
                onClick.invoke(1,position,element)
        }
        holder.itemView.txtDelete.setOnClickListener {
            onClick.invoke(2,position,element)
        }
        holder.itemView.txtDelete_admin.setOnClickListener {
            onClick.invoke(2,position,element)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun remove(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addList(items: MutableList<CommentData>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<CommentData> {
        return list
    }

    fun addItem(item: CommentData) {
        list.add(0, item)
//        notifyItemInserted(0)
        notifyDataSetChanged()

    }

    fun updateItem(item: CommentData, position: Int) {
        list[position] = item
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}
