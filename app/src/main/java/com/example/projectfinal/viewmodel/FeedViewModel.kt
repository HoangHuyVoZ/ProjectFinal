package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.feed.CreateFeedData
import com.example.projectfinal.model.feed.CreateFeedDataComment
import com.example.projectfinal.model.feed.FeedCommentData
import com.example.projectfinal.model.feed.FeedData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

//    var dataFeedLiveData : LiveData<feed> = Transformations.switchMap(dataFeed) {
//        val data = MutableLiveData<feed>()
//        data.value = it
//        data
//    }
class FeedViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val apiManager: CallApi by lazy { CallApi() }
    var dataFeed: MutableLiveData<BaseResponseList<FeedData>> =
        MutableLiveData<BaseResponseList<FeedData>>()
    var dataCreateFeed: MutableLiveData<BaseResponse<CreateFeedData>> =
        MutableLiveData<BaseResponse<CreateFeedData>>()
    var dataUpdateFeed: MutableLiveData<BaseResponse<CreateFeedData>> =
        MutableLiveData<BaseResponse<CreateFeedData>>()
    var dataFeedID: MutableLiveData<BaseResponseList<FeedData>> =
        MutableLiveData<BaseResponseList<FeedData>>()
    var dataCountFeed: MutableLiveData<Int> = MutableLiveData<Int>()
    var dataDeleteFeed: MutableLiveData<BaseResponseList<FeedData>> =
        MutableLiveData<BaseResponseList<FeedData>>()

    var dataFeedCommentID: MutableLiveData<BaseResponseList<FeedCommentData>> =
        MutableLiveData<BaseResponseList<FeedCommentData>>()
    var dataComment: MutableLiveData<BaseResponseList<FeedCommentData>> =
        MutableLiveData<BaseResponseList<FeedCommentData>>()
    var dataCreateComment: MutableLiveData<BaseResponse<CreateFeedDataComment>> =
        MutableLiveData<BaseResponse<CreateFeedDataComment>>()
    var dataDeleteComment: MutableLiveData<BaseResponse<CreateFeedDataComment>> =
        MutableLiveData<BaseResponse<CreateFeedDataComment>>()
    var dataUpdateComment: MutableLiveData<BaseResponse<CreateFeedDataComment>> =
        MutableLiveData<BaseResponse<CreateFeedDataComment>>()
    var dataCountComment: MutableLiveData<Int> = MutableLiveData<Int>()

    fun getCreateFeed(description: String, attachments: ArrayList<String>) {
        compositeDisposable.add(
            apiManager.getCreateFeed(description, attachments)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataCreateFeed.value = it
                        dataCountFeed.value = dataCountFeed.value?.plus(1)
                        getFeedID(it.result.id)
                    } else {
                        dataCreateFeed.value = it

                    }

                }, {
                    dataDeleteFeed.value = null
                })
        )
    }

    fun getUpdateFeed(description: String, attachments: ArrayList<String>, feedId: String) {
        compositeDisposable.add(
            apiManager.getUpdateFeed(description, attachments, feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataCreateFeed.value = it
                        getFeedID(it.result.id)
                    } else {
                        dataCreateFeed.value = it

                    }

                }, {
                    dataDeleteFeed.value = null
                })
        )
    }

    fun getDeleteFeed(feedId: String) {
        compositeDisposable.add(
            apiManager.getDeleteFeedID(feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataDeleteFeed.value = it
                        dataCountFeed.value = dataCountFeed.value?.minus(1)
                    } else {
                        dataDeleteFeed.value = it
                    }
                }, {
                    dataDeleteFeed.value = null

                })
        )
    }

    fun getFeed() {
        compositeDisposable.add(
            apiManager.getFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataFeed.value = it
                    dataCountFeed.value = it.result.size
                }, {
                    dataFeed.value = null

                })
        )
    }


    fun getFeedID(feedId: String) {
        compositeDisposable.add(
            apiManager.getFeedID(feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataFeedID.value = it
                }, {
                    dataFeedID.value = null

                })
        )
    }

    fun getFeedComment(feedId: String) {
        compositeDisposable.add(
            apiManager.getFeedComment(feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataComment.value = it
                    dataCountComment.value = it.result.size
                }, {
                    dataComment.value = null
                })
        )
    }

    private fun getFeedCommentId(feedId: String, commentId: String) {
        compositeDisposable.add(
            apiManager.getFeedCommentID(feedId, commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataFeedCommentID.value = it
                }, {
                    dataFeedCommentID.value = null
                })
        )
    }

    fun getCreateFeedComment(feedId: String, description: String) {
        compositeDisposable.add(
            apiManager.getCreateFeedComment(feedId, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataCreateComment.value = it
                        dataCountComment.value = dataCountComment.value?.plus(1)
                        getFeedCommentId(feedId, it.result.id)
                    } else {
                        dataCreateComment.value = it
                    }
                }, {
                    dataCreateComment.value = null

                })
        )
    }

    fun getDeleteFeedComment(feedId: String, commentId: String) {
        compositeDisposable.add(
            apiManager.getDeleteFeedComment(feedId, commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataDeleteComment.value = it
                        dataCountComment.value = dataCountComment.value?.minus(1)
                    } else {
                        dataDeleteComment.value = it
                    }
                }, {
                    dataDeleteComment.value = null

                })
        )
    }

    fun getUpdateFeedComment(
        feedId: String, commentId: String,
        description: String,
    ) {
        compositeDisposable.add(
            apiManager.getUpdateFeedComment(feedId, commentId, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataUpdateComment.value = it
                        getFeedCommentId(feedId, it.result.id)

                    } else {
                        dataUpdateComment.value = it
                    }
                }, {
                    dataUpdateComment.value = null

                })
        )
    }

}