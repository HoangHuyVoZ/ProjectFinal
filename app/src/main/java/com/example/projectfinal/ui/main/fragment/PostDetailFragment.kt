package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.ui.main.adapter.CommentAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_feed_details.*
import kotlinx.android.synthetic.main.fragment_post_detail.*
import kotlinx.android.synthetic.main.fragment_post_detail.btnSend
import kotlinx.android.synthetic.main.fragment_post_detail.edt_comment


class PostDetailFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CommentAdapter
    private var roleComment: Int? = 0
    private var nameTopic: String? = ""
    private var idGroup: String? = ""
    private var idTopic: String? = ""
    private var idPost: String? = ""
    private var idComment: String? = ""
    private var des: String? = ""
    private var position: Int? = 0
    private var isUpdate: Int? = 0
    private var mAlertDialog: Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        nameTopic = arguments?.getString("name").toString()
        idGroup = arguments?.getString("group_id").toString()
        idTopic = arguments?.getString("topic_id").toString()
        idPost = arguments?.getString("post_id").toString()

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
        mainViewModel.countCommentData.observe(viewLifecycleOwner, {
            tv_comment_count.text = "$it Comment"

        })
        mainViewModel.commentData.observe(viewLifecycleOwner, {
            if (it != null) {

                val diffUtilCallback = CommentDiffUtilCallback(adapter.getList(), it.result)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                adapter.addList(it.result as MutableList<commentData>)
                diffResult.dispatchUpdatesTo(adapter)
            }
        })
        mainViewModel.commentIdData.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    when (isUpdate) {
                        1 -> {
                            adapter.addItem(it.result[0])
                            tv_no_comment.invisible()
                            tv_comment_count.visible()
                        }
                        2 -> adapter.updateItem(it.result[0], position ?: 0)
                    }
                }
            }
        })
        mainViewModel.createComment.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    isUpdate = 1
                    FancyToast.makeText(
                        context, it.message, FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context, it.message, FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR, false
                    ).show()
                }
            }

        })
        mainViewModel.updateComment.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    isUpdate = 2
                    FancyToast.makeText(
                        context, it.message, FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context, it.message, FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR, false
                    ).show()
                }
            }
        })
        mainViewModel.deleteComment.observe(viewLifecycleOwner,
            {
                it.let {
                    if (it.success) {

                        adapter.remove(position ?: 0)
                        FancyToast.makeText(
                            context, it.message, FancyToast.LENGTH_SHORT,
                            FancyToast.SUCCESS, false
                        ).show()

                    } else {
                        FancyToast.makeText(
                            context, it.message, FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR, false
                        ).show()
                    }
                }
            })
    }

    private fun init() {
        btnBack_comment.setOnClickListener {
            findNavController().popBackStack()
        }
        layoutManager = LinearLayoutManager(context)
        recyclerViewComment.setHasFixedSize(true)
        recyclerViewComment.layoutManager = layoutManager
        adapter = CommentAdapter { select, position, item ->
            roleComment = select
            this.position = position
            idComment = item.id

            when (roleComment) {
                2 -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Warning !!!")
                    builder.setMessage("Do you want remove this Comment?")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        mainViewModel.getDeleteComment(
                            idGroup ?: "",
                            idTopic ?: "",
                            idPost ?: "",
                            item.id ?: ""
                        )
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                else -> {
                    showDialog(item.description)
                }
            }
        }
        recyclerViewComment.adapter = adapter
        recyclerViewComment.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        btnSend.setOnClickListener {
            if (edt_comment.text.toString().length >= 3) {
                it.hideKeyboard()
                mainViewModel.getCreateComment(
                    idGroup ?: "", idTopic ?: "", idPost ?: "",
                    edt_comment.text.toString()
                )
                edt_comment.text.clear()
            } else {
                edt_comment.error = "comment is minimum 3 characters"
            }

        }

    }

    private fun getPostID() {
        mainViewModel.getPostId(idGroup ?: "", idTopic ?: "", idPost ?: "")
        mainViewModel.getComment(idGroup ?: "", idTopic ?: "", idPost ?: "")
    }


    private fun showDialog(description: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_group_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog?.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog?.window?.attributes = layoutParams
        dialog.tv_title.text = "Update comment"
        dialog.btnCancel.setOnClickListener {
            mAlertDialog?.dismiss()
        }
        dialog.btn_create_group.text = "update"
        dialog.edt_title_create.setText(description)
        dialog.btn_create_group.setOnClickListener {
            it.hideKeyboard()
            des = dialog.edt_title_create.text.toString()
            when {
                des?.isEmpty()!! -> {
                    dialog.edt_des_topic.error = "You have not entered a description"
                }
                else -> {
                    mainViewModel.getUpdateComment(
                        idGroup ?: "", idTopic ?: "", idPost ?: "", idComment ?: "", des ?: ""
                    )
                    mAlertDialog?.dismiss()
                }
            }
        }
    }
}