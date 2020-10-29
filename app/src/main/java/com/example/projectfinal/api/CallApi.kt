package com.example.projectfinal.api

import android.util.Log
import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.Signup.Signup
import com.example.projectfinal.model.Topic.Topic
import com.example.projectfinal.model.User.User
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
    fun getCreateGroups(authorization: String,name: String
    ):Single<Group>{
        return RetrofitInstants.buildRequest(
            _apiRestFull.getCreateGroup(
                AUTHORIZATION+authorization,name
            )
        )
    }
    fun getUpdateGroup(authorization: String,group_id: String,name: String
    ):Single<Group>{
        return RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateGroup(AUTHORIZATION+authorization,group_id,name)
        )
    }
    fun getDeleteGroup(authorization: String,group_id: String
    ):Single<Group>{
        return RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteGroup(AUTHORIZATION+authorization,group_id)
        )
    }
    //topic
    fun getTopic(authorization: String,group_id: String
    ):Single<Topic>{
        return  RetrofitInstants.buildRequest(
            _apiRestFull.getTopic(
                AUTHORIZATION+authorization,
                group_id
            )
        )
    }
    fun getCreateTopic(authorization: String,group_id: String,name: String,description: String
    ):Single<Topic>{
        return  RetrofitInstants.buildRequest(
            _apiRestFull.getCreateTopic(
                AUTHORIZATION+authorization,
                group_id,name,description
            )
        )
    }
    fun getUpdateTopic(authorization: String,group_id: String,topic_id: String,name: String,description: String
    ):Single<Topic>{
        return  RetrofitInstants.buildRequest(
            _apiRestFull.getUpdateTopic(
                AUTHORIZATION+authorization,
                group_id,topic_id,name,description
            )
        )
    }
    fun getDeleteTopic(authorization: String,group_id: String,topic_id: String
    ):Single<Topic>{
        return  RetrofitInstants.buildRequest(
            _apiRestFull.getDeleteTopic(
                AUTHORIZATION+authorization,
                group_id,topic_id
            )
        )
    }
    //user
    fun getUserInfo(authorization: String
    ):Single<User>{
        return  RetrofitInstants.buildRequest(
            _apiRestFull.getUserInfo(
                AUTHORIZATION+authorization,
            )
        )
    }
}