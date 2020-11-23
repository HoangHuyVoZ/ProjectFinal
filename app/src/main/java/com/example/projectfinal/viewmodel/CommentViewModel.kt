package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.comment.CommentData
import com.example.projectfinal.model.comment.CreateCommentData
import com.example.projectfinal.model.post.PostData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CommentViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()

    var commentData: MutableLiveData<BaseResponseList<CommentData>> =
        MutableLiveData<BaseResponseList<CommentData>>()
    var commentIdData: MutableLiveData<BaseResponseList<CommentData>> =
        MutableLiveData<BaseResponseList<CommentData>>()
    var createComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var updateComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var deleteComment: MutableLiveData<BaseResponse<CreateCommentData>> =
        MutableLiveData<BaseResponse<CreateCommentData>>()
    var countCommentData: MutableLiveData<Int> = MutableLiveData<Int>()
    var postIdData: MutableLiveData<BaseResponseList<PostData>> =
        MutableLiveData<BaseResponseList<PostData>>()


    //topic

    //post


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

    private fun getCommentId(
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
                    getCommentId(groupId, topicId, postId, it.result.id)
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
                    getCommentId(groupId, topicId, postId, it.result.id)
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