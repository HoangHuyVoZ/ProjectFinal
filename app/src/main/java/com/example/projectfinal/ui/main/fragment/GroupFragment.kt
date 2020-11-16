package com.example.projectfinal.ui.main.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectfinal.R
import com.example.projectfinal.model.group.Groupdata
import com.example.projectfinal.ui.auth.AuthActivity
import com.example.projectfinal.ui.main.adapter.GroupAdapter
import com.example.projectfinal.utils.*
import com.example.projectfinal.viewmodel.MainViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.create_group_dialog.view.*
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment(), ClickItem {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: GroupAdapter
    private var roleGroup: Int? = 0
    private var position: Int? = 0
    private var mAlertDialog: Dialog? = null
    private var title: String? = ""
    private var isUpdate: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        getGroup()
        dataGroup()

    }

    @SuppressLint("SetTextI18n")
    private fun dataGroup() {
        //getAll
        mainViewModel.countGroupData.observe(viewLifecycleOwner, {
            it.let {
                tv_countGroup.text = "Total: $it group"

            }
        })
        mainViewModel.groupData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    val list = it.result
                    val diffUtilCallback = GroupDiffUtilCallback(adapter.getList(), list)
                    val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                    adapter.addList(list as MutableList<Groupdata>)
                    diffResult.dispatchUpdatesTo(adapter)
                    progressBar3.invisible()

                } else {
                    tv_error_group.visible()
                    tv_error_group.text = it.message
                }
            } else {
                tv_token.visible()
                tv_token.setOnClickListener {
                    context?.let { it -> firstTime(it, true) }
                    val intent = Intent(activity, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                progressBar3.invisible()
            }
        })
        //Create
        mainViewModel.createData.observe(viewLifecycleOwner, {
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
        mainViewModel.groupDataId.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    mAlertDialog?.dismiss()
                    when (isUpdate) {
                        1 -> adapter.addItem(it.result[0])

                        2 -> adapter.updateItem(it.result[0], position ?: 0)
                    }
                }
            }

        })
        //delete
        mainViewModel.deleteGroupData.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.success) {
                    adapter.remove(position ?: 0)
                    FancyToast.makeText(
                        context, "Delete done !", FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context, "Delete fail !", FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                }
            }
        })
        //update
        mainViewModel.updateGroupData.observe(viewLifecycleOwner, {
            it.let {
                if (it.success) {
                    isUpdate = 2
                    mAlertDialog?.dismiss()

                    FancyToast.makeText(
                        context, "Update done !", FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context, "Update Fail !", FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR, false
                    ).show()
                }
            }
        })
    }

    private fun getGroup() {
        mainViewModel.getGroup()
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
        mAlertDialog = mBuilder.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog?.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mAlertDialog?.window?.attributes = layoutParams
        if (roleGroup == 0) {
            dialog.btnCancel.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_group.setOnClickListener {
                title = dialog.edt_title_create.text.toString()
                if (title?.isEmpty()!!) {
                    dialog.edt_title_create.error = "You have not entered a title"
                } else {
                    mainViewModel.getCreateData(title ?: "")
                }
            }
        } else {
            dialog.tv_title.text = "Update group"
            dialog.edt_title_create.setText(titleGroup)
            dialog.btnCancel.setOnClickListener {
                mAlertDialog?.dismiss()
            }
            dialog.btn_create_group.text = "update"
            dialog.btn_create_group.setOnClickListener {
                title = dialog.edt_title_create.text.toString()
                if (title?.isEmpty()!!) {
                    dialog.edt_title_create.error = "You have not entered a title"
                } else {
                    mainViewModel.getUpdateGroup(title ?: "")

                }
            }
        }

    }

    override fun onClickItem(
        id: String,
        role: Int,
        name: String,
        des: String,
        positionn: Int
    ) {
        groupId = id
        roleGroup = role
        position = positionn
        when (role) {
            4 -> {

                val bundle = bundleOf("group_id" to id, "name" to name)
                findNavController().navigate(R.id.action_groupFragment_to_topicFragment, bundle)
            }
            else -> {
                showDialog(name)
            }
        }
    }

    override fun onRemoveClick(positionItem: Int, id: String) {
        position = positionItem
        groupId = id
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Warning !!!")
        builder.setMessage("Do you want remove this group?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            mainViewModel.getDeleteGroup()

        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

}