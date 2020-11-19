package com.example.projectfinal.ui.auth.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectfinal.R
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_hello.*


@Suppress("DEPRECATION")
class HelloFragment : Fragment() {
    private lateinit var  authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        return inflater.inflate(R.layout.fragment_hello, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE)
            authViewModel.getUserData()

        authViewModel.userData.observe(viewLifecycleOwner, {
            if(it!=null){
                if(it.success){
                    tv_username_hello.text = "Hi, \n${it.result.displayName}"
                    val editor = pref.edit()
                    editor.putString(USERNAME, it.result.displayName)
                    editor.apply()

                    Handler().postDelayed({
                       val intent= Intent(context, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }, 2000)
                }
            }
        })
    }



}