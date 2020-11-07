package com.example.projectfinal.ui.feed

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projectfinal.R
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed_details.*


class FeedDetailsFragment : Fragment() {
    lateinit var feedViewModel: FeedViewModel
    private var feedId = ""
    private var accessToken = ""
    private lateinit var pref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()
        feedId = pref.getString(FEED_ID, "").toString()
        init()
        getData()
    }

    private fun getData() {
        feedViewModel.getFeedID(accessToken,feedId)
    }

    private fun init() {
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

}