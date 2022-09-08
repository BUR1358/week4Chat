package com.example.week4chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.ChatListAdapter
import com.example.week4chat.Adapter.OnChatItemClickListener
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel
import com.example.week4chat.databinding.FragmentChatListBinding


class ChatListFragment : Fragment(), OnChatItemClickListener {
    lateinit var binding: FragmentChatListBinding
    private lateinit var adapter: ChatListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<ChatListItemModel>


    lateinit var senderName: Array<String>
    lateinit var messagePreview: Array<String>
    lateinit var messageSendingTime: Array<String>
    lateinit var unreadMessageCount: Array<String>

    val chatFragment = chatFragment()
    private val argsToChatFragment = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        adapter = ChatListAdapter(chatArrayList, this)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize() {
        chatArrayList = arrayListOf<ChatListItemModel>()

        senderName = arrayOf("Саша", "Слава", "Витя", "Настя")
        messagePreview = arrayOf("Здорово", "Привет", "Хай", "Прив")
        messageSendingTime = arrayOf("10:30", "11:30", "12:30", "13:30")
        unreadMessageCount = arrayOf("2", "3", "5", "20")

        for (i in senderName.indices) {
            val chats = ChatListItemModel(
                senderName[i],
                messagePreview[i],
                messageSendingTime[i],
                unreadMessageCount[i]
            )
            chatArrayList.add(chats)
        }
    }

    override fun OnItemClick(item: ChatListItemModel, position: Int) {
        Toast.makeText(context, item.senderName, Toast.LENGTH_LONG).show()

        argsToChatFragment.putString("senderName", item.senderName)
        chatFragment.arguments = argsToChatFragment

        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrameContainer, chatFragment)
            .commit()
    }
}
