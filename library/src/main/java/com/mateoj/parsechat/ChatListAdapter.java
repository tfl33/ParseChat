package com.mateoj.parsechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mateoj.parsechat.model.ParseConversation;

import java.util.List;

/**
 * Created by jose on 9/17/15.
 */
public class ChatListAdapter extends ArrayAdapter<ParseConversation> {
    private String userId;
    public ChatListAdapter(Context context, String userId, List<ParseConversation> conversations) {
        super(context, 0, conversations);
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.conversation_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        }

        final ParseConversation conversation = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.title.setText(conversation.getTitle());
        return convertView;
    }

    final class ViewHolder {
        public TextView title;
    }
}
