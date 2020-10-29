package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.Topic.Topic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()

    var groupData : MutableLiveData<Group> = MutableLiveData<Group>()
    var createData: MutableLiveData<Group> = MutableLiveData<Group>()
    var updateGroupData: MutableLiveData<Group> = MutableLiveData<Group>()
    var deleteGroupData: MutableLiveData<Group> = MutableLiveData<Group>()
    var topicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var createTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var updateTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var deleteTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()


    fun getCreateData(Authorization: String,name: String){
        compositeDisposable.add(
            apiManager.getCreateGroups(Authorization,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createData.value= it
                },{
                    createData.value=null
                })
        )
    }
    fun getGroup(Authorization : String){
        compositeDisposable.add(
            apiManager.getGroups(Authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupData.value = it
                },{
                    groupData.value = null
                })
        )
    }
    fun getUpdateGroup(Authorization: String,group_id: String,name: String){
        compositeDisposable.add(
            apiManager.getUpdateGroup(Authorization,group_id,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateGroupData.value = it
                },{
                    updateGroupData.value = null
                })
        )
    }
    fun getDeleteGroup(Authorization: String,group_id: String){
        compositeDisposable.add(
            apiManager.getDeleteGroup(Authorization,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteGroupData.value = it
                },{
                    deleteGroupData.value = null
                })
        )
    }
    //topic
    fun getTopic(Authorization:String,group_id: String){
        compositeDisposable.add(
            apiManager.getTopic(Authorization,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicData.value=it
                },{
                    topicData.value=null

                })
        )
    }
    fun getCreateTopic(Authorization: String,group_id: String,name: String,descripton: String){
        compositeDisposable.add(
            apiManager.getCreateTopic(Authorization,group_id,name,descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createTopicData.value= it
                },{
                    createTopicData.value= null

                })
        )
    }
    fun getUpdateTopic(Authorization: String,group_id: String,topic_id: String,name: String,descripton: String){
        compositeDisposable.add(
            apiManager.getUpdateTopic(Authorization,group_id,topic_id,name,descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateTopicData.value= it
                },{
                    updateTopicData.value= null

                })
        )
    }
    fun getDeleteTopic(Authorization: String,group_id: String,topic_id:String){
        compositeDisposable.add(
            apiManager.getDeleteTopic(Authorization,group_id,topic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteTopicData.value= it
                },{
                    deleteTopicData.value= null

                })
        )
    }
}