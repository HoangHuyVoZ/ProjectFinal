package com.example.projectfinal.ui.auth.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinal.R
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    var gender: String = "male"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        listenRegister()
    }

    private fun listenRegister() {
        authViewModel.signUpData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    progressBar2.invisible()
                } else {
                    progressBar2.invisible()
                    tv_error_Res.text = it.message
                    tv_error_Res.visible()
                }
            }
        })
    }

    private fun checkRegister() {
        val email = edtEmail_Res.text.toString()
        val pass = edtPass_Res.text.toString()
        val user = edtUser_Res.text.toString()
        val confirm = edtConfirm_Res.text.toString()
        if (!isEmailValid(email)) {
            edtEmail_Res.error = "Enter a valid email"
            if (!isPassValid(pass)) {
                edtPass_Res.error = "The password must contain 8 characters"
                if (!isPassValid(confirm)) {
                    edtConfirm_Res.error = "The password must contain 8 characters"
                    if (!isUerValid(user)) {
                        edtUser_Res.error = "Enter a valid username"
                        if (pass != confirm) {
                            edtConfirm_Res.error = "passwords must match"
                        }
                    }
                }
            }
        } else {
            progressBar2.visible()
            Toast.makeText(context, "Register successfully !", Toast.LENGTH_SHORT).show()
            authViewModel.getRegister(email, pass, user, gender)
        }
    }


    private fun init() {
        progressBar2.invisible()
        tv_error_Res.invisible()
        edtPass_Res.addTextChangedListener(loginTextChange)
        edtConfirm_Res.addTextChangedListener(loginTextChange)
        edtEmail_Res.addTextChangedListener(loginTextChange)
        edtUser_Res.addTextChangedListener(loginTextChange)
        val listSort = arrayOf("male", "female")
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                listSort
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        edtGender_Res.adapter = adapter
        edtGender_Res.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Display the selected item text on text view
                    gender = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        btnBack_Register.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        btnRes.setOnClickListener {
            it.hideKeyboard()
            checkRegister()
        }
    }

    private val loginTextChange: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val email: String = edtEmail_Res.text.toString().trim()
            val password: String = edtPass_Res.text.toString().trim()
            val username: String = edtUser_Res.text.toString().trim()
            val confirm: String = edtConfirm_Res.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirm.isNotEmpty()) {
                btnRes.isEnabled = true
                btnRes.setBackgroundResource(R.drawable.btn_login_checked)
            } else {
                btnRes.setBackgroundResource(R.drawable.btn_login)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }
}