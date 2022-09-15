package com.example.week4chat.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.messageAdapter
import com.example.week4chat.data.MessageItemModel
import com.example.week4chat.databinding.FragmentMessageListBinding
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class messageListFragment : Fragment() {
    lateinit var binding: FragmentMessageListBinding
    private lateinit var adapter: messageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageArrayList: ArrayList<MessageItemModel>

    var senderName: String? = null
    var personAvatarID: Int? = null
    var personMessagesText: ArrayList<String>? = null
    var personMessagesTime: ArrayList<String>? = null
    private val myMessage = arrayListOf<Boolean>()
    private val faker = Faker()
    private lateinit var sendButton: ImageButton
    private lateinit var messageEditText: EditText
    private val timeNow: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentMessageListBinding.inflate(inflater, container, false)
        sendButtonListener()
        senderName = requireArguments().getString("senderName")
        personAvatarID = requireArguments().getInt("personAvatarID")
        personMessagesText = requireArguments().getStringArrayList("personMessagesText")
        personMessagesTime = requireArguments().getStringArrayList("personMessagesTime")
        for (i in personMessagesText!!) {
            myMessage.add(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesDataInitialization()
        connectingAdapter()
        swipeToRefresh()
    }

    private fun connectingAdapter() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.messageRecycleView
        recyclerView.layoutManager = layoutManager
        adapter = messageAdapter(messageArrayList)
        recyclerView.adapter = adapter
    }

    private fun messagesDataInitialization() {
        messageArrayList = arrayListOf<MessageItemModel>()
        binding.senderNamePreview.text = senderName
        binding.avatarPreviewImage.setImageResource(personAvatarID!!)
        for (i in personMessagesText!!.indices) {
            val messages = MessageItemModel(
                personMessagesText!![i],
                personMessagesTime!![i],
                myMessage[i]
            )
            messageArrayList.add(messages)
        }
    }

    private fun swipeToRefresh() {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            var randomSelection: Int
            for (i in messageArrayList.indices) {
                if (!messageArrayList[i].MyMessage!!) {
                    if ("deleted" !in messageArrayList[i].messageSendingTime!!) {
                        messageArrayList[i].messageText = "..."
                        messageArrayList[i].messageSendingTime = "$timeNow (deleted)"
                        randomSelection = (0..1).random()
                        if (randomSelection == 1) {
                            messageArrayList[i].messageText = faker.starWars.quote()
                            messageArrayList[i].messageSendingTime = "$timeNow (changed)"
                        }
                    }
                }
            }
            updateData()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun updateData() {
        personMessagesText!!.clear()
        personMessagesTime!!.clear()
        myMessage.clear()
        for (i in messageArrayList.indices){
            personMessagesText!!.add(messageArrayList[i].messageText)
            personMessagesTime!!.add(messageArrayList[i].messageSendingTime!!)
            myMessage.add(messageArrayList[i].MyMessage!!)
        }
    }

    private fun sendButtonListener() {
        messageEditText = binding.messageEditText
        sendButton = binding.sendMessageImageButton
        sendButton.setOnClickListener {
            val mySendMessage = MessageItemModel(
                messageEditText.text.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm")),
                true
            )
            messageArrayList.add(mySendMessage)
            messageEditText.text.clear()
            recyclerView.scrollToPosition(messageArrayList.lastIndex)
            updateData()
        }

    }
}