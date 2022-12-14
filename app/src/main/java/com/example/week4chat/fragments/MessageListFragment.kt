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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.adapter.MessageAdapter
import com.example.week4chat.data.MessageItemModel
import com.example.week4chat.databinding.FragmentMessageListBinding
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
class MessageListFragment : Fragment() {
    private lateinit var binding: FragmentMessageListBinding
    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageArrayList: ArrayList<MessageItemModel>
    private lateinit var sendButton: ImageButton
    private lateinit var messageEditText: EditText
    private val faker = Faker()
    private val timeNow: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))
    private lateinit var messages: MessageItemModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageListBinding.inflate(inflater, container, false)
        recyclerView = binding.messageRecycleView
        sendButtonListener()
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
        recyclerView.layoutManager = layoutManager
        adapter = MessageAdapter(messageArrayList)
        recyclerView.adapter = adapter
        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationIcon(com.example.week4chat.R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun messagesDataInitialization() {
        messageArrayList = arrayListOf()
        binding.senderNamePreview.text =
            requireArguments().getString("senderName") //?????????? ?? ?????????????????? ??????????
        binding.avatarPreviewImage.setImageResource(requireArguments().getInt("personAvatarID")) //?????????? ?? ?????????????????? ??????????????
        for (i in requireArguments().getStringArrayList("personMessagesText")!!.indices) {
            messages = MessageItemModel(
                requireArguments().getStringArrayList("personMessagesText")!![i], //?????????? ??????????????????
                requireArguments().getStringArrayList("personMessagesTime")!![i], //?????????? ??????????????????
                false  //?????????????????? ???? ???? ????????
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
            recyclerView.adapter = adapter
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun sendButtonListener() {
        messageEditText = binding.messageEditText
        sendButton = binding.sendMessageImageButton
        sendButton.setOnClickListener {
            messages = MessageItemModel(
                messageEditText.text.toString(), //?????????? ??????????????????
                timeNow, //?????????????????? ??????????
                true //?????????????????? ???? ????????
            )
            messageArrayList.add(messages)
            messageEditText.text.clear()
            recyclerView.scrollToPosition(messageArrayList.lastIndex)
            Log.e("Destroy", messageArrayList.toString())
        }
    }
}