package com.mateoj.parsechat.model;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

/**
 * Created by jose on 9/17/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String KEY_CONVERSATION = "conversation";
    public static final String KEY_FROM_USER = "fromUser";
    public static final String KEY_TO_USER = "toUser";
    public static final String KEY_WAS_READ = "wasRead";
    public static final String KEY_TEXT = "text";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_CREATE_DATE = "createdAt";
    public Message() {}

    public Message(ParseConversation conversation, String message) {
        setConversation(conversation);
        setToUser(conversation.getReceiverUser());
        setText(message);
//        setFromUser(ParseChat.get().getUser());
    }

    public Message(String message) {
        setText(message);
    }

    public void setFromUser(ParseUser user) {
        put(KEY_FROM_USER, user);
    }

    public String getFromUserId() {
        try {
            return (getParseObject(KEY_FROM_USER).getObjectId() == null)
                    ? "" : getParseObject(KEY_FROM_USER).getObjectId();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setToUser(ParseUser receiver) {
        put(KEY_TO_USER, receiver);
    }

    public void setRead(boolean read) {
        put(KEY_WAS_READ, read);
    }

    public void setConversation(ParseConversation conversation) {
        put(KEY_CONVERSATION, conversation);
    }

    public void setText(String text) {
        put(KEY_TEXT, text);
    }

    public String getText() {
        return getString(KEY_TEXT);
    }

    public void setPhoto(ParseFile image) {
        put(KEY_PHOTO, image);
    }

    public void send() {
        saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    e.printStackTrace();
                else {
                    Log.d("Parse", "Message saved: " + getObjectId());
                    ParseQuery<ParseInstallation> installation = new ParseQuery<>(ParseInstallation.class);
                    installation.whereEqualTo("user", get(KEY_TO_USER));
                    ParsePush.sendMessageInBackground(getText(), installation, new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                Log.d("Parse", "Message sent");
                            }
                        }
                    });
                }
            }
        });
    }

    public static void getMessages(String conversationId, FindCallback cb) {
        ParseQuery<Message> query = new ParseQuery<Message>(Message.class);
    }

    public static ParseQuery<Message> getQuery() {
        return new ParseQuery<Message>(Message.class)
                .orderByAscending(KEY_CREATE_DATE);
    }
}
