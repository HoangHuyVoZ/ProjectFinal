package com.example.projectfinal.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE)
        val accessToken = pref.getString(ACCESS_TOKEN, "")
        if (accessToken != null) {
            authViewModel.getUserData(accessToken)
        }
        authViewModel.userData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.success){
                    tv_username_hello.text = "Hi, \n${it.result.displayName}"
                    val editor = pref.edit()
                    editor.putString(USERNAME, it.result.displayName)
                    editor.apply()

                    Handler().postDelayed({
                       val intent= Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                    }, 2000)
                }
            }
        })
    }



}