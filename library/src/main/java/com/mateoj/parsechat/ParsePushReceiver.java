package com.mateoj.parsechat;

import android.content.Context;
import android.content.Intent;

import com.mateoj.parsechat.model.Message;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by jose on 9/17/15.
 */
public class ParsePushReceiver extends ParsePushBroadcastReceiver{
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString(KEY_PUSH_DATA));
            Message message = new Message(json.getString("alert"));
            EventBus.getDefault().post(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
