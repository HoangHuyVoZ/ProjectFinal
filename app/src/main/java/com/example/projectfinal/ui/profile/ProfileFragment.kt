package com.example.projectfinal.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinal.R
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.utils.PREFS_NAME
import com.example.projectfinal.utils.ROLE
import com.example.projectfinal.utils.USERNAME
import com.example.projectfinal.utils.firstTime
import com.example.projectfinal.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


@Suppress("NAME_SHADOWING")
class ProfileFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )

        tv_username_pro.text = pref.getString(USERNAME, "")
        tv_role_pro.text = pref.getString(ROLE, "")

        logout.setOnClickListener {
            context?.let { it -> firstTime(it, true) }
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        changepass.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changPassFragment)
        }
    }


}