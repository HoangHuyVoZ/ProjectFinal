package com.example.projectfinal.ui.feed

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.feed.ImageData
import com.example.projectfinal.ui.feed.adapter.FeedCreateAdapter
import com.example.projectfinal.ui.main.HomeActivity
import com.example.projectfinal.utils.ACCESS_TOKEN
import com.example.projectfinal.utils.PREFS_NAME
import com.example.projectfinal.utils.hideBottomNav
import com.example.projectfinal.viewmodel.FeedViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*
import kotlin.collections.ArrayList


class CreateFragment : Fragment() {
    private val RESULT_LOAD_IMAGE = 1
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedCreateAdapter
    private var imageData: ArrayList<ImageData>? = null


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
        init()
        data()
    }

    private fun data() {
        feedViewModel.dataCreateFeed.observe(viewLifecycleOwner,{
            if(it.success){
                FancyToast.makeText(context,it.message,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
            }else{
                FancyToast.makeText(context,it.message,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()

            }
        })
    }

    private fun init() {

        imageData = arrayListOf()
        adapter = FeedCreateAdapter()
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recycler_image.setHasFixedSize(true)
        recycler_image.layoutManager = layoutManager
        recycler_image.adapter = adapter


        tv_cancel.setOnClickListener {
            findNavController().popBackStack()

        }
        tv_remove.setOnClickListener {
            adapter.remove(imageData ?: arrayListOf())
            adapter.notifyDataSetChanged()
        }
        btn_add_image.setOnClickListener {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
        tv_save.setOnClickListener {
            var listURL : ArrayList<String> = arrayListOf()
            val edtStatus = edt_status.text.toString()
            val listImage = adapter.getList()
            for(item in listImage){

                if (item.image != null) {
                    val fileName = UUID.randomUUID().toString() +".jpg"
                    val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

                    refStorage.putFile(item.image)
                        .addOnSuccessListener(
                            OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                                    listURL.add(it.toString())
                                    Log.d("woo", listURL.toString())
                                    if(listURL.size ==listImage.size){
                                        feedViewModel.getCreatedFeed(edtStatus,listURL)
                                    }
                                }
                            })

                        ?.addOnFailureListener(OnFailureListener { e ->
                            print(e.message)
                        })


                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (data!!.clipData != null) {
                val totalItemsSelected = data.clipData!!.itemCount
                imageData = arrayListOf()
//                imageData!!.clear()
                for (i in 0 until totalItemsSelected) {
                    val fileUri = data.clipData!!.getItemAt(i).uri
                    val fileName: String = getFileName(fileUri).toString()
                    val image = ImageData(fileName, fileUri)
                    imageData!!.add(image)

                }
                adapter.addList(imageData ?: arrayListOf())

            }else if (data.data != null) {
                val fileUri = data.data!!
                val fileName: String = getFileName(fileUri).toString()
                val image = ImageData(fileName, fileUri)
                imageData!!.add(image)
                adapter.addList(imageData ?: arrayListOf())

            }


//            Toast.makeText(context, "Selected Multiple Files", Toast.LENGTH_SHORT).show();
        }


    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }
        return result
    }


}