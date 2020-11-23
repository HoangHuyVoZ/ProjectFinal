package com.example.projectfinal.model.feed


import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@SuppressLint("ParcelCreator")
data class FeedData(
    val attachments: List<String>,
    val avatar: String,
    val countCommentFeed: Int,
    val countLike: Int,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val flags: List<String>,
    @SerializedName("_id")
    val id: String,
    val userId: String
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {


    }

}
