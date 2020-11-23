package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.topic.CreateTopicData
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.model.topic.UpdateTopicData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopicViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()
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

    private fun getTopicId(topicId: String, groupId: String) {
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
                    getTopicId(it.result.id, groupId)
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
                    getTopicId(it.result.id, groupId)
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

}