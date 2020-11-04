package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.Group.Groupdata
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.GroupAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: GroupAdapter
    private var accessToken: String = ""
    private var roleGroup = 0
    private var idGroup = ""
    private var role = ""
    private var position =""
    private lateinit var pref : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pref = requireContext().getSharedPreferences(
            PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        accessToken = pref.getString(ACCESS_TOKEN, "").toString()
        role = pref.getString(ROLE, "").toString()

        init()
        getGroup()
        dataGroup()
    }

    @SuppressLint("SetTextI18n")
    private fun dataGroup() {
        //getAll
        mainViewModel.groupData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    adapter.clear()
                    adapter.addList(it.result as MutableList<Groupdata>)
                    adapter.notifyDataSetChanged()
                    tv_countGroup.text = "Total: ${it.result.size} group"
                    progressBar3.invisible()

                } else {
                    tv_error_group.visible()
                    tv_error_group.text = it.message
                }
            } else {
                tv_token.visible()
                tv_token.setOnClickListener {
                    context?.let { it -> firstTime(it,true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar3.invisible()
            }
        })
        //create
        mainViewModel.createData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "Create Group Successfully", Toast.LENGTH_SHORT)

                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })
        //update
        mainViewModel.updateGroupData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "Update Group Successfully", Toast.LENGTH_SHORT)

                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })
        //remove
        mainViewModel.deleteGroupData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(context, "remove Group Successfully", Toast.LENGTH_SHORT)

                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                }
            }
            progressBar3.visible()
            mainViewModel.getGroup(accessToken)
            adapter.clear()
        })

    }

    private fun getGroup() {
        mainViewModel.getGroup(accessToken)
    }

    private fun init() {
        tv_token.invisible()
        progressBar3.visible()
        tv_error_group.invisible()
        layoutManager = GridLayoutManager(context, 2)
        recyclerViewGroup.setHasFixedSize(true)
        recyclerViewGroup.layoutManager = layoutManager
        adapter = GroupAdapter(this)
        recyclerViewGroup.adapter = adapter

        if (role == ADMIN) {
            btnAdd_Group.visible()
        } else {
            btnAdd_Group.invisible()
        }

        btnAdd_Group.setOnClickListener {
            roleGroup = 0
            showDialog("")
        }
    }

    private fun showDialog(titleGroup: String) {
        val dialog = LayoutInflater.from(context)
            .inflate(R.layout.create_group_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(dialog)
        val mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog.window?.attributes = layoutParams
        if (roleGroup == 0) {
            dialog.btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_group.setOnClickListener {
                val title = dialog.edt_title_create.text.toString()
                if (title.isEmpty()) {
                    dialog.edt_title_create.error = "You have not entered a title"
                } else {
                    mainViewModel.getCreateData(accessToken, title)
                    mAlertDialog.dismiss()
                }
            }
        } else {
            dialog.tv_title.text = "Update group"
            dialog.edt_title_create.setText(titleGroup)
            dialog.btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            dialog.btn_create_group.text = "update"
            dialog.btn_create_group.setOnClickListener {
                val name = dialog.edt_title_create.text.toString()
                if (name.isEmpty()) {
                    dialog.edt_title_create.error = "You have not entered a title"
                } else {
                    mainViewModel.getUpdateGroup(accessToken, idGroup, name)
                    mAlertDialog.dismiss()
                }
            }
        }

    }

    override fun onClickItem(id: String, role: Int, name: String, positionn: String) {
        idGroup = id
        roleGroup = role
        position=positionn
        when (role) {
            2 -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Warning !!!")
                builder.setMessage("Do you want remove this group?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    mainViewModel.getDeleteGroup(accessToken, id)
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            4 -> {
                val pref: SharedPreferences = requireContext().getSharedPreferences(
                    PREFS_NAME,
                    AppCompatActivity.MODE_PRIVATE
                )
                val editor = pref.edit()
                editor.putString(GROUP_ID, id)
                editor.putString(NAME_GROUP, name)
                editor.apply()
                findNavController().navigate(R.id.action_groupFragment_to_topicFragment)
            }
            else -> {
                showDialog(name)
            }
        }
    }

}