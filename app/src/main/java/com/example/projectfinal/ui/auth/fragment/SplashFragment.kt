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
import androidx.navigation.fragment.findNavController
import com.example.projectfinal.R
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.*
import kotlinx.android.synthetic.main.fragment_splash.*

@Suppress("DEPRECATION")
class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            val pref = requireContext().getSharedPreferences(
                PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            val fistTime = pref.getBoolean(FIRST_TIME, true)
            val name = pref.getString(USERNAME, "...")
            accessToken= pref.getString(ACCESS_TOKEN,"")
            if (fistTime) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                tv_username_splash.visible()
                tv_username_splash.text = "Hi, \n$name"
                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity?.finish()
            }
        }, 2000)
    }
}