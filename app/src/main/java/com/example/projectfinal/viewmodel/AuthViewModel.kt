package com.example.projectfinal.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.Signup.Signup
import com.example.projectfinal.model.User.User
import com.example.projectfinal.model.login.Login
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val apiManager: CallApi by lazy { CallApi() }

    var loginData: MutableLiveData<Login> = MutableLiveData<Login>()
    var signUpData: MutableLiveData<Signup> = MutableLiveData<Signup>()
    var userData: MutableLiveData<User> = MutableLiveData<User>()


    fun getUserData(accesstoken : String) {
        compositeDisposable.add(
            apiManager.getUserInfo(accesstoken)
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
                    signUpData.value =it
                },{
                    signUpData.value =null

                })
        )
    }
}