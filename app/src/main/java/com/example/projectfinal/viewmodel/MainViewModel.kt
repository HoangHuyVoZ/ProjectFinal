package com.example.projectfinal.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.comment.CreateCommentData
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.model.group.CreateGroupData
import com.example.projectfinal.model.group.Groupdata
import com.example.projectfinal.model.group.UpdateGroupData
import com.example.projectfinal.model.post.CreatePostData
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.model.topic.CreateTopicData
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.model.topic.UpdateTopicData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()


    var groupData: MutableLiveData<BaseResponseList<Groupdata>> =
        MutableLiveData<BaseResponseList<Groupdata>>()
    var groupDataId: MutableLiveData<BaseResponseList<Groupdata>> =
        MutableLiveData<BaseResponseList<Groupdata>>()
    var createData: MutableLiveData<BaseResponse<CreateGroupData>> =
        MutableLiveData<BaseResponse<CreateGroupData>>()
    var updateGroupData: MutableLiveData<BaseResponse<UpdateGroupData>> =
        MutableLiveData<BaseResponse<UpdateGroupData>>()
    var deleteGroupData: MutableLiveData<BaseResponse<CreateGroupData>> =
        MutableLiveData<BaseResponse<CreateGroupData>>()
    var countGroupData: MutableLiveData<Int> = MutableLiveData<Int>()

    var topicData: MutableLiveData<BaseResponseList<TopicData>> =
        MutableLiveData<BaseResponseList<TopicData>>()
    var topicDataId: MutableLiveData<BaseResponseList<TopicData>> =
        MutableLiveData<BaseResponseList<TopicData>>()
    var createTopicData: MutableLiveData<BaseResponse<CreateTopicData>> =
        MutableLiveData<BaseResponse<CreateTopicData>>()
    var updateTopicData: MutableLiveData<BaseResponse<UpdateTopicData>> =
        MutableLiveData<BaseResponse<UpdateTopicData>>()
    var deleteTopicData: MutableLiveData<BaseResponse<CreateTopicData>> =
        MutableLiveData<BaseResponse<CreateTopicData>>()
    var countTopicData: MutableLiveData<Int> = MutableLiveData<Int>()

    var countPostData: MutableLiveData<Int> = MutableLiveData<Int>()
    var postData: MutableLiveData<BaseResponseList<PostData>> =
        MutableLiveData<BaseResponseList<PostData>>()
    var createPostData: MutableLiveData<BaseResponse<CreatePostData>> =
        MutableLiveData<BaseResponse<CreatePostData>>()
    var updatePostData: MutableLiveData<BaseResponse<CreatePostData>> =
        MutableLiveData<BaseResponse<CreatePostData>>()
    var deletePostData: MutableLiveData<BaseResponse<CreatePostData>> =
        MutableLiveData<BaseResponse<CreatePostData>>()
    var postIdData: MutableLiveData<BaseResponseList<PostData>> =
        MutableLiveData<BaseResponseList<PostData>>()

    var commentData: MutableLiveData<BaseResponseList<commentData>> =
        MutableLiveData<BaseResponseList<commentData>>()
    var commentIdData: MutableLiveData<BaseResponseList<commentData>> =
        MutableLiveData<BaseResponseList<commentData>>()
    var createComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var updateComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var deleteComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var countCommentData: MutableLiveData<Int> = MutableLiveData<Int>()


    fun getCreateData(name: String) {
        compositeDisposable.add(
            apiManager.getCreateGroups(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        createData.value = it
                        if (it.result != null) {
                            getGroupId(it.result.id)
                        }
                        countGroupData.value = countGroupData.value?.plus(1)
                    } else {
                        createData.value = it
                    }

                }, {
                    createData.value = null
                })
        )
    }

    fun getGroup() {
        compositeDisposable.add(
            apiManager.getGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupData.value = it
                    countGroupData.value = it.result.size
                }, {
                    groupData.value = null
                })
        )
    }

    private fun getGroupId(groupId: String) {
        compositeDisposable.add(
            apiManager.getGroupId(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupDataId.value = it
                }, {
                    groupDataId.value = null
                })
        )
    }

    fun getUpdateGroup(groupId: String, name: String) {
        compositeDisposable.add(
            apiManager.getUpdateGroup(groupId, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateGroupData.value = it
                    if (it.result != null) {
                        getGroupId(it.result.id)
                    }
                }, {
                    updateGroupData.value = null
                })
        )
    }

    fun getDeleteGroup(groupId: String) {
        compositeDisposable.add(
            apiManager.getDeleteGroup(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        deleteGroupData.value = it
                        countGroupData.value = countGroupData.value?.minus(1)
                    } else {
                        deleteGroupData.value = it

                    }

                }, {
                    deleteGroupData.value = null
                })
        )
    }

    //topic
    fun getTopic(groupId: String) {
        compositeDisposable.add(
            apiManager.getTopic(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicData.value = it
                    countTopicData.value = it.result.size
                }, {
                    topicData.value = null

                })
        )
    }

    fun getTopicId(topicId: String, groupId: String) {
        compositeDisposable.add(
            apiManager.getTopicId(groupId, topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicDataId.value = it
                }, {
                    topicDataId.value = null

                })
        )
    }

    fun getCreateTopic(name: String, descripton: String, groupId: String) {
        compositeDisposable.add(
            apiManager.getCreateTopic(name, descripton, groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createTopicData.value = it
                    if (it.result != null) {
                        getTopicId(it.result.id, groupId)
                    }
                    countTopicData.value = countTopicData.value?.plus(1)
                }, {
                    createTopicData.value = null

                })
        )
    }

    fun getUpdateTopic(
        name: String,
        description: String, groupId: String, topicId: String
    ) {
        compositeDisposable.add(
            apiManager.getUpdateTopic(name, description, groupId, topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateTopicData.value = it
                    if (it.result != null) {
                        getTopicId(it.result.id, groupId)
                    }
                }, {
                    updateTopicData.value = null

                })
        )
    }

    fun getDeleteTopic(groupId: String, topicId: String) {
        compositeDisposable.add(
            apiManager.getDeleteTopic(groupId, topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteTopicData.value = it
                    countTopicData.value = countTopicData.value?.minus(1)
                }, {
                    deleteTopicData.value = null

                })
        )
    }

    //post
    fun getPost(groupId: String, topicId: String) {
        compositeDisposable.add(
            apiManager.getPost(groupId, topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postData.value = it
                    countPostData.value = it.result.size
                }, {
                    postData.value = null

                })
        )
    }

    fun getCreatePost(
        groupId: String, topicId: String,
        title: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getCreatePost(groupId, topicId, title, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createPostData.value = it
                    if (it.result != null) {
                        getPostId( groupId,topicId, it.result.id)
                    }

                    countPostData.value = countPostData.value?.plus(1)
                }, {
                    createPostData.value = null

                })
        )
    }

    fun getUpdatePost(
        groupId: String, topicId: String, postId: String,
        name: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getUpdatePost(groupId, topicId, postId, name, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        updatePostData.value = it
                        if(it.result!=null){
                            getPostId( groupId,topicId, it.result.id)
                        }
                    } else {
                        updatePostData.value = null
                    }

                }, {
                    updatePostData.value = null

                })
        )
    }

    fun getDeletePost(groupId: String, topicId: String, postId: String) {
        compositeDisposable.add(
            apiManager.getDeletePost(groupId, topicId, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deletePostData.value = it
                    countPostData.value = countPostData.value?.minus(1)

                }, {
                    deletePostData.value = null

                })
        )
    }

    //comment
    fun getPostId(groupId: String, topicId: String, postId: String) {
        compositeDisposable.add(
            apiManager.getPostId(groupId, topicId, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postIdData.value = it
                }, {
                    postIdData.value = null
                })
        )
    }

    fun getComment(groupId: String, topicId: String, postId: String) {
        compositeDisposable.add(
            apiManager.getComment(groupId, topicId, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    commentData.value = it
                    countCommentData.value = it.result.size
                }, {
                    commentData.value = null
                })
        )
    }

    fun getCommentId(
        groupId: String, topicId: String, postId: String,
        commentId: String
    ) {
        compositeDisposable.add(
            apiManager.getCommentId(groupId, topicId, postId, commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    commentIdData.value = it
                }, {
                    commentIdData.value = null
                })
        )
    }

    fun getCreateComment(
        groupId: String, topicId: String, postId: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getCreateComment(groupId, topicId, postId, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createComment.value = it
                    if(it.result!=null){
                        getCommentId(groupId, topicId, postId, it.result.id)
                    }
                    countCommentData.value = countCommentData.value?.plus(1)
                }, {
                    createComment.value = null
                })
        )
    }

    fun getUpdateComment(
        groupId: String, topicId: String, postId: String,
        commentId: String,
        description: String,
    ) {
        compositeDisposable.add(
            apiManager.getUpdateComment(
                groupId, topicId, postId, commentId, description
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateComment.value = it
                    if(it.result!=null){
                        getCommentId(groupId, topicId, postId, it.result.id)
                    }
                }, {
                    updateComment.value = null
                })
        )
    }

    fun getDeleteComment(
        groupId: String, topicId: String, postId: String, commentId: String
    ) {
        compositeDisposable.add(
            apiManager.getDeleteComment(groupId, topicId, postId, commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteComment.value = it
                    countCommentData.value = countCommentData.value?.minus(1)
                }, {
                    deleteComment.value = null
                })
        )
    }

}