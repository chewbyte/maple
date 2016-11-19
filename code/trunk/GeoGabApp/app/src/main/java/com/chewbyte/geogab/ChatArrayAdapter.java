package com.chewbyte.geogab;

/**
 * Created by Chris on 17/09/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chewbyte.geogab.common.Session;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private TextView snippet;
    private CircleImageView profileImage;
    private List<ChatMessage> chatMessageList;
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        snippet.setText(object.getMessage());
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        this.chatMessageList = Session.getCurrentThread().getChatMessageList();
        this.snippet = Session.getCurrentThread().getSnippet();
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View row, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.chat_right, parent, false);
        }else{
            row = inflater.inflate(R.layout.chat_left, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.message);
        chatText.setText(chatMessageObj.message);

        profileImage = (CircleImageView) row.findViewById(R.id.profile_image);
        profileImage.setImageResource(Session.users.get(chatMessageObj.ownerId).getProfileImage());

        return row;
    }
}