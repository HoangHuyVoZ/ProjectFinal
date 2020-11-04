package com.example.projectfinal.api

import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.Signup.Signup
import com.example.projectfinal.model.Topic.Topic
import com.example.projectfinal.model.user.User
import com.example.projectfinal.model.comment.comment
import com.example.projectfinal.model.feed.feed
import com.example.projectfinal.model.login.Login
import com.example.projectfinal.utils.AUTHORIZATION
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
    fun getGroups(
        authorization: String
    ): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getGroups(
                AUTHORIZATION + authorization
            )
        )
    }

    fun getCreateGroups(
        authorization: String, name: String
    ): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateGroup(
                AUTHORIZATION + authorization, name
            )
        )
    }

    fun getUpdateGroup(
        authorization: String, group_id: String, name: String
    ): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateGroup(AUTHORIZATION + authorization, group_id, name)
        )
    }

    fun getDeleteGroup(
        authorization: String, group_id: String
    ): Single<Group> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteGroup(AUTHORIZATION + authorization, group_id)
        )
    }

    //topic
    fun getTopic(
        authorization: String, group_id: String
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                AUTHORIZATION + authorization,
                group_id
            )
        )
    }

    fun getCreateTopic(
        authorization: String, group_id: String, name: String, description: String
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateTopic(
                AUTHORIZATION + authorization,
                group_id, name, description
            )
        )
    }

    fun getUpdateTopic(
        authorization: String, group_id: String, topic_id: String, name: String, description: String
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateTopic(
                AUTHORIZATION + authorization,
                group_id, topic_id, name, description
            )
        )
    }

    fun getDeleteTopic(
        authorization: String, group_id: String, topic_id: String
    ): Single<Topic> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteTopic(
                AUTHORIZATION + authorization,
                group_id, topic_id
            )
        )
    }

    //user
    fun getUserInfo(
        authorization: String
    ): Single<User> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserInfo(
                AUTHORIZATION + authorization,
            )
        )
    }

    fun getUserChangePass(
        authorization: String, old: String, new: String, renew: String
    ): Single<User> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUserChangePass(
                AUTHORIZATION + authorization,
                old, new, renew
            )
        )
    }

    //post
    fun getPost(
        authorization: String, group_id: String, topic_id: String
    ): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPost(AUTHORIZATION + authorization, group_id, topic_id)
        )
    }

    fun getCreatePost(
        authorization: String,
        group_id: String,
        topic_id: String,
        title: String,
        description: String
    ): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreatePost(
                AUTHORIZATION + authorization,
                group_id,
                topic_id,
                title,
                description
            )
        )
    }

    fun getUpdatePost(
        authorization: String,
        group_id: String,
        topic_id: String,
        post_id: String,
        title: String,
        description: String
    ): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdatePost(
                AUTHORIZATION + authorization,
                group_id,
                topic_id,
                post_id,
                title,
                description
            )
        )
    }

    fun getDeletePost(
        authorization: String, group_id: String, topic_id: String, post_id: String
    ): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeletePost(AUTHORIZATION + authorization, group_id, topic_id, post_id)
        )
    }

    //comment
    fun getPostId(
        authorization: String, group_id: String, topic_id: String, post_id: String
    ): Single<Post> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getPostId(AUTHORIZATION + authorization, group_id, topic_id, post_id)
        )
    }

    fun getComment(
        authorization: String, group_id: String, topic_id: String, post_id: String
    ): Single<comment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getComment(AUTHORIZATION + authorization, group_id, topic_id, post_id)
        )
    }
    fun getCreateComment(
        authorization: String, group_id: String, topic_id: String, post_id: String,description: String
    ): Single<comment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateComment(AUTHORIZATION + authorization, group_id, topic_id, post_id,description)
        )
    }
    fun getUpdateComment(
        authorization: String, group_id: String, topic_id: String, post_id: String,description: String,comment_id: String
    ): Single<comment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateComment(AUTHORIZATION + authorization, group_id, topic_id, post_id,comment_id,description)
        )
    }
    fun getDeleteComment(
        authorization: String, group_id: String, topic_id: String, post_id: String,comment_id: String
    ): Single<comment> {
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteComment(AUTHORIZATION + authorization, group_id, topic_id, post_id,comment_id)
        )
    }
    //feed
    fun getFeed(authorization: String): Single<feed>{
        return RetrofitInstants.buildRequest(
            _apiRestFull.getFeed(AUTHORIZATION+authorization)
        )
    }
}