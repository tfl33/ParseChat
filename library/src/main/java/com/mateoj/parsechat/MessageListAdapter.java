package com.mateoj.parsechat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mateoj.parsechat.model.Message;

import java.util.List;

/**
 * Created by jose on 9/17/15.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {
    private String userId;
    public MessageListAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        final boolean isMine = message.getFromUserId().equals(userId);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.message_item_mine, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        if (isMine) {
            viewHolder.body.setTextColor(Color.RED);
            viewHolder.body.setGravity(Gravity.RIGHT);
        } else {
            viewHolder.body.setTextColor(Color.BLACK);
            viewHolder.body.setGravity(Gravity.LEFT);
        }
        viewHolder.body.setText(message.getText());
        return convertView;
    }

    public final class ViewHolder {
        TextView body;
    }
}
