package com.example.projectfinal.ui.feed.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(private val onClickItem: ClickItem) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    private var list = ArrayList<feedData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(list: feedData) {
            val pref = itemView.context.getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val userId= pref.getString(USERNAME_ID,"")
            itemView.tv_user.text = list.createdBy
            itemView.tv_des.text = list.description
            itemView.tv_heart_count.text = list.countLike.toString()
            itemView.tv_comment_count.text= list.countCommentFeed.toString()
            for(item in list.flags){
                if(item==userId){
                    itemView.heart.setImageResource(R.drawable.heart)
                }
            }
            when (list.attachments.size) {
                0->{
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
                    Glide.with(itemView)
                        .load(list.attachments[0])
                        .into(itemView.image_layout1)
                }
                2 -> {
                    itemView.image_layout1.invisible()
                    itemView.image1_layout3.invisible()
                    itemView.image2_layout3.invisible()
                    itemView.tv_count_image.invisible()
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
                    itemView.tv_count_image.text="+ ${list.attachments.size-3}"
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
//        val id = element.id
//        val des = element.description
//        holder.itemView.txtEdit.setOnClickListener {
//            onClickItem.onClickItem(id, 1,"",des)
//        }
//        holder.itemView.txtDelete.setOnClickListener {
//            onClickItem.onClickItem(id, 2,"",des)
//        }
//        holder.itemView.txtDelete_admin.setOnClickListener {
//            onClickItem.onClickItem(id, 2,"",des)
//        }
//        holder.itemView.relative.setOnClickListener {
//            onClickItem.onClickItem(id, 4,"",des)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: MutableList<feedData>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}
