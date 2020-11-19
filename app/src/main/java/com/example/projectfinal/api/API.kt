package com.example.projectfinal.api

import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.topic.*
import com.example.projectfinal.model.comment.CreateCommentData
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.model.feed.createFeedData
import com.example.projectfinal.model.feed.createFeedDataComment
import com.example.projectfinal.model.feed.feedCommentData
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.model.group.CreateGroupData
import com.example.projectfinal.model.group.Groupdata
import com.example.projectfinal.model.group.UpdateGroupData
import com.example.projectfinal.model.login.LoginData
import com.example.projectfinal.model.post.CreatePostData
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.model.signup.SignUpData
import com.example.projectfinal.model.user.UserData
import retrofit2.Call
import retrofit2.http.*

interface API {
    //login
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): Call<BaseResponse<LoginData>>

    @FormUrlEncoded
    @POST("signup")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("display_name") display_name: String,
        @Field("gender") gender: String
    ): Call<BaseResponse<SignUpData>>
    //group
    @GET("group")
    fun getGroups(
    ): Call<BaseResponseList<Groupdata>>
    @GET("group/{group_id}")
    fun getGroupId(
        @Path("group_id")group_id :String?,
    ): Call<BaseResponseList<Groupdata>>
    @FormUrlEncoded
    @POST("group")
    fun getCreateGroup(
        @Field("name")name: String
    ): Call<BaseResponse<CreateGroupData>>

    @FormUrlEncoded
    @PATCH("group/{group_id}")
    fun getUpdateGroup(
        @Path("group_id")group_id :String?,
        @Field("name")name: String
    ):Call<BaseResponse<UpdateGroupData>>

    @DELETE("group/{group_id}")
    fun getDeleteGroup(
        @Path("group_id")group_id :String?,
    ):Call<BaseResponse<CreateGroupData>>
//topic
    @GET("group/{group_id}/topic")
    fun getTopic(
      @Path("group_id")group_id :String?,
      ): Call<BaseResponseList<TopicData>>
    @GET("group/{group_id}/topic/{topic_id}")
    fun getTopic(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
    ): Call<BaseResponseList<TopicData>>
    @FormUrlEncoded
    @POST("group/{group_id}/topic")
    fun getCreateTopic(
        @Path("group_id")group_id :String?,
        @Field("name")name: String,
        @Field("description")description: String,
    ): Call<BaseResponse<CreateTopicData>>

    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}")
    fun getUpdateTopic(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Field("name")name: String,
        @Field("description")description: String
    ):Call<BaseResponse<UpdateTopicData>>

    @DELETE("group/{group_id}/topic/{topic_id}")
    fun getDeleteTopic(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id :String?,
    ):Call<BaseResponse<CreateTopicData>>
    //
    // user
    @GET("info")
    fun getUserInfo(
        ): Call<BaseResponse<UserData>>
    @FormUrlEncoded
    @PATCH("info")
    fun getUserChangePass(
        @Field("oldpassword")old: String,
        @Field("newpassword")new: String,
        @Field("renewpassword")renew: String,
    ): Call<BaseResponse<UserData>>
    //post
    @GET("group/{group_id}/topic/{topic_id}/post")
    fun getPost(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
    ): Call<BaseResponseList<PostData>>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post")
    fun getCreatePost(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Field("title")title: String,
        @Field("description")description: String
    ): Call<BaseResponse<CreatePostData>>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getUpdatePost(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Field("title")name: String,
        @Field("description")description: String
    ):Call<BaseResponse<CreatePostData>>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getDeletePost(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ):Call<BaseResponse<CreatePostData>>
    //comment
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}")
    fun getPostId(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ): Call<BaseResponseList<PostData>>
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getComment(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
    ): Call<BaseResponseList<commentData>>
    @GET("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getCommentId(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Path("comment_id")comment_id: String?,
    ): Call<BaseResponseList<commentData>>
    @FormUrlEncoded
    @POST("group/{group_id}/topic/{topic_id}/post/{post_id}/comment")
    fun getCreateComment(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Field("description")description: String
    ): Call<BaseResponse<CreateCommentData>>
    @FormUrlEncoded
    @PATCH("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getUpdateComment(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Path("comment_id")comment_id: String?,
        @Field("description")description: String
    ): Call<BaseResponse<CreateCommentData>>
    @DELETE("group/{group_id}/topic/{topic_id}/post/{post_id}/comment/{comment_id}")
    fun getDeleteComment(
        @Path("group_id")group_id :String?,
        @Path("topic_id")topic_id: String?,
        @Path("post_id")post_id: String?,
        @Path("comment_id")comment_id: String?): Call<BaseResponse<CreateCommentData>>
    @GET("feed")
    fun getFeed(
    ): Call<BaseResponseList<feedData>>
    @GET("feed/{feed_id}")
    fun getFeedID(
        @Path("feed_id")feed_id: String?,
        ): Call<BaseResponseList<feedData>>
    @DELETE("feed/{feed_id}")
    fun getDeleteFeedID(
        @Path("feed_id")feed_id: String?,
    ): Call<BaseResponseList<feedData>>

    @GET("feed/{feed_id}/comment")
    fun getFeedComment(
        @Path("feed_id")feed_id: String?,
    ): Call<BaseResponseList<feedCommentData>>
    @GET("feed/{feed_id}/comment/{comment_id}")
    fun getFeedCommentID(
        @Path("feed_id")feed_id: String?,
        @Path("comment_id")comment_id: String?,
    ): Call<BaseResponseList<feedCommentData>>
    @FormUrlEncoded
    @POST("feed/{feed_id}/comment")
    fun getCreateFeedComment(
        @Path("feed_id")feed_id: String?,
        @Field("description")description: String
    ): Call<BaseResponse<createFeedDataComment>>
    @FormUrlEncoded
    @PATCH("feed/{feed_id}/comment/{comment_id}")
    fun getUpdateFeedComment(
        @Path("feed_id")feed_id: String?,
        @Path("comment_id")comment_id: String?,
        @Field("description")description: String,
    ): Call<BaseResponse<createFeedDataComment>>
    @DELETE("feed/{feed_id}/comment/{comment_id}")
    fun getDeleteFeedComment(
        @Path("feed_id")feed_id: String?,
        @Path("comment_id")comment_id: String?): Call<BaseResponse<createFeedDataComment>>
    @FormUrlEncoded
    @POST("feed")
    fun getCreateFeed(
        @Field("description")description: String,
        @Field("attachments")attachments: ArrayList<String>,
    ): Call<BaseResponse<createFeedData>>
    @FormUrlEncoded
    @PATCH("feed/{feed_id}")
    fun getUpdateFeed(
        @Path("feed_id")feed_id: String?,
        @Field("description")description: String,
        @Field("attachments")attachments: ArrayList<String>,
    ): Call<BaseResponse<createFeedData>>
}