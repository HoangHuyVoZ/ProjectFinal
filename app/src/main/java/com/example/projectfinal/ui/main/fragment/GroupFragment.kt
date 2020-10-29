package com.example.projectfinal.ui.main.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.Group.Groupdata
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.GroupAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment(),ClickItem {
   private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: GridLayoutManager
    lateinit var adapter: GroupAdapter
    lateinit var accessToken : String
    var roleGroup = 0
    var idGroup = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE)
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()
        tv_token.invisible()
        progressBar3.visible()
        tv_error_group.invisible()
        getGroup()
        dataGroup()
    }

    private fun dataGroup() {
        //getAll
        mainViewModel.groupData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.success){
                    adapter.addList(it.result as MutableList<Groupdata>)
                    adapter.notifyDataSetChanged()
                    tv_countGroup.text = "Total: ${it.result.size.toString()} group" ?: "Total: 0 group"
                    progressBar3.invisible()

                }else{
                    tv_error_group.visible()
                    tv_error_group.text = it.message
                }
            }else{
                tv_token.visible()
                tv_token.setOnClickListener {
                    val pref: SharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putBoolean(FIRST_TIME, true)
                    editor.apply()
                    val intent= Intent(activity,AuthActivity::class.java)
                    startActivity(intent)
                }
                progressBar3.invisible()
            }
        })
        //create
        mainViewModel.createData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.success){
                    Toast.makeText(context, "Create Group Successfully", Toast.LENGTH_SHORT)

                }else{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })
        //update
        mainViewModel.updateGroupData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.success){
                    Toast.makeText(context, "Update Group Successfully", Toast.LENGTH_SHORT)

                }else{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })
        //remove
        mainViewModel.deleteGroupData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if(it.success){
                    Toast.makeText(context, "remove Group Successfully", Toast.LENGTH_SHORT)
                }else{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })

    }

    private fun getGroup() {
        if (accessToken != null) {
            mainViewModel.getGroup(accessToken)
        }
    }

    private fun init() {
        val pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE)
        val role = pref.getString(ROLE, "")
        if(role == ADMIN){
            btnAdd_Group.visible()
        }else{
            btnAdd_Group.invisible()
        }
        layoutManager = GridLayoutManager(context, 2)
        recyclerViewGroup.setHasFixedSize(true)
        recyclerViewGroup.layoutManager = layoutManager
        adapter = GroupAdapter(this)
        recyclerViewGroup.adapter = adapter
        btnAdd_Group.setOnClickListener {
            roleGroup = 0
            showDialog("")
        }
    }

    private fun showDialog(titleGroup: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_group_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        val  mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog.window?.attributes = layoutParams
        if(roleGroup==0){
            dialog.btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_group.setOnClickListener {
                var title = dialog.edt_title_create.text.toString()
                if(title.isEmpty()){
                    dialog.edt_title_create.error="You have not entered a title"
                }else{
                    mainViewModel.getCreateData(accessToken,title)
                    mAlertDialog.dismiss()
                }
            }
        }else{
            dialog.tv_title.text ="Update group"
            dialog.edt_title_create.setText(titleGroup)
            dialog.btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_group.text = "update"
            dialog.btn_create_group.setOnClickListener {
                var name = dialog.edt_title_create.text.toString()
                if(name.isEmpty()){
                    dialog.edt_title_create.error="You have not entered a title"
                }else{
                    if(idGroup!=null){
                        mainViewModel.getUpdateGroup(accessToken,idGroup,name)
                        mAlertDialog.dismiss()
                    }
                }
            }
        }

    }

    override fun onClickItem(id: String,role: Int,name: String,description: String) {
        idGroup = id
        roleGroup =role
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this group?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    mainViewModel.getDeleteGroup(accessToken,id)
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            4 -> {
                val pref: SharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString(GROUP_ID, id)
                editor.putString(NAME_GROUP,name)
                editor.apply()
                findNavController().navigate(R.id.action_groupFragment_to_topicFragment)
            }
            else -> {
                showDialog(name)
            }
        }
    }

}