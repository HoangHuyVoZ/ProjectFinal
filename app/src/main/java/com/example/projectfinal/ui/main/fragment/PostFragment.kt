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
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.PostAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.PostViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_post.*


@Suppress("DEPRECATION", "NAME_SHADOWING")
class PostFragment : Fragment() {
    private lateinit var postViewModel: PostViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter
    private var roleTopic: Int? = 0
    private var position: Int? = 0
    private var nameTopic: String? = ""
    private var idGroup: String? = ""
    private var idTopic: String? = ""
    private var idPost: String? = ""
    private var mAlertDialog: Dialog? = null
    private var title: String? = ""
    private var des: String? = ""
    private var isUpdate: Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        nameTopic = arguments?.getString("name").toString()
        idGroup = arguments?.getString("group_id").toString()
        idTopic = arguments?.getString("topic_id").toString()

        init()
        getPost()
        dataPost()
    }

    @SuppressLint("SetTextI18n")
    private fun dataPost() {
        postViewModel.countPostData.observe(viewLifecycleOwner, {
            tv_countPost.text = "Total: $it post"
        })
        postViewModel.postData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    val diffUtilCallback = PostDiffUtilCallback(adapter.getList(), it.result)
                    val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                    adapter.addList(it.result as MutableList<PostData>)
                    diffResult.dispatchUpdatesTo(adapter)
                    progressBar_Post.invisible()
                } else {
                    tv_error_Post.text = it.message
                    tv_error_Post.visible()
                }
            } else {
                tv_token_post.visible()
                tv_token_post.setOnClickListener {
                    context?.let { it -> firstTime(it, true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar_Post.invisible()
            }
        })
        postViewModel.postIdData.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    when (isUpdate) {
                        1 -> adapter.addItem(it.result[0])

                        2 -> adapter.updateItem(it.result[0], position ?: 0)
                    }
                }
            }
        })
        postViewModel.createPostData.observe(viewLifecycleOwner, {
            it.let {
                if (it.message != null) {
                    if (it.success) {
                        mAlertDialog?.dismiss()
                        isUpdate = 1

                        FancyToast.makeText(
                            context,
                            "Create post successfully",
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
                it.message = null
            }

        })
        postViewModel.updatePostData.observe(viewLifecycleOwner, {
            it.let {
                if (it.message != null) {
                    if (it.success) {
                        isUpdate = 2
                        mAlertDialog?.dismiss()
                        FancyToast.makeText(
                            context,
                            "Update post successfully",
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
                it.message = null
            }


        })
        postViewModel.deletePostData.observe(viewLifecycleOwner, {
            it.let {
                if (it.message != null) {
                    if (it.success) {
                        adapter.remove(position ?: 0)

                        FancyToast.makeText(
                            context,
                            "Delete post successfully",
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
                it.message = null
            }
        })
    }

    private fun getPost() {
        postViewModel.getPost(idGroup ?: "", idTopic ?: "")

    }

    private fun init() {

        tv_Post.text = nameTopic

        tv_token_post.invisible()
        progressBar_Post.visible()
        tv_error_Post.invisible()
        layoutManager = LinearLayoutManager(context)
        recyclerViewPost.setHasFixedSize(true)
        recyclerViewPost.layoutManager = layoutManager
        adapter = PostAdapter { select, position, item ->
            roleTopic = select
            this.position = position
            idPost = item.id

            when (roleTopic) {
                2 -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Warning !!!")
                    builder.setMessage("Do you want remove this Post?")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        postViewModel.getDeletePost(idGroup ?: "", idTopic ?: "", idPost ?: "")
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                3 -> {

                    val bundle = bundleOf(
                        "title" to item.title,
                        "group_id" to idGroup,
                        "topic_id" to idTopic,
                        "post_id" to idPost
                    )
                    findNavController().navigate(
                        R.id.action_postFragment_to_postDetailFragment,
                        bundle
                    )
                }
                else -> {
                    showDialog(item.title, item.description)
                }
            }
        }
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
        if (roleTopic == 0) {
            dialog.tv_title_topic.text = "Create post"
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_topic.setOnClickListener {
                title = dialog.edt_title_topic.text.toString()
                des = dialog.edt_des_topic.text.toString()
                it.hideKeyboard()
                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        postViewModel.getCreatePost(
                            idGroup ?: "",
                            idTopic ?: "",
                            title ?: "",
                            des ?: ""
                        )
                    }
                }
            }
        } else {
            dialog.tv_title_topic.text = "Update post"
            dialog.btnCancel_topic.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_topic.text = "update"
            dialog.edt_des_topic.setText(description)
            dialog.edt_title_topic.setText(name)
            dialog.btn_create_topic.setOnClickListener {
                title = dialog.edt_title_topic.text.toString()
                des = dialog.edt_des_topic.text.toString()
                it.hideKeyboard()
                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        postViewModel.getUpdatePost(
                            idGroup ?: "", idTopic ?: "", idPost ?: "",
                            title ?: "",
                            des ?: ""
                        )
                        mAlertDialog?.dismiss()
                    }
                }
            }
        }
    }


}
