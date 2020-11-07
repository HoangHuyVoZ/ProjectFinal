package com.example.projectfinal.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.projectfinal.model.Group.Groupdata
import com.example.projectfinal.model.Topic.TopicData

class UserDiffUtil(
    private val mOldTopic: List<TopicData>,
    private val mNewTopic: List<TopicData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldTopic.size
    }

    override fun getNewListSize(): Int {
        return mNewTopic.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldTopic[oldItemPosition].id === mNewTopic[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUserName = mOldTopic[oldItemPosition].name
        val newUserName = mNewTopic[newItemPosition].name
        return oldUserName == newUserName
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
