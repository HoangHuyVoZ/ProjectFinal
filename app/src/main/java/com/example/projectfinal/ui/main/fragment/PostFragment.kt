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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.PostAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter
    private lateinit var accessToken: String
    private var roleTopic = 0
    private var idGroup = ""
    private var idTopic = ""
    private var role = ""
    private var nameTopic = ""
    private var idPost = ""
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.fragment_post, container, false)
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
        init()
        getPost()
        dataPost()
    }

    private fun dataPost() {
        mainViewModel.postData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    tv_countPost.text = "Total: ${it.result.size} post"
                    adapter.clear()
                    adapter.addList(it.result as MutableList<PostData>)
                    adapter.notifyDataSetChanged()
                    progressBar_Post.invisible()
                } else {
                    tv_error_Post.text = it.message
                    tv_error_Post.visible()
                }
            } else {
                tv_token_post.visible()
                tv_token_post.setOnClickListener {
                    context?.let { it -> firstTime(it,true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar_Post.invisible()
            }
        })
        mainViewModel.createPostData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "create post successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_Post.visible()
            adapter.clear()
            mainViewModel.getPost(accessToken, idGroup,idTopic)

        })
        mainViewModel.updatePostData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "update post successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_Post.visible()
            adapter.clear()
            mainViewModel.getPost(accessToken, idGroup,idTopic)
        })
        mainViewModel.deletePostData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "delete post successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar_Post.visible()
            adapter.clear()
            mainViewModel.getPost(accessToken, idGroup,idTopic)

        })
    }

    private fun getPost() {
        if (idTopic.isNotEmpty()) {
            mainViewModel.getPost(accessToken, idGroup,idTopic)
        }
    }
    private fun init() {
        tv_Post.text = nameTopic

        tv_token_post.invisible()
        progressBar_Post.visible()
        tv_error_Post.invisible()
        layoutManager = LinearLayoutManager(context)
        recyclerViewPost.setHasFixedSize(true)
        recyclerViewPost.layoutManager = layoutManager
        adapter = PostAdapter(this)
        recyclerViewPost.adapter = adapter
        btnAdd_Post.setOnClickListener {
            roleTopic = 0
            showDialog("", "")
        }
        btnBack_Post.setOnClickListener {
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
        if (roleTopic == 0) {
            dialog.tv_title_topic.text = "Create post"
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
                        mainViewModel.getCreatePost(accessToken, idGroup, idTopic, title, des)
                        mAlertDialog.dismiss()
                    }
                }
            }
        } else {
            dialog.tv_title_topic.text = "Update post"
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
                        mainViewModel.getUpdatePost(
                            accessToken,
                            idGroup,
                            idTopic,
                            idPost,
                            title,
                            des
                        )
                        mAlertDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onClickItem(id: String, role: Int, name: String, description: String) {
        idPost = id
        roleTopic = role
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this Post?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    mainViewModel.getDeletePost(accessToken, idGroup, idTopic, idPost)
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
                showDialog(name, description)
            }
        }
    }


}
