package com.example.projectfinal.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.projectfinal.model.group.Groupdata
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.model.post.PostData

class GroupDiffUtilCallback(private val oldList: List<Groupdata>, private val newList: List<Groupdata>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}
class TopicDiffUtilCallback(private val oldList: List<TopicData>, private val newList: List<TopicData>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}
class PostDiffUtilCallback(private val oldList: List<PostData>, private val newList: List<PostData>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}
class CommentDiffUtilCallback(private val oldList: List<commentData>, private val newList: List<commentData>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}