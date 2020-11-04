package com.example.projectfinal.api

import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.Signup.Signup
import com.example.projectfinal.model.Topic.Topic
import com.example.projectfinal.model.user.User
import com.example.projectfinal.model.comment.comment
import com.example.projectfinal.model.feed.feed
import com.example.projectfinal.model.login.Login
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

    @FormUrlEncoded
    @POST("group")
    fun getCreateGroup(
        @Header("Authorization") authorization: String,
        @Field("name")name: String
    ): Call<Group>

    @FormUrlEncoded
    @PATCH("group/{group_id}")
    fun getUpdateGroup(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Field("name")name: String
    ):Call<Group>

    @DELETE("group/{group_id}")
    fun getDeleteGroup(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
    ):Call<Group>
//topic
    @GET("group/{group_id}/topic")
    fun getTopic(
      @Header("Authorization")authorization: String,
      @Path("group_id")group_id :String,
      ): Call<Topic>

    @FormUrlEncoded
    @POST("group/{group_id}/topic")
    fun getCreateTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Field("name")name: String,
        @Field("description")description: String,
    ): Call<Topic>

    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}")
    fun getUpdateTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Field("name")name: String,
        @Field("description")description: String
    ):Call<Topic>

    @DELETE("group/{group_id}/topic/{topic_id}")
    fun getDeleteTopic(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id :String,
    ):Call<Topic>
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
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
    ): Call<Post>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post")
    fun getCreatePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Field("title")title: String,
        @Field("description")description: String
    ): Call<Post>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getUpdatePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
        @Field("title")name: String,
        @Field("description")description: String
    ):Call<Post>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getDeletePost(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
    ):Call<Post>
    //comment
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getPostId(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
    ): Call<Post>
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
    ): Call<comment>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getCreateComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
        @Field("description")description: String
    ): Call<comment>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getUpdateComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
        @Path("comment_id")comment_id: String,
        @Field("description")description: String
    ): Call<comment>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getDeleteComment(
        @Header("Authorization")authorization: String,
        @Path("group_id")group_id :String,
        @Path("topic_id")topic_id: String,
        @Path("post_id")post_id: String,
        @Path("comment_id")comment_id: String): Call<comment>
    @GET("feed")
    fun getFeed(
        @Header("Authorization")authorization: String,
        ): Call<feed>
}