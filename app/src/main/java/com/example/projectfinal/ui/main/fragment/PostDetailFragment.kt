package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.ui.main.adapter.CommentAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_post_detail.*


class PostDetailFragment : Fragment(), ClickItem {
    lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CommentAdapter
    private lateinit var accessToken: String
    private var roleComment = 0
    private var idGroup = ""
    private var idTopic = ""
    private var role = ""
    private var nameTopic = ""
    private var idPost = ""
    private var username = ""
    private var idComment = ""

    private lateinit var pref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        role = pref.getString(ROLE, "").toString()
        nameTopic = pref.getString(NAME_TOPIC, "").toString()
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()
        idGroup = pref.getString(GROUP_ID, "").toString()
        idTopic = pref.getString(TOPIC_ID, "").toString()
        idPost = pref.getString(POST_ID, "").toString()
        username = pref.getString(USERNAME, "").toString()

        init()
        getPostID()
        postIdData()
    }

    @SuppressLint("SetTextI18n")
    private fun postIdData() {
        mainViewModel.postIdData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    tv_profile_comment.text = it.result[0].createdBy
                    tv_comment2.text = it.result[0].title
                    tv_countLike_com.text = it.result[0].countLike.toString()
                    tv_des_comment.text = it.result[0].description
                    if (it.result[0].countCommentPost <= 0) {
                        tv_no_comment.visible()
                        tv_comment_count.invisible()
                    } else {
                        tv_no_comment.invisible()
                        tv_comment_count.visible()
                    }
                }
            }
        })
        mainViewModel.commentData.observe(viewLifecycleOwner, {
            if (it != null) {
                tv_comment_count.text = "${it.result.size} Comment"
                adapter.clear()
                adapter.addList(it.result as MutableList<commentData>)
                adapter.notifyDataSetChanged()
            }
        })
        mainViewModel.createComment.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            adapter.clear()
            mainViewModel.getComment(accessToken, idGroup, idTopic, idPost)
            mainViewModel.getPostId(accessToken, idGroup, idTopic, idPost)
        })
        mainViewModel.updateComment.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            adapter.clear()
            mainViewModel.getComment(accessToken, idGroup, idTopic, idPost)
        })
        mainViewModel.deleteComment.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            adapter.clear()
            mainViewModel.getComment(accessToken, idGroup, idTopic, idPost)
        })
    }

    private fun init() {
        btnBack_comment.setOnClickListener {
            findNavController().popBackStack()
        }
        layoutManager = LinearLayoutManager(context)
        recyclerViewComment.setHasFixedSize(true)
        recyclerViewComment.layoutManager = layoutManager
        adapter = CommentAdapter(this)
        recyclerViewComment.adapter = adapter
        recyclerViewComment.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        btnSend.setOnClickListener {
            if(edt_comment.text.toString().length>=3){
                mainViewModel.getCreateComment(
                    accessToken,
                    idGroup,
                    idTopic,
                    idPost,
                    edt_comment.text.toString()
                )
                edt_comment.text.clear()
            }else{
                edt_comment.error= "comment is minimum 3 characters"
            }

        }

    }

    private fun getPostID() {
        if (idTopic.isNotEmpty()) {
            mainViewModel.getPostId(accessToken, idGroup, idTopic, idPost)
            mainViewModel.getComment(accessToken, idGroup, idTopic, idPost)
        }
    }

    override fun onClickItem(id: String, role: Int, name: String, description: String) {
        idComment = id
        roleComment = role
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this Comment?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    mainViewModel.getDeleteComment(accessToken, idGroup, idTopic, idPost, id)
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
                editor.putString(POST_ID, id)
                editor.apply()
                findNavController().navigate(R.id.action_postFragment_to_postDetailFragment)
            }
            else -> {
                showDialog(description)
            }
        }
    }

    private fun showDialog(description: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_group_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        val mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog.window?.attributes = layoutParams
        dialog.tv_title.text = "Update comment"
        dialog.btnCancel_topic.setOnClickListener {
            mAlertDialog.dismiss()
        }
        dialog.btn_create_group.text = "update"
        dialog.edt_title_create.setText(description)
        dialog.btn_create_group.setOnClickListener {
            val des = dialog.edt_title_create.text.toString()
            when {
                des.isEmpty() -> {
                    dialog.edt_des_topic.error = "You have not entered a description"
                }
                else -> {
                    mainViewModel.getUpdateComment(
                        accessToken,
                        idGroup,
                        idTopic,
                        idPost,
                        idComment,
                        des
                    )
                    mAlertDialog.dismiss()
                }
            }
        }
    }
}