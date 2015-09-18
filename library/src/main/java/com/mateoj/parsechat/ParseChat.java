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
    private static ParseChat instance;
    private final Context mContext;
    private final String appId;
    private final String consumerKey;
    private ParseUser user;

    private ParseChat(Builder builder) {
        this.mContext = builder.mContext;
        this.appId = builder.appId;
        this.consumerKey = builder.consumerKey;
    }

    public static ParseChat get() {
        return instance;
    }

    public static void initialize(Builder builder) {
        instance = new ParseChat(builder);
        Parse.enableLocalDatastore(instance.mContext);
        Parse.initialize(instance.mContext, instance.appId, instance.consumerKey);
        ParseObject.registerSubclass(ParseConversation.class);
        ParseObject.registerSubclass(Message.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void setUser(ParseUser user) {
        instance.user = user;
        ParseInstallation.getCurrentInstallation()
                .put(KEY_INSTALLATION_USER, user);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public ParseUser getUser() {
        return user;
    }
    public static void showConversations(Activity activityContext) {
        Intent intent = new Intent(activityContext, ConversationsActivity.class);
        intent.putExtra(ConversationsActivity.EXTRA_USER_ID, instance.getUser().getObjectId());
        activityContext.startActivity(intent);
    }

    public static void showConversation(ParseConversation conversation) {

    }

    public static void startConversation(final ParseConversation conversation) {
        conversation.saveInBackground();
    }

    public void startConversation(String user) {

    }

    public static class Builder {
        private final Context mContext;
        private final String appId;
        private final String consumerKey;
        public Builder(Context context, String appId, String consumerKey) {
            this.mContext = context;
            this.appId = appId;
            this.consumerKey = consumerKey;
        }
    }
}
