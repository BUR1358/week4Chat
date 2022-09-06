package com.example.week4chat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.ChatListAdapter
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel
import com.example.week4chat.databinding.FragmentChatListBinding


class ChatListFragment : Fragment() {
    lateinit var binding: FragmentChatListBinding
    private lateinit var adapter: ChatListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<ChatListItemModel>


    lateinit var senderName: Array<String>
    lateinit var messagePreview: Array<String>
    lateinit var messageSendingTime: Array<String>
    lateinit var unreadMessageCount: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.chatRecycleView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ChatListAdapter(chatArrayList)
        recyclerView.adapter = adapter
        Log.e("AAA", "onViewCreated")
    }

    private fun dataInitialize() {
        chatArrayList = arrayListOf<ChatListItemModel>()

        senderName = arrayOf("Саша", "Слава", "Витя", "Настя")
        messagePreview = arrayOf("Здорово", "Привет", "Хай", "Прив")
        messageSendingTime = arrayOf("10:30", "11:30", "12:30", "13:30")
        unreadMessageCount = arrayOf("2", "3", "5", "1")

        for (i in senderName.indices) {
            val chats = ChatListItemModel(
                senderName[i],
                messagePreview[i],
                messageSendingTime[i],
                unreadMessageCount[i]
            )
            chatArrayList.add(chats)
            Log.e("AAA", "chatArrayList")
            Log.e("ААА", chatArrayList.toString())
        }
    }
}
