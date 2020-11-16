package com.example.projectfinal.ui.feed

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.feed.adapter.FeedAdapter
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.FeedViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.tv_token


class FeedFragment : Fragment() ,ClickItem{
    lateinit var feedViewModel: FeedViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedAdapter
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ( activity as? HomeActivity)?.hideBottomNav(false)

        init()
        getFeed()
        dataFeed()
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
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createFragment)
        }
    }
    private fun dataFeed() {

        feedViewModel.dataCountFeed.observe(viewLifecycleOwner,{
            tv_count.text = "$it feed"
        })
        feedViewModel.dataFeed.observe(viewLifecycleOwner,{
            if(it!=null){
                if(it.success){
                    progressBar.invisible()
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
        feedViewModel.getFeed()
    }



    override fun onClickItem(
        id: String,
        role: Int,
        name: String,
        description: String,
        positionn: Int
    ) {
        feedId=id
        position= positionn
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this Feed?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    feedViewModel.getDeleteFeed()
                    feedViewModel.dataDeleteFeed.observe(viewLifecycleOwner,{
                        if(it.success){
                            adapter.remove(position)
                            FancyToast.makeText(
                                context, it.message, FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false
                            ).show()
                        }else{
                            FancyToast.makeText(
                                context, it.message, FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false
                            ).show()
                        }
                    })
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            4 -> {
//                val bundle = bundleOf("feed_id" to id)
                findNavController().navigate(R.id.action_feedFragment_to_feedDetailsFragment)
            }
        }

    }

    override fun onRemoveClick(position: Int, id: String) {
    }

}