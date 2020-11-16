package com.example.projectfinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectfinal.api.CallApi
import com.example.projectfinal.model.group.CreateGroup
import com.example.projectfinal.model.group.Group
import com.example.projectfinal.model.topic.CreateTopic
import com.example.projectfinal.model.post.Post
import com.example.projectfinal.model.topic.Topic
import com.example.projectfinal.model.topic.updateTopic
import com.example.projectfinal.model.comment.CreateComment
import com.example.projectfinal.model.comment.comment
import com.example.projectfinal.model.post.CreatePost
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiManager: CallApi by lazy { CallApi() }
    private val compositeDisposable = CompositeDisposable()


    var groupData: MutableLiveData<Group> = MutableLiveData<Group>()
    var groupDataId: MutableLiveData<Group> = MutableLiveData<Group>()
    var createData: MutableLiveData<CreateGroup> = MutableLiveData<CreateGroup>()
    var updateGroupData: MutableLiveData<CreateGroup> = MutableLiveData<CreateGroup>()
    var deleteGroupData: MutableLiveData<CreateGroup> = MutableLiveData<CreateGroup>()
    var countGroupData: MutableLiveData<Int> = MutableLiveData<Int>()

    var topicData: MutableLiveData<Topic> = MutableLiveData<Topic>()
    var topicDataId: MutableLiveData<Topic> = MutableLiveData<Topic>()
    var createTopicData: MutableLiveData<CreateTopic> = MutableLiveData<CreateTopic>()
    var updateTopicData: MutableLiveData<updateTopic> = MutableLiveData<updateTopic>()
    var deleteTopicData: MutableLiveData<CreateTopic> = MutableLiveData<CreateTopic>()
    var countTopicData: MutableLiveData<Int> = MutableLiveData<Int>()

    var countPostData: MutableLiveData<Int> = MutableLiveData<Int>()
    var postData: MutableLiveData<Post> = MutableLiveData<Post>()
    var createPostData: MutableLiveData<CreatePost> = MutableLiveData<CreatePost>()
    var updatePostData: MutableLiveData<CreatePost> = MutableLiveData<CreatePost>()
    var deletePostData: MutableLiveData<CreatePost> = MutableLiveData<CreatePost>()
    var postIdData: MutableLiveData<Post> = MutableLiveData<Post>()

    var commentData: MutableLiveData<comment> = MutableLiveData<comment>()
    var createComment: MutableLiveData<CreateComment> = MutableLiveData<CreateComment>()
    var updateComment: MutableLiveData<CreateComment> = MutableLiveData<CreateComment>()
    var deleteComment: MutableLiveData<CreateComment> = MutableLiveData<CreateComment>()
    var countCommentData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun getCreateData( name: String) {
        compositeDisposable.add(
            apiManager.getCreateGroups(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        createData.value = it
                        getGroupId(it.result.id)
                        countGroupData.value = countGroupData.value?.plus(1)
                    } else {
                        createData.value = it
                    }

                }, {
                    createData.value = null
                })
        )
    }

    fun getGroup() {
        compositeDisposable.add(
            apiManager.getGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupData.value = it
                    countGroupData.value = it.result.size
                }, {
                    groupData.value = null
                })
        )
    }
    fun getGroupId(groupId: String) {
        compositeDisposable.add(
            apiManager.getGroupId(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    groupDataId.value = it
                }, {
                    groupDataId.value = null
                })
        )
    }
    fun getUpdateGroup( name: String) {
        compositeDisposable.add(
            apiManager.getUpdateGroup( name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateGroupData.value = it
                    getGroupId(it.result.id)
                }, {
                    updateGroupData.value = null
                })
        )
    }

    fun getDeleteGroup() {
        compositeDisposable.add(
            apiManager.getDeleteGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        deleteGroupData.value = it
                        countGroupData.value = countGroupData.value?.minus(1)
                    } else {
                        deleteGroupData.value = it

                    }

                }, {
                    deleteGroupData.value = null
                })
        )
    }

    //topic
    fun getTopic() {
        compositeDisposable.add(
            apiManager.getTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicData.value = it
                    countTopicData.value = it.result.size
                }, {
                    topicData.value = null

                })
        )
    }
    fun getTopicId(topicId: String) {
        compositeDisposable.add(
            apiManager.getTopicId(topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topicDataId.value = it
                }, {
                    topicDataId.value = null

                })
        )
    }
    fun getCreateTopic( name: String, descripton: String) {
        compositeDisposable.add(
            apiManager.getCreateTopic( name, descripton)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createTopicData.value = it
                    getTopicId(it.result.id)
                    countTopicData.value= countTopicData.value?.plus(1)
                }, {
                    createTopicData.value = null

                })
        )
    }

    fun getUpdateTopic(

        name: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getUpdateTopic( name, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateTopicData.value = it
                    getTopicId(it.result.id)
                }, {
                    updateTopicData.value = null

                })
        )
    }

    fun getDeleteTopic() {
        compositeDisposable.add(
            apiManager.getDeleteTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteTopicData.value = it
                    countTopicData.value= countTopicData.value?.minus(1)
                }, {
                    deleteTopicData.value = null

                })
        )
    }

    //post
    fun getPost() {
        compositeDisposable.add(
            apiManager.getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postData.value = it
                    countPostData.value = it.result.size
                }, {
                    postData.value = null

                })
        )
    }

    fun getCreatePost(
        title: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getCreatePost(title, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createPostData.value = it
                    countPostData.value = countPostData.value?.plus(1)
                }, {
                    createPostData.value = null

                })
        )
    }

    fun getUpdatePost(
        name: String,
        description: String
    ) {
        compositeDisposable.add(
            apiManager.getUpdatePost(name, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updatePostData.value = it
                }, {
                    updatePostData.value = null

                })
        )
    }

    fun getDeletePost() {
        compositeDisposable.add(
            apiManager.getDeletePost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deletePostData.value = it
                    countPostData.value = countPostData.value?.minus(1)

                }, {
                    deletePostData.value = null

                })
        )
    }

    //comment
    fun getPostId() {
        compositeDisposable.add(
            apiManager.getPostId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    postIdData.value = it
                }, {
                    postIdData.value = null
                })
        )
    }

    fun getComment() {
        compositeDisposable.add(
            apiManager.getComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    commentData.value = it
                    countCommentData.value= it.result.size
                }, {
                    commentData.value = null
                })
        )
    }

    fun getCreateComment(

        description: String
    ) {
        compositeDisposable.add(
            apiManager.getCreateComment( description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createComment.value = it
                    countCommentData.value= countCommentData.value?.plus(1)
                }, {
                    createComment.value = null
                })
        )
    }

    fun getUpdateComment(

        description: String
    ) {
        compositeDisposable.add(
            apiManager.getUpdateComment(
                description
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateComment.value = it

                }, {
                    updateComment.value = null
                })
        )
    }

    fun getDeleteComment(

    ) {
        compositeDisposable.add(
            apiManager.getDeleteComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteComment.value = it
                    countCommentData.value= countCommentData.value?.minus(1)
                }, {
                    deleteComment.value = null
                })
        )
    }
}