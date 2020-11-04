package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.Group.Group
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.Topic.Topic
import com.example.projectfinal.model.comment.comment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()

    var groupData : MutableLiveData<Group> = MutableLiveData<Group>()
    var createData: MutableLiveData<Group> = MutableLiveData<Group>()
    var updateGroupData: MutableLiveData<Group> = MutableLiveData<Group>()
    var deleteGroupData: MutableLiveData<Group> = MutableLiveData<Group>()
    var topicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var createTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var updateTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var deleteTopicData : MutableLiveData<Topic> = MutableLiveData<Topic>()
    var postData : MutableLiveData<Post> = MutableLiveData<Post>()
    var createPostData : MutableLiveData<Post> = MutableLiveData<Post>()
    var updatePostData : MutableLiveData<Post> = MutableLiveData<Post>()
    var deletePostData : MutableLiveData<Post> = MutableLiveData<Post>()
    var postIdData : MutableLiveData<Post> = MutableLiveData<Post>()
    var commentData : MutableLiveData<comment> = MutableLiveData<comment>()
    var createComment : MutableLiveData<comment> = MutableLiveData<comment>()
    var updateComment : MutableLiveData<comment> = MutableLiveData<comment>()
    var deleteComment : MutableLiveData<comment> = MutableLiveData<comment>()

    fun getCreateData(Authorization: String,name: String){
        compositeDisposable.add(
            apiManager.getCreateGroups(Authorization,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createData.value= it
                },{
                    createData.value=null
                })
        )
    }
    fun getGroup(Authorization : String){
        compositeDisposable.add(
            apiManager.getGroups(Authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupData.value = it
                },{
                    groupData.value = null
                })
        )
    }
    fun getUpdateGroup(Authorization: String,group_id: String,name: String){
        compositeDisposable.add(
            apiManager.getUpdateGroup(Authorization,group_id,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateGroupData.value = it
                },{
                    updateGroupData.value = null
                })
        )
    }
    fun getDeleteGroup(Authorization: String,group_id: String){
        compositeDisposable.add(
            apiManager.getDeleteGroup(Authorization,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteGroupData.value = it
                },{
                    deleteGroupData.value = null
                })
        )
    }
    //topic
    fun getTopic(Authorization:String,group_id: String){
        compositeDisposable.add(
            apiManager.getTopic(Authorization,group_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicData.value=it
                },{
                    topicData.value=null

                })
        )
    }
    fun getCreateTopic(Authorization: String,group_id: String,name: String,descripton: String){
        compositeDisposable.add(
            apiManager.getCreateTopic(Authorization,group_id,name,descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createTopicData.value= it
                },{
                    createTopicData.value= null

                })
        )
    }
    fun getUpdateTopic(Authorization: String,group_id: String,topic_id: String,name: String,descripton: String){
        compositeDisposable.add(
            apiManager.getUpdateTopic(Authorization,group_id,topic_id,name,descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateTopicData.value= it
                },{
                    updateTopicData.value= null

                })
        )
    }
    fun getDeleteTopic(Authorization: String,group_id: String,topic_id:String){
        compositeDisposable.add(
            apiManager.getDeleteTopic(Authorization,group_id,topic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteTopicData.value= it
                },{
                    deleteTopicData.value= null

                })
        )
    }
    //post
    fun getPost(Authorization:String,group_id: String,topic_id: String){
        compositeDisposable.add(
            apiManager.getPost(Authorization,group_id,topic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postData.value=it
                },{
                    postData.value=null

                })
        )
    }
    fun getCreatePost(Authorization: String, group_id: String, topic_id: String, title: String, description: String){
        compositeDisposable.add(
            apiManager.getCreatePost(Authorization,group_id,topic_id,title,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createPostData.value= it
                },{
                    createPostData.value= null

                })
        )
    }
    fun getUpdatePost(Authorization: String, group_id: String, topic_id: String,post_id: String, name: String, description: String){
        compositeDisposable.add(
            apiManager.getUpdatePost(Authorization,group_id,topic_id,post_id,name,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updatePostData.value= it
                },{
                    updatePostData.value= null

                })
        )
    }
    fun getDeletePost(Authorization: String,group_id: String,topic_id:String,post_id: String){
        compositeDisposable.add(
            apiManager.getDeletePost(Authorization,group_id,topic_id,post_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deletePostData.value= it
                },{
                    deletePostData.value= null

                })
        )
    }
    //comment
    fun getPostId(Authorization: String,group_id: String,topic_id:String,post_id: String){
        compositeDisposable.add(
            apiManager.getPostId(Authorization,group_id,topic_id,post_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postIdData.value=it
                },{
                    postIdData.value=null
                })
        )
    }
    fun getComment(Authorization: String,group_id: String,topic_id:String,post_id: String){
        compositeDisposable.add(
            apiManager.getComment(Authorization,group_id,topic_id,post_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    commentData.value=it
                },{
                    commentData.value=null
                })
        )
    }
    fun getCreateComment(Authorization: String,group_id: String,topic_id:String,post_id: String,descripton: String){
        compositeDisposable.add(
            apiManager.getCreateComment(Authorization,group_id,topic_id,post_id,descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createComment.value=it
                },{
                    createComment.value=null
                })
        )
    }
    fun getUpdateComment(Authorization: String, group_id: String, topic_id:String, post_id: String, comment_id: String, description: String){
        compositeDisposable.add(
            apiManager.getUpdateComment(Authorization,group_id,topic_id,post_id,comment_id,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateComment.value=it
                },{
                    updateComment.value=null
                })
        )
    }
    fun getDeleteComment(Authorization: String, group_id: String, topic_id:String, post_id: String, comment_id: String){
        compositeDisposable.add(
            apiManager.getDeleteComment(Authorization,group_id,topic_id,post_id,comment_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteComment.value=it
                },{
                    deleteComment.value=null
                })
        )
    }
}