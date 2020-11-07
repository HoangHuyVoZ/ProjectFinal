package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.Group.Groupdata
import com.example.projectfinal.model.Topic.TopicData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.GroupAdapter
import com.example.projectfinal.ui.main.adapter.TopicAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_group.*
import kotlinx.android.synthetic.main.fragment_topic.*


class TopicFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TopicAdapter
    private lateinit var accessToken: String
    private var roleGroup = 0
    private var idGroup = ""
    private var idTopic = ""
    private var role = ""
    private var nameGroup = ""
    private lateinit var pref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        role = pref.getString(ROLE, "").toString()
        nameGroup = pref.getString(NAME_GROUP, "").toString()
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()
        idGroup = pref.getString(GROUP_ID, "").toString()
        init()
        getTopic()
        data()
    }

    @SuppressLint("ShowToast", "SetTextI18n")
    private fun data() {
        mainViewModel.topicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    tv_countTopic.text = "Total: ${it.result.size} topic"
                    adapter.addList(it.result as MutableList<TopicData>)
                    progressBar_topic.invisible()


                } else {
                    tv_error_Topic.text = it.message
                    tv_error_Topic.visible()
                }
            } else {
                tv_token_topic.visible()
                tv_token_topic.setOnClickListener {
                    context?.let { it -> firstTime(it, true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar3.invisible()
            }
        })

        mainViewModel.createTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "create topic successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_topic.visible()
            mainViewModel.getTopic(accessToken, idGroup)
        })
        mainViewModel.updateTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "update topic successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_topic.visible()
            mainViewModel.getTopic(accessToken, idGroup)
        })
        mainViewModel.deleteTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "delete topic successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_topic.visible()
            mainViewModel.getTopic(accessToken, idGroup)
        })
    }


    private fun getTopic() {
        if (idGroup.isNotEmpty()) {
            mainViewModel.getTopic(accessToken, idGroup)
        }
    }

    private fun init() {

        tv_Topic.text = nameGroup
        if (role == MEMBER) {
            btnAdd_Topic.invisible()
        } else {
            btnAdd_Topic.visible()
        }
        tv_token_topic.invisible()
        progressBar_topic.visible()
        tv_error_Topic.invisible()
        layoutManager = LinearLayoutManager(context)
        recyclerViewTopic.setHasFixedSize(true)
        recyclerViewTopic.layoutManager = layoutManager
        adapter = TopicAdapter(this)
        recyclerViewTopic.adapter = adapter
        btnAdd_Topic.setOnClickListener {
            roleGroup = 0
            showDialog("", "")
        }
        btnBack_topic.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(name: String, des: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_topic_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        val mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog.window?.attributes = layoutParams
        if (roleGroup == 0) {
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_topic.setOnClickListener {
                val title = dialog.edt_title_topic.text.toString()
                val des = dialog.edt_des_topic.text.toString()

                when {
                    title.isEmpty() -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des.isEmpty() -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getCreateTopic(accessToken, idGroup, title, des)
                        mAlertDialog.dismiss()
                    }
                }
            }
        } else {
            dialog.tv_title_topic.text = "Update topic"
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_topic.text = "update"
            dialog.edt_des_topic.setText(des)
            dialog.edt_title_topic.setText(name)
            dialog.btn_create_topic.setOnClickListener {
                val title = dialog.edt_title_topic.text.toString()
                val des = dialog.edt_des_topic.text.toString()
                when {
                    title.isEmpty() -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des.isEmpty() -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getUpdateTopic(accessToken, idGroup, idTopic, title, des)
                        mAlertDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onClickItem(id: String, role: Int, name: String, description: String) {
        idTopic = id
        roleGroup = role
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this Topic?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    mainViewModel.getDeleteTopic(accessToken, idGroup, id)
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            4 -> {
                val pref: SharedPreferences = requireContext().getSharedPreferences(
                    PREFS_NAME,
                    AppCompatActivity.MODE_PRIVATE
                )
                val editor = pref.edit()
                editor.putString(TOPIC_ID, id)
                editor.putString(NAME_TOPIC, name)
                editor.apply()
                findNavController().navigate(R.id.action_topicFragment_to_postFragment)
            }
            else -> {
                showDialog(name, description)
            }
        }
    }


}