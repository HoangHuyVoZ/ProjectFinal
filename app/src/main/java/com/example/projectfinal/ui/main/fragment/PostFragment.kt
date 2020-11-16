package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.post.CreatePost
import com.example.projectfinal.model.post.CreatePostData
import com.example.projectfinal.model.post.PostData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.PostAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter
    private var roleTopic:Int?  = 0
    private var position :Int? = 0
    private var nameTopic :String? = ""
    private var mAlertDialog: Dialog? = null
    private var title :String? = ""
    private var des :String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        nameTopic = arguments?.getString("name").toString()
        init()
        getPost()
        dataPost()
    }

    private fun dataPost() {
        mainViewModel.countPostData.observe(viewLifecycleOwner,{
            tv_countPost.text = "Total: $it post"
        })
        mainViewModel.postData.observe(viewLifecycleOwner, {
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
        mainViewModel.createPostData.observe(viewLifecycleOwner, { it ->
            if (it != null) {
                if (it.success && it.message.isNotEmpty()) {
                    mAlertDialog?.dismiss()
                    val postData= PostData(arrayListOf(),0,0,"",username?:"",des?:"",it.result.id,title?:"")
                    adapter.addItem(postData)
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
        })
        mainViewModel.updatePostData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    val postData= PostData(arrayListOf(),0,0,"",username?:"",des?:"",it.result.id,title?:"")
                    adapter.updateItem(postData,position?:0)
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

        })
        mainViewModel.deletePostData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    adapter.remove(position?:0)
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


        })
    }

    private fun getPost() {
        if (topicId?.isNotEmpty()!!) {
            mainViewModel.getPost()
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

                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getCreatePost( title?:"", des?:"")
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
                when {
                    title?.isEmpty()!! -> {
                        dialog.edt_title_topic.error = "You have not entered a title"
                    }
                    des?.isEmpty()!! -> {
                        dialog.edt_des_topic.error = "You have not entered a descripttion"
                    }
                    else -> {
                        mainViewModel.getUpdatePost(
                            title?:"",
                            des?:""
                        )
                        mAlertDialog?.dismiss()
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
        postId = id
        roleTopic = role
        position= positionn
        when (role) {
            4 -> {

                val bundle = bundleOf(
                    "name" to name
                )
                findNavController().navigate(R.id.action_postFragment_to_postDetailFragment, bundle)
            }
            else -> {
                showDialog(name, description)
            }
        }
    }

    override fun onRemoveClick(positionn: Int, id: String) {
        position= positionn
        postId=id
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Warning !!!")
        builder.setMessage("Do you want remove this Post?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            mainViewModel.getDeletePost()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


}
