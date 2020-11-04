package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.feed.feed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FeedViewModel :ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val apiManager: CallApi by lazy { CallApi() }
    var dataFeed : MutableLiveData<feed> = MutableLiveData<feed>()

    fun getFeed(Authorization: String){
        compositeDisposable.add(
            apiManager.getFeed(Authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataFeed.value = it
                },{
                    dataFeed.value = null

                })
        )
    }
}