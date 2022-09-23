package com.example.week4chat.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.adapter.OnChatItemClickListener
import com.example.week4chat.adapter.ChatListAdapter
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel
import com.example.week4chat.data.Repository
import com.example.week4chat.databinding.FragmentChatListBinding


@RequiresApi(Build.VERSION_CODES.O)
class ChatListFragment : Fragment(), OnChatItemClickListener {
    private lateinit var binding: FragmentChatListBinding
    private lateinit var adapter: ChatListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<ChatListItemModel>
    private lateinit var senderNameList: ArrayList<String>
    private lateinit var personAvatarIDList: Array<Int>
    private lateinit var avatarImageResource: Array<Int>
    private val messageListFragment = MessageListFragment()
    private val repository = Repository()
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
        chatArrayList = getDataInitialization()
        connectingAdapter()
        swipeToRefresh()
        scrollChangeListener()
    }

    private fun connectingAdapter() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.chatRecycleView
        recyclerView.layoutManager = layoutManager
        adapter = ChatListAdapter(chatArrayList, this)
        recyclerView.adapter = adapter
    }

    private fun getDataInitialization(): ArrayList<ChatListItemModel> {
        val generateNewChatList = arrayListOf<ChatListItemModel>()
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
            val timeAndMessageList = repository.getTimeAndMessageList()
            val chats = ChatListItemModel(
                repository.getUniqueId(),
                senderNameList[i],//имя отправителя
                timeAndMessageList[1].last(),//последнее сообщение
                timeAndMessageList[0].last(),//время последнего сообщения
                timeAndMessageList[1].size.toString(),//количество непрочтенных сообщений
                avatarImageResource[personAvatarIDList[i]],//ресурс аватара
                timeAndMessageList // полный список сообщений и их времени
            )
            generateNewChatList.add(chats)
        }
        return generateNewChatList
    }


    override fun onItemClick(item: ChatListItemModel, position: Int) {
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

    private fun swipeToRefresh() {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            chatArrayList.clear()
            chatArrayList = getDataInitialization()
            connectingAdapter()
            swipeToRefresh.isRefreshing = false

        }
    }

    private fun scrollChangeListener() {
        val nestedScrollView = binding.nestedScrollView
        val loadingProgressBar = binding.loadingProgressBar
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadingProgressBar.visibility = View.VISIBLE
                val newChatList: ArrayList<ChatListItemModel> = getDataInitialization()
                adapter.addChatList(newChatList)
            }
        })
    }
}
