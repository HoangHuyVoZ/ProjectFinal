package com.example.projectfinal.api

import com.example.projectfinal.model.group.CreateGroup
import com.example.projectfinal.model.group.Group
import com.example.projectfinal.model.signup.Signup
import com.example.projectfinal.model.topic.CreateTopic
import com.example.projectfinal.model.topic.Topic
import com.example.projectfinal.model.topic.updateTopic
import com.example.projectfinal.model.comment.CreateComment
import com.example.projectfinal.model.comment.comment
import com.example.projectfinal.model.feed.feed
import com.example.projectfinal.model.feed.feedComment
import com.example.projectfinal.model.login.Login
import com.example.projectfinal.model.post.CreatePost
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.user.User
import com.example.projectfinal.utils.*
import io.reactivex.Single

class CallApi {
    private val _apiRestFull: API by lazy {
        RetrofitInstants.getHelperRestFull()!!.create(API::class.java)
    }

    // user
    fun getLogin(username: String, password: String): Single<Login> {
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
    ): Single<Signup> {
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
    fun getGroups(): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getGroups(
                AUTHORIZATION + accessToken
            )
        )
    }
    fun getGroupId(groupId:String): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getGroupId(
                AUTHORIZATION + accessToken, groupId
            )
        )
    }
    fun getCreateGroups(
        name: String
    ): Single<CreateGroup> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateGroup(
                AUTHORIZATION + accessToken, name
            )
        )
    }

    fun getUpdateGroup(
        name: String
    ): Single<CreateGroup> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateGroup(AUTHORIZATION + accessToken, groupId, name)
        )
    }

    fun getDeleteGroup(): Single<CreateGroup> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteGroup(AUTHORIZATION + accessToken, groupId)
        )
    }

    //topic
    fun getTopic(
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                AUTHORIZATION + accessToken, groupId
            )
        )
    }
    fun getTopicId(topicId: String
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                AUTHORIZATION + accessToken, groupId, topicId
            )
        )
    }
    fun getCreateTopic(
       name: String, description: String
    ): Single<CreateTopic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateTopic(
                AUTHORIZATION + accessToken, groupId, name, description
            )
        )
    }

    fun getUpdateTopic(name: String, description: String
    ): Single<updateTopic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateTopic(
                AUTHORIZATION + accessToken, groupId, topicId, name, description
            )
        )
    }

    fun getDeleteTopic(
    ): Single<CreateTopic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteTopic(
                AUTHORIZATION + accessToken, groupId, topicId
            )
        )
    }

    //user
    fun getUserInfo(

    ): Single<User> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserInfo(
                AUTHORIZATION + accessToken,
            )
        )
    }

    fun getUserChangePass(
     old: String, new: String, renew: String
    ): Single<User> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserChangePass(
                AUTHORIZATION + accessToken,
                old, new, renew
            )
        )
    }

    //post
    fun getPost(): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPost(AUTHORIZATION + accessToken, groupId, topicId)
        )
    }

    fun getCreatePost(
        title: String,
        description: String
    ): Single<CreatePost> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreatePost(
                AUTHORIZATION + accessToken, groupId, topicId,
                title,
                description
            )
        )
    }

    fun getUpdatePost(
        title: String,
        description: String
    ): Single<CreatePost> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdatePost(
                AUTHORIZATION + accessToken, groupId, topicId,
                postId,
                title,
                description
            )
        )
    }

    fun getDeletePost(
    ): Single<CreatePost> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeletePost(AUTHORIZATION + accessToken, groupId, topicId, postId)
        )
    }

    //comment
    fun getPostId(): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPostId(AUTHORIZATION + accessToken, groupId, topicId, postId)
        )
    }

    fun getComment(
    ): Single<comment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getComment(AUTHORIZATION + accessToken, groupId, topicId, postId)
        )
    }

    fun getCreateComment(
        description: String
    ): Single<CreateComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateComment(
                AUTHORIZATION + accessToken, groupId, topicId, postId,
                description
            )
        )
    }

    fun getUpdateComment(

        description: String,
    ): Single<CreateComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateComment(
                AUTHORIZATION + accessToken, groupId, topicId, postId,
                commentId,
                description
            )
        )
    }

    fun getDeleteComment(
    ): Single<CreateComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteComment(
                AUTHORIZATION + accessToken, groupId, topicId, postId, commentId
            )
        )
    }

    //feed
    fun getFeed(): Single<feed> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeed(AUTHORIZATION + accessToken)
        )
    }

    fun getFeedID(): Single<feed> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeedID(AUTHORIZATION + accessToken, feedId)
        )
    }

    fun getDeleteFeedID(): Single<feed> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteFeedID(AUTHORIZATION + accessToken, feedId)
        )
    }

    fun getFeedComment(): Single<feedComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeedComment(AUTHORIZATION + accessToken, feedId)
        )
    }

    fun getCreateFeedComment(
        description: String
    ): Single<feedComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateFeedComment(AUTHORIZATION + accessToken, feedId, description)
        )
    }

    fun getUpdateFeedComment(
        description: String,
    ): Single<feedComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateFeedComment(
                AUTHORIZATION + accessToken, feedId,commentId,
                description

            )
        )
    }

    fun getDeleteFeedComment(
    ): Single<feedComment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteFeedComment(AUTHORIZATION + accessToken, feedId,commentId,)
        )
    }

    fun getCreateFeed(
        description: String,
        attachments: ArrayList<String>
    ): Single<feed> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateFeed(AUTHORIZATION + accessToken, description, attachments)
        )
    }
}