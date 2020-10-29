package com.example.projectfinal.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        var fistTime = pref.getBoolean(FIRST_TIME, true)
        var name = pref.getString(USERNAME, "...")
        if (fistTime) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            tv_username_splash.visible()
            tv_username_splash.text="Hi, $name"
            val intent = Intent(context,HomeActivity::class.java)
            startActivity(intent)
        }
        }, 2000)
    }
}