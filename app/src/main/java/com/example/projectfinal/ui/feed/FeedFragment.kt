package com.example.projectfinal.ui.feed

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.comment.commentData
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.feed.adapter.FeedAdapter
import com.example.projectfinal.ui.main.adapter.CommentAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.tv_token
import kotlinx.android.synthetic.main.fragment_group.*
import kotlinx.android.synthetic.main.fragment_post_detail.*
import kotlinx.android.synthetic.main.fragment_post_detail.recyclerViewComment


class FeedFragment : Fragment() ,ClickItem{
    lateinit var feedViewModel: FeedViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedAdapter
    private lateinit var pref: SharedPreferences
    private lateinit var accessToken: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()

        init()
        getFeed()
        dataFeed()
    }

    private fun dataFeed() {
        feedViewModel.dataFeed.observe(viewLifecycleOwner,{
            if(it!=null){
                if(it.success){
                    progressBar.invisible()
                    tv_count.text = "${it.result.size} feed"
                    adapter.addList(it.result as MutableList<feedData>)
                }else{
                    tv_error.visible()
                    tv_error.text= it.message
                }
            }else{
                tv_token.visible()
                tv_token.setOnClickListener {
                    context?.let { it -> firstTime(it,true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar.invisible()

            }
        })
    }

    private fun getFeed() {
        feedViewModel.getFeed(accessToken)
    }

    private fun init() {
        tv_token.invisible()
        progressBar.visible()
        tv_error.invisible()
        layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        adapter = FeedAdapter(this)
        recyclerView.adapter = adapter
    }

    override fun onClickItem(id: String, role: Int, name: String, description: String) {
        val pref: SharedPreferences = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putString(FEED_ID, id)
            findNavController().navigate(R.id.action_feedFragment_to_feedDetailsFragment)
    }

}