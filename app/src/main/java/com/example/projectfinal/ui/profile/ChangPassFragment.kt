package com.example.projectfinal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinal.R
import com.example.projectfinal.utils.hideKeyboard
import com.example.projectfinal.utils.invisible
import com.example.projectfinal.utils.isPassValid
import com.example.projectfinal.utils.visible
import com.example.projectfinal.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_chang_pass.*


class ChangPassFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.fragment_chang_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        dataChange()
    }

    private fun dataChange() {
        authViewModel.userChangPass.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    edtOldPass.text.clear()
                    edtNewPass.text.clear()
                    edtReNewPass.text.clear()
                    tv_error_Res.visible()
                    tv_error_Res.text = it.message
                } else {
                    tv_error_Res.visible()
                    tv_error_Res.text = it.message
                }
            }
        })
    }

    private fun init() {
        tv_error_Res.invisible()
        btnBack_pass.setOnClickListener {
            findNavController().popBackStack()
        }
        btnChange.setOnClickListener {
            it.hideKeyboard()
            checkChangePass()
        }
    }

    private fun checkChangePass() {
        val old = edtOldPass.text.toString()
        val new = edtNewPass.text.toString()
        val reNew = edtReNewPass.text.toString()
        if (!isPassValid(old)) {
            edtOldPass.error = "The password must contain 8 characters"
        }
        if (!isPassValid(new)) {
            edtNewPass.error = "The password must contain 8 characters"
        }
        if (!isPassValid(reNew)) {
            edtReNewPass.error = "The password must contain 8 characters"
        } else if (isPassValid(old) && isPassValid(new) && isPassValid(reNew)) {
            authViewModel.getUserChangPass(old, new, reNew)
        }
    }

}