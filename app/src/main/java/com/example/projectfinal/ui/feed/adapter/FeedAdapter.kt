package com.example.projectfinal.ui.feed.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(private var onClick: (select: Int, position: Int, item: feedData) -> Unit) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    private var list = ArrayList<feedData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: feedData) {
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val userId = pref.getString(USERNAME_ID, "")
            itemView.tv_user.text = list.createdBy
            itemView.tv_des.text = list.description
            itemView.tv_heart_count.text = list.countLike.toString()
            itemView.tv_comment_count.text = list.countCommentFeed.toString()
            var checkLike = false
            for (item in list.flags) {
                if (item == userId) {
                    itemView.heart.setImageResource(R.drawable.heart)
                } else {
                    itemView.heart.setImageResource(R.drawable.heart_empty)
                }
            }

            itemView.heart.setOnClickListener {
                if (checkLike) {
                    checkLike = false
                    itemView.heart.setImageResource(R.drawable.heart_empty)
                } else {
                    checkLike = true
                    itemView.heart.setImageResource(R.drawable.heart)
                }
            }
            when (list.attachments.size) {
                0 -> {
                    itemView.image1_layout3.invisible()
                    itemView.image2_layout3.invisible()
                    itemView.image_layout2.invisible()
                    itemView.image2_layout2.invisible()
                    itemView.tv_count_image.invisible()
                    itemView.image_layout1.invisible()
                    itemView.cardView.invisible()
                }
                1 -> {
                    itemView.image1_layout3.invisible()
                    itemView.image2_layout3.invisible()
                    itemView.image_layout2.invisible()
                    itemView.image2_layout2.invisible()
                    itemView.tv_count_image.invisible()
                    itemView.image_layout1.visible()
                    itemView.cardView.visible()
                    Glide.with(itemView)
                        .load(list.attachments[0])
                        .into(itemView.image_layout1)
                }
                2 -> {
                    itemView.image_layout1.invisible()
                    itemView.image1_layout3.invisible()
                    itemView.image2_layout3.invisible()
                    itemView.tv_count_image.invisible()
                    itemView.image_layout2.visible()
                    itemView.image2_layout2.visible()
                    itemView.cardView.visible()

                    Glide.with(itemView)
                        .load(list.attachments[0])
                        .into(itemView.image_layout2)
                    Glide.with(itemView)
                        .load(list.attachments[1])
                        .into(itemView.image2_layout2)
                }
                3 -> {
                    itemView.image_layout1.invisible()
                    itemView.tv_count_image.invisible()
                    itemView.image2_layout2.invisible()
                    itemView.image_layout2.visible()
                    itemView.cardView.visible()
                    itemView.image1_layout3.visible()
                    itemView.image2_layout3.visible()

                    Glide.with(itemView)
                        .load(list.attachments[0])
                        .into(itemView.image_layout2)
                    Glide.with(itemView)
                        .load(list.attachments[1])
                        .into(itemView.image1_layout3)
                    Glide.with(itemView)
                        .load(list.attachments[2])
                        .into(itemView.image2_layout3)
                }
                else -> {
                    itemView.image_layout1.invisible()
                    itemView.image2_layout2.invisible()
                    itemView.image_layout2.visible()
                    itemView.cardView.visible()
                    itemView.image1_layout3.visible()
                    itemView.image2_layout3.visible()
                    itemView.tv_count_image.visible()
                    itemView.tv_count_image.text = "+ ${list.attachments.size - 3}"
                    Glide.with(itemView)
                        .load(list.attachments[0])
                        .into(itemView.image_layout2)
                    Glide.with(itemView)
                        .load(list.attachments[1])
                        .into(itemView.image1_layout3)
                    Glide.with(itemView)
                        .load(list.attachments[2])
                        .into(itemView.image2_layout3)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_feed,
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
        holder.itemView.btn_more.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(holder.itemView.context, holder.itemView.btn_more)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.Delete -> onClick.invoke(1, position, element)

                    R.id.CreateFeedFragment -> onClick.invoke(2, position, element)
                }
                true
            })
            popupMenu.show()
        }
        holder.itemView.feed.setOnClickListener {
            onClick.invoke(3, position, element)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<feedData>) {
        list.clear()
        list.addAll(items)
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
