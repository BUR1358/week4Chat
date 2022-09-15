package com.example.week4chat.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.chatListAdapter
import com.example.week4chat.Adapter.OnChatItemClickListener
import com.example.week4chat.R
import com.example.week4chat.data.Repository
import com.example.week4chat.data.ChatListItemModel

import com.example.week4chat.databinding.FragmentChatListBinding

@RequiresApi(Build.VERSION_CODES.O)
class chatListFragment : Fragment(), OnChatItemClickListener {
    lateinit var binding: FragmentChatListBinding
    private lateinit var adapter: chatListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<ChatListItemModel>
    lateinit var senderNameList: ArrayList<String>
    lateinit var personAvatarIDList: Array<Int>
    lateinit var avatarImageResource: Array<Int>
    val messageListFragment = messageListFragment()
    val repository = Repository()
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
        getDataInitialization()
        connectingAdapter()
        swipeToRefresh()
    }

    private fun connectingAdapter() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.chatRecycleView
        recyclerView.layoutManager = layoutManager
        adapter = chatListAdapter(chatArrayList, this)
        recyclerView.adapter = adapter
    }

    private fun getDataInitialization() {
        chatArrayList = arrayListOf<ChatListItemModel>()
        senderNameList = repository.getNameList()
        personAvatarIDList = (0..senderNameList.size).map { (0..5).random() }.toTypedArray()
        avatarImageResource =
            arrayOf(
                R.drawable.av1,
                R.drawable.av2,
                R.drawable.av3,
                R.drawable.av4,
                R.drawable.av5,
                R.drawable.av6
            )

        for (i in senderNameList.indices) {
            val TimeAndMessageList = repository.getTimeAndMessageList()
            val chats = ChatListItemModel(
                senderNameList[i],//имя отправителя
                TimeAndMessageList[1].last(),//последнее сообщение
                TimeAndMessageList[0].last(),//время последнего сообщения
                TimeAndMessageList[1].size.toString(),//количество непрочтенных сообщений
                avatarImageResource[personAvatarIDList[i]],//ресурс аватара
                TimeAndMessageList // полный список сообщений и их времени
            )
            chatArrayList.add(chats)
        }
    }

    override fun OnItemClick(item: ChatListItemModel, position: Int) {
        argsToChatFragment.putString("senderName", item.senderName)
        argsToChatFragment.putInt("personAvatarID", item.avatarImageResource!!)
        argsToChatFragment.putStringArrayList("personMessagesText", item.personMessagesAndTime[1])
        argsToChatFragment.putStringArrayList("personMessagesTime", item.personMessagesAndTime[0])
        messageListFragment.arguments = argsToChatFragment

        if (!messageListFragment.isAdded) {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.mainFrameContainer, messageListFragment)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun swipeToRefresh() {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            getDataInitialization()
            connectingAdapter()
            swipeToRefresh.isRefreshing = false
        }
    }
}
