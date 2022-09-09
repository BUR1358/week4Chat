package com.example.week4chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.chatListAdapter
import com.example.week4chat.Adapter.OnChatItemClickListener
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel
import com.example.week4chat.databinding.FragmentChatListBinding


class chatListFragment : Fragment(), OnChatItemClickListener {
    lateinit var binding: FragmentChatListBinding
    private lateinit var adapter: chatListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<ChatListItemModel>


    lateinit var senderNameList: Array<String>
    lateinit var messagePreviewList: Array<String>
    lateinit var messageSendingTimeList: Array<String>
    lateinit var unreadMessageCountList: Array<String>
    lateinit var personAvatarIDList: Array<Int>
    lateinit var personMessagesList: ArrayList<String>
    lateinit var avatarImageResource: Array<Int>

    val messageListFragment = messageListFragment()
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
        //recyclerView.setHasFixedSize(true)
        adapter = chatListAdapter(chatArrayList, this)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize() {
        chatArrayList = arrayListOf<ChatListItemModel>()
        senderNameList = arrayOf("Саша", "Слава", "Витя", "Настя")
        messagePreviewList = arrayOf("Здорово", "Привет", "Хай", "Прив")
        messageSendingTimeList = arrayOf("10:30", "11:30", "12:30", "13:30")
        unreadMessageCountList = arrayOf("2", "3", "5", "20")
        personAvatarIDList = (0..senderNameList.size).map { (0..5).random() }.toTypedArray()
        personMessagesList = arrayListOf("сообщение 1", "сообщение 2", "сообщение 3", "сообщение 4")

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
            val chats = ChatListItemModel(
                senderNameList[i],
                messagePreviewList[i],
                messageSendingTimeList[i],
                unreadMessageCountList[i],
                avatarImageResource[personAvatarIDList[i]],
                personMessagesList
            )
            chatArrayList.add(chats)
        }
    }

    override fun OnItemClick(item: ChatListItemModel, position: Int) {
        argsToChatFragment.putString("senderName", item.senderName)
        argsToChatFragment.putInt("personAvatarID", item.avatarImageResource!!)
        argsToChatFragment.putStringArrayList("personMessages", item.personMessages)
        messageListFragment.arguments = argsToChatFragment

        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrameContainer, messageListFragment)
            .commit()
    }
}
