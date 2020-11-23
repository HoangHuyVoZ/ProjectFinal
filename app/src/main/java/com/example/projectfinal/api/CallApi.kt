package com.example.projectfinal.api

import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.comment.CreateCommentData
import com.example.projectfinal.model.comment.CommentData
import com.example.projectfinal.model.feed.CreateFeedData
import com.example.projectfinal.model.feed.CreateFeedDataComment
import com.example.projectfinal.model.feed.FeedCommentData
import com.example.projectfinal.model.feed.FeedData
import com.example.projectfinal.model.group.CreateGroupData
import com.example.projectfinal.model.group.GroupData
import com.example.projectfinal.model.group.UpdateGroupData
import com.example.projectfinal.model.login.LoginData
import com.example.projectfinal.model.post.CreatePostData
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.model.signup.SignUpData
import com.example.projectfinal.model.topic.CreateTopicData
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.model.topic.UpdateTopicData
import com.example.projectfinal.model.user.UserData
import io.reactivex.Single

class CallApi {
    private val _apiRestFull: API by lazy {
        RetrofitInstants.getHelperRestFull()!!.create(API::class.java)
    }

    // user
    fun getLogin(username: String, password: String): Single<BaseResponse<LoginData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.login(
                username, password
            )
        )
    }

    fun getSignUp(
        email: String,
        password: String,
        display_name: String,
        gender: String
    ): Single<BaseResponse<SignUpData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.signUp(
                email,
                password,
                display_name,
                gender
            )
        )
    }

    //group
    fun getGroups(): Single<BaseResponseList<GroupData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getGroups()
        )
    }

    fun getGroupId(groupId: String): Single<BaseResponseList<GroupData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getGroupId(
                groupId
            )
        )
    }

    fun getCreateGroups(
        name: String
    ): Single<BaseResponse<CreateGroupData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateGroup(
                name
            )
        )
    }

    fun getUpdateGroup(
        groupId: String, name: String,
    ): Single<BaseResponse<UpdateGroupData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateGroup(groupId, name)
        )
    }

    fun getDeleteGroup(groupId: String): Single<BaseResponse<CreateGroupData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteGroup(groupId)
        )
    }

    //topic
    fun getTopic(
        groupId: String
    ): Single<BaseResponseList<TopicData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                groupId
            )
        )
    }

    fun getTopicId(
        groupId: String, topicId: String
    ): Single<BaseResponseList<TopicData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                groupId, topicId
            )
        )
    }

    fun getCreateTopic(
        name: String, description: String, groupId: String
    ): Single<BaseResponse<CreateTopicData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateTopic(
                groupId, name, description
            )
        )
    }

    fun getUpdateTopic(
        name: String, description: String, groupId: String, topicId: String
    ): Single<BaseResponse<UpdateTopicData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateTopic(
                groupId, topicId, name, description
            )
        )
    }

    fun getDeleteTopic(
        groupId: String, topicId: String
    ): Single<BaseResponse<CreateTopicData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteTopic(
                groupId, topicId
            )
        )
    }

    //user
    fun getUserInfo(

    ): Single<BaseResponse<UserData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserInfo(
            )
        )
    }

    fun getUserChangePass(
        old: String, new: String, renew: String
    ): Single<BaseResponse<UserData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserChangePass(
                old, new, renew
            )
        )
    }

    //post
    fun getPost(groupId: String, topicId: String): Single<BaseResponseList<PostData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPost(groupId, topicId)
        )
    }

    fun getCreatePost(
        groupId: String, topicId: String,
        title: String,
        description: String
    ): Single<BaseResponse<CreatePostData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreatePost(
                groupId, topicId,
                title,
                description
            )
        )
    }

    fun getUpdatePost(
        groupId: String, topicId: String, postId: String,
        title: String,
        description: String
    ): Single<BaseResponse<CreatePostData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdatePost(
                groupId, topicId,
                postId,
                title,
                description
            )
        )
    }

    fun getDeletePost(
        groupId: String, topicId: String, postId: String,
    ): Single<BaseResponse<CreatePostData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeletePost(groupId, topicId, postId)
        )
    }

    //comment
    fun getPostId(
        groupId: String, topicId: String, postId: String,
    ): Single<BaseResponseList<PostData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPostId(groupId, topicId, postId)
        )
    }

    fun getComment(
        groupId: String, topicId: String, postId: String,
    ): Single<BaseResponseList<CommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getComment(groupId, topicId, postId)
        )
    }

    fun getCommentId(
        groupId: String, topicId: String, postId: String,
        commentId: String
    ): Single<BaseResponseList<CommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCommentId(
                groupId, topicId, postId,
                commentId
            )
        )
    }

    fun getCreateComment(
        groupId: String, topicId: String, postId: String,
        description: String
    ): Single<BaseResponse<CreateCommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateComment(
                groupId, topicId, postId,
                description
            )
        )
    }

    fun getUpdateComment(
        groupId: String, topicId: String, postId: String,
        commentId: String,
        description: String,
    ): Single<BaseResponse<CreateCommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateComment(
                groupId, topicId, postId,
                commentId,
                description
            )
        )
    }

    fun getDeleteComment(
        groupId: String, topicId: String, postId: String, commentId: String
    ): Single<BaseResponse<CreateCommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteComment(
                groupId, topicId, postId, commentId
            )
        )
    }

    //feed
    fun getFeed(): Single<BaseResponseList<FeedData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeed()
        )
    }

    fun getFeedID(feedId: String): Single<BaseResponseList<FeedData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeedID(feedId)
        )
    }

    fun getDeleteFeedID(feedId: String): Single<BaseResponseList<FeedData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteFeedID(feedId)
        )
    }

    fun getFeedComment(feedId: String): Single<BaseResponseList<FeedCommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeedComment(feedId)
        )
    }

    fun getFeedCommentID(
        feedId: String,
        commentId: String
    ): Single<BaseResponseList<FeedCommentData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeedCommentID(feedId, commentId)
        )
    }

    fun getCreateFeedComment(
        feedId: String,
        description: String
    ): Single<BaseResponse<CreateFeedDataComment>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateFeedComment(feedId, description)
        )
    }

    fun getUpdateFeedComment(
        feedId: String, commentId: String,
        description: String,
    ): Single<BaseResponse<CreateFeedDataComment>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateFeedComment(
                feedId, commentId,
                description

            )
        )
    }

    fun getDeleteFeedComment(
        feedId: String, commentId: String,
    ): Single<BaseResponse<CreateFeedDataComment>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteFeedComment(feedId, commentId)
        )
    }

    fun getCreateFeed(
        description: String,
        attachments: ArrayList<String>
    ): Single<BaseResponse<CreateFeedData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateFeed(description, attachments)
        )
    }

    fun getUpdateFeed(
        description: String,
        attachments: ArrayList<String>,
        feedId: String
    ): Single<BaseResponse<CreateFeedData>> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateFeed(feedId, description, attachments)
        )
    }
}