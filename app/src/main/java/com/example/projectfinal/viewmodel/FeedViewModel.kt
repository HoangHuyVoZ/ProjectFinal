package com.example.projectfinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.feed.feed
import com.example.projectfinal.model.feed.feedComment
import com.example.projectfinal.model.feed.feedCommentData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val apiManager: CallApi by lazy { CallApi() }
    var dataFeed: MutableLiveData<feed> = MutableLiveData<feed>()

    //    var dataFeedLiveData : LiveData<feed> = Transformations.switchMap(dataFeed) {
//        val data = MutableLiveData<feed>()
//        data.value = it
//        data
//    }

    var dataCreateFeed: MutableLiveData<feed> = MutableLiveData<feed>()
    var dataFeedID: MutableLiveData<feed> = MutableLiveData<feed>()
    var dataComment: MutableLiveData<feedComment> = MutableLiveData<feedComment>()
    var dataCreateComment: MutableLiveData<String> = MutableLiveData<String>()
    var dataDeleteComment: MutableLiveData<String> = MutableLiveData<String>()
    var dataUpdateComment: MutableLiveData<String> = MutableLiveData<String>()
    var dataCountFeed: MutableLiveData<Int> = MutableLiveData<Int>()
    var dataDeleteFeed: MutableLiveData<feed> = MutableLiveData<feed>()


    fun getCreatedFeed( description: String, attachments: ArrayList<String>) {
        compositeDisposable.add(
            apiManager.getCreateFeed( description, attachments)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataCreateFeed.value = it
                        dataCountFeed.value = dataCountFeed.value?.plus(1)
                    } else {
                        dataCreateFeed.value = it

                    }

                }, {
                    dataDeleteFeed.value = null
                })
        )
    }

    fun getFeedComment() {
        compositeDisposable.add(
            apiManager.getFeedComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataComment.value = it
                }, {
                    dataComment.value = null
                })
        )
    }

    fun getDeleteFeed() {
        compositeDisposable.add(
            apiManager.getDeleteFeedID()
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

    fun getFeedID() {
        compositeDisposable.add(
            apiManager.getFeedID()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataFeedID.value = it
                }, {
                    dataFeedID.value = null

                })
        )
    }

    fun getCreateFeed( description: String) {
        compositeDisposable.add(
            apiManager.getCreateFeedComment( description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataCreateComment.value = it.message
                    } else {
                        dataCreateComment.value = it.message
                    }
                }, {
                    dataCreateComment.value = null

                })
        )
    }

    fun getDeleteFeedComment() {
        compositeDisposable.add(
            apiManager.getDeleteFeedComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataDeleteComment.value = it.message
                    } else {
                        dataDeleteComment.value = it.message
                    }
                }, {
                    dataDeleteComment.value = null

                })
        )
    }

    fun getUpdateFeedComment(
        description: String,
    ) {
        compositeDisposable.add(
            apiManager.getUpdateFeedComment(description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        dataUpdateComment.value = it.message
                    } else {
                        dataUpdateComment.value = it.message
                    }
                }, {
                    dataUpdateComment.value = null

                })
        )
    }
}