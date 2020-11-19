package com.example.projectfinal.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.R
import com.example.projectfinal.model.group.Groupdata
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.coroutines.selects.select

class GroupAdapter(
    private var onClick: (select: Int, position: Int, item: Groupdata) -> Unit
) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var list = ArrayList<Groupdata>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: Groupdata) {
            itemView.item_create_group.text = "Created by: ${list.createdBy}"
            itemView.item_group.text = list.name
            itemView.swipelayout.close(true)
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
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
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_group,
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
        holder.itemView.txtEdit.setOnClickListener {
//            onClickItem.onClickItem(id, 3, name, "", position)
            onClick.invoke(1, position, element)
        }
        holder.itemView.txtDelete.setOnClickListener {
//            onClickItem.onRemoveClick(position, id)
            onClick.invoke(2, position, element)
        }
        holder.itemView.txtEdit_mod.setOnClickListener {
//            onClickItem.onClickItem(id, 3, name, "", position)
            onClick.invoke(1, position, element)
        }
        holder.itemView.item_group_re.setOnClickListener {
//            onClickItem.onClickItem(id, 4, name, "", position)
            onClick.invoke(3, position, element)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun remove(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun addList(items: MutableList<Groupdata>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<Groupdata> {
        return list
    }

    fun addItem(item: Groupdata) {
        list.add(0, item)
        notifyItemInserted(0)
    }

    fun updateItem(item: Groupdata, position: Int) {
        list[position] = item
        notifyItemChanged(position)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

}