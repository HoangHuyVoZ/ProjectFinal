package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.post.CreatePostData
import com.example.projectfinal.model.post.PostData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()
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

    private fun getPostId(groupId: String, topicId: String, postId: String) {
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
                    getPostId(groupId, topicId, it.result.id)

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
                        getPostId(groupId, topicId, it.result.id)
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
}