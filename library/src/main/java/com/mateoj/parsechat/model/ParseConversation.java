package com.mateoj.parsechat.model;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Created by jose on 9/17/15.
 */
@ParseClassName("Conversation")
public class ParseConversation extends ParseObject {
    public static final String KEY_USERS = "userIds";
    public static final String KEY_NAME = "name";

    private ParseUser receiverUser;

    public ParseConversation(Builder builder) {
        put(KEY_NAME, builder.title);
        put(KEY_USERS, Arrays.asList(builder.sender.getObjectId(), builder.receiverUserId));
        fetchToUserObject(builder.receiverUserId);
    }
    public ParseConversation() {
        fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    try {
                        String receiverId;
                        if (getJSONArray(KEY_USERS).getString(0).equals(ParseUser.getCurrentUser().getObjectId())) {
                            receiverId = getJSONArray(KEY_USERS).getString(1);
                        } else {
                            receiverId = getJSONArray(KEY_USERS).getString(0);
                        }
                        fetchToUserObject(receiverId);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void fetchToUserObject(String objectId) {
        ParseUser.getQuery().getInBackground(objectId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e != null)
                    e.printStackTrace();
                else {
                    receiverUser = object;
                }
            }
        });
    }

    public ParseUser getReceiverUser() {
        return receiverUser;
    }
    public static ParseQuery<ParseConversation> getQuery() {
        return new ParseQuery<ParseConversation>(ParseConversation.class);
    }

    public String getTitle() {
        return getString(KEY_NAME);
    }

    public static class Builder {
        private ParseUser sender;
        private String receiverUserId;
        private String title;
        public Builder setSender(ParseUser user) {
            sender = user;
            return this;
        }

        public Builder setReceiver(String userId) {
            this.receiverUserId = userId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        public ParseConversation build() {
            return new ParseConversation(this);
        }
    }
}
