package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.login.LoginData
import com.example.projectfinal.model.signup.SignUpData
import com.example.projectfinal.model.user.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val apiManager: CallApi by lazy { CallApi() }

    var loginData: MutableLiveData<BaseResponse<LoginData>> =
        MutableLiveData<BaseResponse<LoginData>>()
    var signUpData: MutableLiveData<BaseResponse<SignUpData>> =
        MutableLiveData<BaseResponse<SignUpData>>()
    var userData: MutableLiveData<BaseResponse<UserData>> = MutableLiveData<BaseResponse<UserData>>()
    var userChangPass: MutableLiveData<BaseResponse<UserData>> = MutableLiveData<BaseResponse<UserData>>()

    fun getUserChangPass(old: String, new: String, renew: String) {
        compositeDisposable.add(
            apiManager.getUserChangePass(
                old, new, renew
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userChangPass.value = it
                }, {
                    userChangPass.value = null
                })

        )

    }

    fun getUserData() {
        compositeDisposable.add(
            apiManager.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userData.value = it
                }, {
                    userData.value = null
                })
        )
    }

    fun getLoginData(email: String, password: String) {
        compositeDisposable.add(
            apiManager.getLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginData.value = it
                }, {
                    loginData.value = null
                })
        )
    }

    fun getRegister(email: String, password: String, username: String, gender: String) {
        compositeDisposable.add(
            apiManager.getSignUp(email, password, username, gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    signUpData.value = it
                }, {
                    signUpData.value = null

                })
        )
    }
}