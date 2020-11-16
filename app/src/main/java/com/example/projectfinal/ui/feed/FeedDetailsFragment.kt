package com.example.projectfinal.ui.feed

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.feedCommentData
import com.example.projectfinal.ui.feed.adapter.FeedCommentAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.create_topic_dialog.view.*
import kotlinx.android.synthetic.main.fragment_feed_details.*


class FeedDetailsFragment : Fragment(), ClickItem {
    lateinit var feedViewModel: FeedViewModel
    private var feedId = ""
    private var accessToken = ""
    private lateinit var pref: SharedPreferences
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        getData()
        data()
    }


    private fun init() {
        btnSend.setOnClickListener {
            it.hideKeyboard()
            if (edt_comment.text.toString().length >= 3) {
                feedViewModel.getCreateFeed( edt_comment.text.toString())
                edt_comment.text.clear()
            }
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        layoutManager = LinearLayoutManager(context)
        recyclerViewCommentFeed.setHasFixedSize(true)
        recyclerViewCommentFeed.layoutManager = layoutManager
        adapter = FeedCommentAdapter(this)
        recyclerViewCommentFeed.adapter = adapter


    }

    private fun data() {
        feedViewModel.dataUpdateComment.observe(viewLifecycleOwner, {
            it.let {
                feedViewModel.getFeedComment()
            }
        })
        feedViewModel.dataCreateComment.observe(viewLifecycleOwner, {
            it.let {
                feedViewModel.getFeedComment()
            }
        })
        feedViewModel.dataDeleteComment.observe(viewLifecycleOwner, {
            it.let {
                feedViewModel.getFeedComment()
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
                tv_comment_count_feed.text = "${it.result.size.toString()} comment"
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
        feedViewModel.getFeedID()
        feedViewModel.getFeedComment()
    }

    override fun onClickItem(
        id: String,
        role: Int,
        name: String,
        description: String,
        position: Int
    ) {
        commentId=id
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this Comment?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    feedViewModel.getDeleteFeedComment ()
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            else -> {
                showDialog(description,id)
            }
        }
    }



    override fun onRemoveClick(position: Int, id: String) {

    }

    private fun showDialog(description: String,comment_id :String) {
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
        dialog.btnCancel.setOnClickListener {
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
                    feedViewModel.getUpdateFeedComment(
                        des
                    )
                    mAlertDialog.dismiss()
                }
            }
        }
    }


}