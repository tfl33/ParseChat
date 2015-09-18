package com.mateoj.parsechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mateoj.parsechat.model.Message;
import com.mateoj.parsechat.model.ParseConversation;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MessagesActivity extends AppCompatActivity {
    public static final String TAG = MessagesActivity.class.getSimpleName();
    public static final String EXTRA_CONVERSATION_ID = TAG + ".conversationId";
    private ListView messagesListView;
    private EditText messageEditText;
    private Button submitButton;
    private ParseConversation conversation;
    private MessageListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        EventBus.getDefault().register(this);
        if (EventBus.getDefault().getStickyEvent(ParseConversation.class) != null)
            conversation = EventBus.getDefault().removeStickyEvent(ParseConversation.class);
        else
            finish();
        getSupportActionBar().setTitle(conversation.getTitle());
        messagesListView = (ListView) findViewById(R.id.lvChat);
        messageEditText = (EditText) findViewById(R.id.etMessage);
        submitButton = (Button) findViewById(R.id.btSend);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    public class SendMessageCallback implements SaveCallback {
        private Message message;
        public SendMessageCallback(Message message) {
            this.message = message;
        }
        @Override
        public void done(ParseException e) {
            if (e != null) {
                e.printStackTrace();
                mAdapter.remove(message);
                Toast.makeText(MessagesActivity.this, "Error posting message", Toast.LENGTH_SHORT).show();
            } else {
                ParseQuery<ParseInstallation> installation = new ParseQuery<>(ParseInstallation.class);
                installation.whereEqualTo(ParseChat.KEY_INSTALLATION_USER, message.get(Message.KEY_TO_USER));
                ParsePush.sendMessageInBackground(message.getText(), installation);
            }
        }
    }

    private void sendMessage() {
        final Message message = new Message(conversation, messageEditText.getText().toString());
        mAdapter.add(message);
        message.saveInBackground(new SendMessageCallback(message));
    }

    public void onEvent(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.add(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message.getQuery()
                .whereEqualTo(Message.KEY_CONVERSATION, conversation)
                .findInBackground(new FindCallback<Message>() {
                    @Override
                    public void done(List<Message> objects, ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            mAdapter = new MessageListAdapter(MessagesActivity.this,
                                    ParseUser.getCurrentUser().getObjectId(),
                                    objects);
                            messagesListView.setAdapter(mAdapter);
                        }
                    }
                });
    }
}
