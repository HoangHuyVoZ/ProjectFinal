package com.example.projectfinal.ui.feed

import android.app.AlertDialog
import android.app.Dialog
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.model.BaseResponse
import com.example.projectfinal.model.feed.createFeedDataComment
import com.example.projectfinal.model.feed.feedCommentData
import com.example.projectfinal.ui.feed.adapter.FeedCommentAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.FeedViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_feed_details.*


class FeedDetailsFragment : Fragment() {
    lateinit var feedViewModel: FeedViewModel
    private var idFeed :String? = ""
    private var idComment :String? = ""
    private var position :Int? = 0
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedCommentAdapter
    private var mAlertDialog : Dialog?= null
    private var isUpdate : Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idFeed = arguments?.getString("id").toString()
        init()
        getData()
        data()
    }


    private fun init() {
        btnSend.setOnClickListener {
            it.hideKeyboard()
            if (edt_comment.text.toString().length >= 3) {
                feedViewModel.getCreateFeedComment(idFeed?:"", edt_comment.text.toString())
                edt_comment.text.clear()
            }
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        layoutManager = LinearLayoutManager(context)
        recyclerViewCommentFeed.setHasFixedSize(true)
        recyclerViewCommentFeed.layoutManager = layoutManager
        adapter = FeedCommentAdapter{
            select, position, item ->
            this.position= position
            idComment= item.id

            when(select){
                2->{
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Warning !!!")
                    builder.setMessage("Do you want remove this Comment?")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        feedViewModel.getDeleteFeedComment (idFeed?:"",idComment?:"")
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()

                }
                1->{
                    showDialog(item.description,item.id)

                }
            }
        }
        recyclerViewCommentFeed.adapter = adapter


    }

    private fun data() {
        feedViewModel.dataUpdateComment.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    isUpdate = 2
                    mAlertDialog?.dismiss()
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
        feedViewModel.dataCreateComment.observe(viewLifecycleOwner, {
            it.let {
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
        feedViewModel.dataDeleteComment.observe(viewLifecycleOwner, {
            it.let {
                if(it.success){
                    adapter.remove(position?:0)
                    Toast.makeText(context,"delete successfully",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"delete fail",Toast.LENGTH_SHORT).show()
                }
            }
        })
        feedViewModel.dataFeedCommentID.observe(viewLifecycleOwner, {
            it.let {
                it.let {
                    if (it.success) {
                        when (isUpdate) {
                            1 ->{
                                adapter.addItem(it.result[0])
                                tv_no_comment_feed.invisible()
                                tv_comment_count_feed.visible()
                            }


                            2 -> adapter.updateItem(it.result[0], position ?: 0)
                        }
                    }
                }
            }
        })
        feedViewModel.dataCountComment.observe(viewLifecycleOwner,{
            it.let {
                tv_comment_count_feed.text = "$it comment"
            }
        })
        feedViewModel.dataComment.observe(viewLifecycleOwner, {
            it.let {
                if (it.result.isEmpty()) {
                    tv_no_comment_feed.visible()
                    tv_comment_count_feed.invisible()
                } else {
                    tv_no_comment_feed.invisible()
                    tv_comment_count_feed.visible()
                }
                adapter.addList(it.result as MutableList<feedCommentData>)

            }
        })
        feedViewModel.dataFeedID.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    tv_user.text = it.result[0].createdBy
                    tv_des.text = it.result[0].description
                    when (it.result[0].attachments.size) {
                        0 -> {
                            image1_layout3.invisible()
                            image2_layout3.invisible()
                            image_layout2.invisible()
                            image2_layout2.invisible()
                            tv_count_image.invisible()
                            image_layout1.invisible()
                            cardView.invisible()
                        }
                        1 -> {
                            image1_layout3.invisible()
                            image2_layout3.invisible()
                            image_layout2.invisible()
                            image2_layout2.invisible()
                            tv_count_image.invisible()
                            image_layout1.visible()
                            cardView.visible()
                            Glide.with(this)
                                .load(it.result[0].attachments[0])
                                .into(image_layout1)
                        }
                        2 -> {
                            image_layout1.invisible()
                            image1_layout3.invisible()
                            image2_layout3.invisible()
                            tv_count_image.invisible()
                            image_layout2.visible()
                            image2_layout2.visible()
                            cardView.visible()

                            Glide.with(this)
                                .load(it.result[0].attachments[0])
                                .into(image_layout2)
                            Glide.with(this)
                                .load(it.result[0].attachments[1])
                                .into(image2_layout2)
                        }
                        3 -> {
                            image_layout1.invisible()
                            tv_count_image.invisible()
                            image2_layout2.invisible()
                            image_layout2.visible()
                            cardView.visible()
                            image1_layout3.visible()
                            image2_layout3.visible()

                            Glide.with(this)
                                .load(it.result[0].attachments[0])
                                .into(image_layout2)
                            Glide.with(this)
                                .load(it.result[0].attachments[1])
                                .into(image1_layout3)
                            Glide.with(this)
                                .load(it.result[0].attachments[2])
                                .into(image2_layout3)
                        }
                        else -> {
                            image_layout1.invisible()
                            image2_layout2.invisible()
                            image_layout2.visible()
                            cardView.visible()
                            image1_layout3.visible()
                            image2_layout3.visible()
                            tv_count_image.visible()
                            tv_count_image.text = "+ ${it.result[0].attachments.size - 3}"
                            Glide.with(this)
                                .load(it.result[0].attachments[0])
                                .into(image_layout2)
                            Glide.with(this)
                                .load(it.result[0].attachments[1])
                                .into(image1_layout3)
                            Glide.with(this)
                                .load(it.result[0].attachments[2])
                                .into(image2_layout3)
                        }
                    }


                }
            }
        })
    }

    private fun getData() {
        feedViewModel.getFeedID(idFeed?:"")
        feedViewModel.getFeedComment(idFeed?:"")
    }






    private fun showDialog(description: String,id: String) {
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
            val des = dialog.edt_title_create.text.toString()
            when {
                des.isEmpty() -> {
                    dialog.edt_des_topic.error = "You have not entered a description"
                }
                else -> {
                    feedViewModel.getUpdateFeedComment(idFeed?:"",id, des)
                }
            }
        }
    }


}