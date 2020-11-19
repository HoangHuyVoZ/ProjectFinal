package com.example.projectfinal.ui.feed

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.feedData
import com.example.projectfinal.ui.feed.adapter.FeedCreateAdapter
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.hideBottomNav
import com.example.projectfinal.utils.hideKeyboard
import com.example.projectfinal.utils.invisible
import com.example.projectfinal.utils.visible
import com.example.projectfinal.viewmodel.FeedViewModel
import com.google.firebase.storage.FirebaseStorage
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*


class CreateFragment : Fragment() {
    private val RESULT_LOAD_IMAGE = 1
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedCreateAdapter
    private var imageUri: ArrayList<Uri>? = null
    private var update: String? = ""
    private var feed: feedData? = null
    private var image: ArrayList<String>? = arrayListOf()

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProvider(this)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? HomeActivity)?.hideBottomNav(true)
        feed = arguments?.getParcelable("feed")
        update = arguments?.getString("update")
        image = feed?.attachments as ArrayList<String>?
        imageUri = arrayListOf()
        for (item in image ?: arrayListOf()) {
            imageUri?.add(item.toUri())
        }
        Log.d("woo", imageUri.toString())
        init()
        data()
    }

    private fun data() {
        feedViewModel.dataCreateFeed.observe(viewLifecycleOwner, {
            if (it.success) {
                edt_status.text.clear()
                adapter.clear()
                progressBar.invisible()
                FancyToast.makeText(
                    context,
                    it.message,
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
        })
        feedViewModel.dataUpdateFeed.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    edt_status.text.clear()
                    adapter.clear()
                    progressBar.invisible()
                    FancyToast.makeText(
                        context,
                        it.message,
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

    @SuppressLint("SetTextI18n")
    private fun init() {
        adapter = FeedCreateAdapter()
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recycler_image.setHasFixedSize(true)
        recycler_image.layoutManager = layoutManager
        recycler_image.adapter = adapter

        progressBar.invisible()
        if (update == "update") {
            textView4.text = "Update feed"
            edt_status.setText(feed?.description)
            adapter.addList(imageUri ?: arrayListOf())
        }
        tv_cancel.setOnClickListener {
            it.hideKeyboard()
            findNavController().popBackStack()

        }
        tv_remove.setOnClickListener {
            adapter.remove(imageUri ?: arrayListOf())
            adapter.notifyDataSetChanged()
        }
        btn_add_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_LOAD_IMAGE)
        }
        tv_save.setOnClickListener {
            progressBar.visible()
            it.hideKeyboard()
            val edtStatus = edt_status.text.toString()
            val listImage = adapter.getList()
            dataImage(edtStatus, listImage)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageUri?.removeAll(imageUri?: arrayListOf())

            if (data!!.clipData != null) {
                val totalItemsSelected = data.clipData!!.itemCount
                for (i in 0 until totalItemsSelected) {
                    val fileUri = data.clipData!!.getItemAt(i).uri
                    imageUri!!.add(fileUri)


                }

                adapter.addList(imageUri ?: arrayListOf())

            } else if (data.data != null) {
                val fileUri = data.data!!
                imageUri!!.add(fileUri)
                adapter.addList(imageUri ?: arrayListOf())

            }
        }


    }

    private fun dataImage(status: String, list: ArrayList<Uri>) {
        val listURL: ArrayList<String>? = arrayListOf()
        for (item in list) {

            val fileName = UUID.randomUUID().toString() + ".jpg"
            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(item)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        listURL?.add(it.toString())
                        Log.d("woo", listURL.toString())
                        if (listURL?.size == list.size) {
                            if (update == "update") {
                                feedViewModel.getUpdateFeed(status, listURL, feed?.id!!)
                            } else {
                                feedViewModel.getCreateFeed(status, listURL)

                            }
                        }
                    }
                }

                .addOnFailureListener { e ->
                    print(e.message)
                }
        }


    }

}