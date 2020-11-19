package com.example.projectfinal.ui.auth.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinal.R
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        listenLogin()
    }

    private fun init() {
        progressBar.invisible()
        tv_error_login.invisible()
        edtEmail_login.addTextChangedListener(loginTextChange)
        edtPass_login.addTextChangedListener(loginTextChange)
        btnSignup_login.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        btnLogin.setOnClickListener {
            it.hideKeyboard()
            checkLogin()
        }
}

    private fun listenLogin() {
        authViewModel.loginData.observe(viewLifecycleOwner, {
            if(it!=null){
                if(it.success){
                    val pref: SharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putBoolean(FIRST_TIME, false)
                    editor.putString(ACCESS_TOKEN,it.result.accessToken)
                    editor.putString(ROLE,it.result.role)
                    editor.putString(USERNAME_ID,it.result.userId)
                    editor.apply()
                    accessToken = it.result.accessToken
                    findNavController().navigate(R.id.action_loginFragment_to_helloFragment)
                    progressBar.invisible()
                }else{
                    tv_error_login.text =it.message
                    tv_error_login.visible()
                    progressBar.invisible()
                }
            }
        })
    }

    private fun checkLogin() {
        val email = edtEmail_login.text.toString()
        val pass = edtPass_login.text.toString()
        if (!isEmailValid(email)) {
            edtEmail_login.error = "Enter a valid email"
        }
        if (!isPassValid(pass)) {
            edtPass_login.error = "The password must contain 8 characters"
        } else if (isEmailValid(email) && isPassValid(pass)) {
            progressBar.visible()
            authViewModel.getLoginData(email,pass)
        }
    }

    private val loginTextChange: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val email: String = edtEmail_login.text.toString()
            val password: String = edtPass_login.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                btnLogin.isEnabled = true
                btnLogin.setBackgroundResource(R.drawable.btn_login_checked)
            } else {
                btnLogin.setBackgroundResource(R.drawable.btn_login)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

}