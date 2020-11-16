package com.example.projectfinal.api

import com.example.projectfinal.model.group.CreateGroup
import com.example.projectfinal.model.group.Group
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.signup.Signup
import com.example.projectfinal.model.topic.*
import com.example.projectfinal.model.comment.CreateComment
import com.example.projectfinal.model.user.User
import com.example.projectfinal.model.comment.comment
import com.example.projectfinal.model.feed.feed
import com.example.projectfinal.model.feed.feedComment
import com.example.projectfinal.model.login.Login
import com.example.projectfinal.model.post.CreatePost
import retrofit2.Call
import retrofit2.http.*

interface API {
    //login
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): Call<Login>

    @FormUrlEncoded
    @POST("signup")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("display_name") display_name: String,
        @Field("gender") gender: String
    ): Call<Signup>
    //group
    @GET("group")
    fun getGroups(
        @Header("Authorization") authorization: String
    ): Call<Group>
    @GET("group/{group_id}")
    fun getGroupId(
        @Header("Authorization") authorization: String,
        @Path("group_id")group_id :String?,
    ): Call<Group>
    @FormUrlEncoded
    @POST("group")
    fun getCreateGroup(
        @Header("Authorization") authorization: String,
        @Field("name")name: String
    ): Call<CreateGroup>

    @FormUrlEncoded
    @PATCH("group/{group_id}")
    fun getUpdateGroup(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Field("name")name: String
    ):Call<CreateGroup>

    @DELETE("group/{group_id}")
    fun getDeleteGroup(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
    ):Call<CreateGroup>
//topic
    @GET("group/{group_id}/topic")
    fun getTopic(
      @Header("Authorization")authorization: String,
      @Path("group_id")group_id :String?,
      ): Call<Topic>
    @GET("group/{group_id}/topic/{topic_id}")
    fun getTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
    ): Call<Topic>
    @FormUrlEncoded
    @POST("group/{group_id}/topic")
    fun getCreateTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Field("name")name: String,
        @Field("description")description: String,
    ): Call<CreateTopic>

    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}")
    fun getUpdateTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Field("name")name: String,
        @Field("description")description: String
    ):Call<updateTopic>

    @DELETE("group/{group_id}/topic/{topic_id}")
    fun getDeleteTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id :String?,
    ):Call<CreateTopic>
    //
    // user
    @GET("info")
    fun getUserInfo(
        @Header("Authorization")authorization: String,
        ): Call<User>
    @FormUrlEncoded
    @PATCH("info")
    fun getUserChangePass(
        @Header("Authorization")authorization: String,
        @Field("oldpassword")old: String,
        @Field("newpassword")new: String,
        @Field("renewpassword")renew: String,
    ): Call<User>
    //post
    @GET("group/{group_id}/topic/{topic_id}/post")
    fun getPost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
    ): Call<Post>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post")
    fun getCreatePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Field("title")title: String,
        @Field("description")description: String
    ): Call<CreatePost>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getUpdatePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Field("title")name: String,
        @Field("description")description: String
    ):Call<CreatePost>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getDeletePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ):Call<CreatePost>
    //comment
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getPostId(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ): Call<Post>
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ): Call<comment>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getCreateComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Field("description")description: String
    ): Call<CreateComment>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getUpdateComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Path("comment_id")comment_id: String?,
        @Field("description")description: String
    ): Call<CreateComment>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getDeleteComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Path("comment_id")comment_id: String?): Call<CreateComment>
    @GET("feed")
    fun getFeed(
        @Header("Authorization")authorization: String,
    ): Call<feed>
    @GET("feed/{feed_id}")
    fun getFeedID(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
        ): Call<feed>
    @DELETE("feed/{feed_id}")
    fun getDeleteFeedID(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
    ): Call<feed>
    @FormUrlEncoded
    @PATCH("feed/{feed_id}/addlike")
    fun getAddLikeFeed(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
    ): Call<feed>
    @PATCH("feed/{feed_id}/minusllike")
    fun getMinusLikeFeed(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
    ): Call<feed>
    @GET("feed/{feed_id}/comment")
    fun getFeedComment(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
    ): Call<feedComment>
    @FormUrlEncoded
    @POST("feed/{feed_id}/comment")
    fun getCreateFeedComment(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
        @Field("description")description: String
    ): Call<feedComment>
    @FormUrlEncoded
    @PATCH("feed/{feed_id}/comment/{comment_id}")
    fun getUpdateFeedComment(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
        @Path("comment_id")comment_id: String?,
        @Field("description")description: String,
    ): Call<feedComment>
    @DELETE("feed/{feed_id}/comment/{comment_id}")
    fun getDeleteFeedComment(
        @Header("Authorization")authorization: String,
        @Path("feed_id")feed_id: String?,
        @Path("comment_id")comment_id: String?): Call<feedComment>
    @FormUrlEncoded
    @POST("feed")
    fun getCreateFeed(
        @Header("Authorization")authorization: String,
        @Field("description")description: String,
        @Field("attachments")attachments: ArrayList<String>,
    ): Call<feed>
}