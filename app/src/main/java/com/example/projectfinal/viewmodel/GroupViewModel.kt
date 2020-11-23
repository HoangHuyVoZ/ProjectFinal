package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.BaseResponseList
import com.example.projectfinal.model.group.CreateGroupData
import com.example.projectfinal.model.group.GroupData
import com.example.projectfinal.model.group.UpdateGroupData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GroupViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()
    var groupData: MutableLiveData<BaseResponseList<GroupData>> =
        MutableLiveData<BaseResponseList<GroupData>>()
    var groupDataId: MutableLiveData<BaseResponseList<GroupData>> =
        MutableLiveData<BaseResponseList<GroupData>>()
    var createData: MutableLiveData<BaseResponse<CreateGroupData>> =
        MutableLiveData<BaseResponse<CreateGroupData>>()
    var updateGroupData: MutableLiveData<BaseResponse<UpdateGroupData>> =
        MutableLiveData<BaseResponse<UpdateGroupData>>()
    var deleteGroupData: MutableLiveData<BaseResponse<CreateGroupData>> =
        MutableLiveData<BaseResponse<CreateGroupData>>()
    var countGroupData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun getCreateData(name: String) {
        compositeDisposable.add(
            apiManager.getCreateGroups(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        createData.value = it
                        getGroupId(it.result.id)
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
                    getGroupId(it.result.id)
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
}