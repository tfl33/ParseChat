package com.mateoj.parsechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mateoj.parsechat.model.Message;
import com.mateoj.parsechat.model.ParseConversation;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by jose on 9/17/15.
 */
public class ParseChat {
    public static final String KEY_INSTALLATION_USER = "user";
    private final Context mContext;
    private final String appId;
    private final String consumerKey;
    private ParseUser user;

    public ParseChat(Config config) {
        this.mContext = config.mContext;
        this.appId = config.appId;
        this.consumerKey = config.consumerKey;
        Parse.enableLocalDatastore(mContext);
        Parse.initialize(mContext, appId, consumerKey);
        ParseObject.registerSubclass(ParseConversation.class);
        ParseObject.registerSubclass(Message.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


    public void setUser(ParseUser user) {
        this.user = user;
        ParseInstallation.getCurrentInstallation()
                .put(KEY_INSTALLATION_USER, user);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public ParseUser getUser() {
        return user;
    }

    public void showConversations(Activity activityContext) {
        Intent intent = new Intent(activityContext, ConversationsActivity.class);
        intent.putExtra(ConversationsActivity.EXTRA_USER_ID, getUser().getObjectId());
        activityContext.startActivity(intent);
    }

    public void showConversation(ParseConversation conversation) {
    }

    public void startConversation(final ParseConversation conversation) {
        conversation.saveInBackground();
    }

    public void startConversation(String user) {
    }

    public static class Config {
        private final Context mContext;
        private final String appId;
        private final String consumerKey;
        public Config(Context context, String appId, String consumerKey) {
            this.mContext = context;
            this.appId = appId;
            this.consumerKey = consumerKey;
        }
    }
}
