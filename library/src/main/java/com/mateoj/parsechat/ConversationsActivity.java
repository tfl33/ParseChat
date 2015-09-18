package com.mateoj.parsechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mateoj.parsechat.model.ParseConversation;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ConversationsActivity extends AppCompatActivity {
    public static final String TAG = ConversationsActivity.class.getSimpleName();
    public static final String EXTRA_USER_ID = TAG + ".userid";
    private ListView conversationsListView;
    private String userId;
    private ChatListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        conversationsListView = (ListView) findViewById(R.id.lvChat);
        conversationsListView.setOnItemClickListener(new OnConversationClickListener());
        if (getIntent().hasExtra(EXTRA_USER_ID)) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
        } else {
            finish();
        }
    }

    public class OnConversationClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(ConversationsActivity.this, MessagesActivity.class);
            intent.putExtra(MessagesActivity.EXTRA_CONVERSATION_ID, mAdapter.getItem(i).getObjectId());
            EventBus.getDefault().postSticky(mAdapter.getItem(i));
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseConversation.getQuery()
        .whereContainedIn(ParseConversation.KEY_USERS, Collections.singletonList(userId))
        .findInBackground(new FindCallback<ParseConversation>() {
            @Override
            public void done(List<ParseConversation> objects, ParseException e) {
                if (e != null)
                    e.printStackTrace();
                else {
                    Log.d("Parse", objects.toString());
                    mAdapter = new ChatListAdapter(ConversationsActivity.this, userId, objects);
                    conversationsListView.setAdapter(mAdapter);
                }
            }
        });
    }
}
