package com.delivery.xianxian.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.utils.LocalUserInfo;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by zyz on 2019-10-15.
 * 环信聊天
 */
public class ChatMainActivity extends EaseBaseActivity {
    private TextView unreadLabel;
    private Button[] mTabs;
    private EaseConversationListFragment conversationListFragment;
    private EaseContactListFragment contactListFragment;
//    private SettingsFragment settingFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chatmain);


        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_conversation);
        mTabs[1] = (Button) findViewById(R.id.btn_address_list);
        mTabs[2] = (Button) findViewById(R.id.btn_setting);
        // set first tab as selected
        mTabs[0].setSelected(true);

        conversationListFragment = new EaseConversationListFragment();
        contactListFragment = new EaseContactListFragment();
//        settingFragment = new SettingsFragment();
        contactListFragment.setContactsMap(getContacts());
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(ChatMainActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                intent.putExtra("NickName", "对方昵称");
                startActivity(intent);

//                startActivity(new Intent(ChatMainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        /*contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(ChatMainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });*/
        fragments = new Fragment[] { conversationListFragment, contactListFragment };
        // add and show first fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, conversationListFragment)
                .add(R.id.fragment_container, contactListFragment).hide(contactListFragment).show(conversationListFragment)
                .commit();

        //注册一个监听连接状态的listener
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                for (EMMessage message : list) {
                    MyLogger.i("》》》》》》》会话列表", "onMessageReceived id : " + message.getMsgId());
                    //接收并处理扩展消息
                    String userName = message.getStringAttribute("user_name", "");
                    String userId = message.getStringAttribute("user", "");
                    String userPic = message.getStringAttribute("head_img_url", "");
                    String hxIdFrom = message.getFrom();
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    easeUser.setAvatar(userPic);
                    easeUser.setNickname(userName);
                    getSharedPreferences("user",MODE_PRIVATE).edit().putString(hxIdFrom,userName+"&"+userPic).commit();
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });

        registerMessageListener();
    }


    /**
     * onTabClicked
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                index = 0;
                break;
            case R.id.btn_address_list:
                index = 1;
                break;
            case R.id.btn_setting:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab as selected.
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /**
     * prepared users, password is "123456"
     * you can use these user to test
     *
     * @return
     */
    private Map<String, EaseUser> getContacts(){
        Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();
        for(int i = 1; i <= 10; i++){
            EaseUser user = new EaseUser("easeuitest" + i);

            contacts.put("easeuitest" + i, user);
        }
        return contacts;
    }

    private void setEaseUser() {
        EaseUI easeUI = EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username) {
        EaseUser easeUser = new EaseUser(username);
        if (username.equals(LocalUserInfo.getInstance(this).getNickname())) {
            easeUser.setNickname(LocalUserInfo.getInstance(this).getNickname());
            easeUser.setAvatar(OkHttpClientManager.IMGHOST + LocalUserInfo.getInstance(this).getUserImage());
        } else {
           /* Connections friend = new FriendDao(getApplicationContext()).getFriendByUsername(username);
            easeUser.setNickname(friend.getRealname());
            easeUser.setAvatar(friend.getHeadimg());*/
        }
        return easeUser;
    }

    EMMessageListener messageListener;
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    MyLogger.i("》》》》》》》》", "onMessageReceived id : " + message.getMsgId());
                    //接收并处理扩展消息
                    String userName = message.getStringAttribute("user_name", "");
                    String userId = message.getStringAttribute("user", "");
                    String userPic = message.getStringAttribute("head_img_url", "");
                    String hxIdFrom = message.getFrom();
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    easeUser.setAvatar(userPic);
                    easeUser.setNickname(userName);
                    getSharedPreferences("user",MODE_PRIVATE).edit().putString(hxIdFrom,userName+"&"+userPic).commit();
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    MyLogger.i("》》》》》》》》", "receive command message");
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
}
