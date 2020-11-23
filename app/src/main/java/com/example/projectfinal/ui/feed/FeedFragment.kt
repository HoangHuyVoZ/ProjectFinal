package com.example.projectfinal.ui.feed

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.FeedData
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.feed.adapter.FeedAdapter
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.firstTime
import com.example.projectfinal.utils.hideBottomNav
import com.example.projectfinal.utils.invisible
import com.example.projectfinal.utils.visible
import com.example.projectfinal.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


@Suppress("NAME_SHADOWING")
class FeedFragment : Fragment() {
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedAdapter
    private var position: Int? = 0
    private var idFeed: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? HomeActivity)?.hideBottomNav(false)
        swipeRefreshLayoutFeed.setOnRefreshListener {
            feedViewModel.getFeed()
        }
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
        adapter = FeedAdapter { select, position, item ->
            this.position = position
            idFeed = item.id

            when (select) {
                1 -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Warning !!!")
                    builder.setMessage("Do you want remove this group?")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        feedViewModel.getDeleteFeed(idFeed ?: "")
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, _which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                3 -> {
                    val bundle = bundleOf("id" to item.id)
                    findNavController().navigate(
                        R.id.action_feedFragment_to_feedDetailsFragment,
                        bundle
                    )
                }
                else -> {
                    val bundle = Bundle()
                    bundle.putParcelable("feed", item)
                    bundle.putString("update", "update")

                    findNavController().navigate(R.id.action_feedFragment_to_createFragment, bundle)
                }
            }
        }
        recyclerView.adapter = adapter
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dataFeed() {
        feedViewModel.dataDeleteFeed.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    adapter.remove(position ?: 0)
                    position = 0
                }
            }
        })
        feedViewModel.dataCountFeed.observe(viewLifecycleOwner, {
            tv_count.text = "$it feed"
        })
        feedViewModel.dataFeed.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    progressBar.invisible()
                    swipeRefreshLayoutFeed.isRefreshing= false
                    adapter.addList(it.result as MutableList<FeedData>)
                } else {
                    tv_error.visible()
                    tv_error.text = it.message
                }
            } else {
                tv_token.visible()
                tv_token.setOnClickListener {
                    context?.let { it -> firstTime(it, true) }
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


}