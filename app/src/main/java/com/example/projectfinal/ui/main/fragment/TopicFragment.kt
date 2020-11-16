package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.topic.TopicData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.TopicAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_group.*
import kotlinx.android.synthetic.main.fragment_topic.*


class TopicFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TopicAdapter
    private var roleGroup: Int? = 0
    private var isUpdate: Int? = 0
    private var role: String? = ""
    private var nameGroup: String? = ""
    private var mAlertDialog: Dialog? = null
    private var title: String? = ""
    private var des: String? = ""
    private var position: Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        nameGroup = arguments?.getString("name").toString()
        init()
        getTopic()
        data()
    }

    @SuppressLint("ShowToast", "SetTextI18n")
    private fun data() {
        mainViewModel.countTopicData.observe(viewLifecycleOwner, {
            tv_countTopic.text = "Total: $it topic"

        })
        mainViewModel.topicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    val diffUtilCallback = TopicDiffUtilCallback(adapter.getList(), it.result)
                    val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                    adapter.addList(it.result as MutableList<TopicData>)
                    diffResult.dispatchUpdatesTo(adapter)
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
        mainViewModel.topicDataId.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    when(isUpdate){
                        1 -> adapter.addItem(it.result[0])

                        2 -> adapter.updateItem(it.result[0], position ?: 0)
                    }
                }
            }

        })
        mainViewModel.createTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    isUpdate = 1
                    FancyToast.makeText(
                        context,
                        "create topic successfully",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context,
                        it.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }

        })
        mainViewModel.updateTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    isUpdate = 2
                    FancyToast.makeText(
                        context,
                        "Update topic successfully",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context,
                        it.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }

        })
        mainViewModel.deleteTopicData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    adapter.remove(position?:0)
                    FancyToast.makeText(
                        context,
                        "Delete topic successfully",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context,
                        it.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }

        })
    }


    private fun getTopic() {
        mainViewModel.getTopic()

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
    private fun showDialog(name: String, description: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_topic_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog?.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog?.window?.attributes = layoutParams
        if (roleGroup == 0) {
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_topic.setOnClickListener {
                title = dialog.edt_title_topic.text.toString()
                des = dialog.edt_des_topic.text.toString()

                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getCreateTopic(title ?: "", des ?: "")
                    }
                }
            }
        } else {
            dialog.tv_title_topic.text = "Update topic"
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_topic.text = "update"
            dialog.edt_des_topic.setText(description)
            dialog.edt_title_topic.setText(name)
            dialog.btn_create_topic.setOnClickListener {
                title = dialog.edt_title_topic.text.toString()
                des = dialog.edt_des_topic.text.toString()
                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getUpdateTopic(title ?: "", des ?: "")
                    }
                }
            }
        }
    }

    override fun onClickItem(
        id: String,
        role: Int,
        name: String,
        description: String,
        positionn: Int
    ) {
        topicId = id
        roleGroup = role
        position = positionn

        when (role) {
            4 -> {
                val bundle = bundleOf("name" to name)
                findNavController().navigate(R.id.action_topicFragment_to_postFragment, bundle)
            }
            else -> {
                showDialog(name, description)
            }
        }
    }

    override fun onRemoveClick(positionn: Int, id: String) {
        position = positionn
        topicId = id
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Warning !!!")
        builder.setMessage("Do you want remove this Topic?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            mainViewModel.getDeleteTopic()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


}