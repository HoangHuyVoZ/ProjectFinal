package com.example.projectfinal.api

import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.Signup.Signup
import com.example.projectfinal.model.Topic.Topic
import com.example.projectfinal.model.User.User
import com.example.projectfinal.model.login.Login
import retrofit2.Call
import retrofit2.http.*
import java.nio.file.attribute.UserDefinedFileAttributeView

interface API {
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
  //  /v1/api/group/:group_id/topic
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
    // user
    @GET("info")
    fun getUserInfo(
        @Header("Authorization")authorization: String,
        ): Call<User>
}