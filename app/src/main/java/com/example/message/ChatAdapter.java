package com.example.message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Chat;
import com.example.model.Comment;
import com.example.model.Post;
import com.example.umlife.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatView> {

    private List<Chat> chatList;
    private FragmentActivity fragmentActivity;

    public class ChatView extends RecyclerView.ViewHolder{
        TextView TVChatUsername;
        TextView TVChatDetail;
        CircleImageView IVChatAvatar;

        public ChatView(View view){
            super(view);
            TVChatUsername = view.findViewById(R.id.TVChatUsername);
            TVChatDetail = view.findViewById(R.id.TVChatDetail);
            IVChatAvatar = view.findViewById(R.id.IVChatAvatar);
        }
    }

    public ChatAdapter(FragmentActivity fragmentActivity, List<Chat> chatList){
        this.fragmentActivity = fragmentActivity;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public ChatView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatView(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatView holder, int position) {
        ///Assigning values
        Chat chat = chatList.get(position);
        Log.d("Msg-inAdapter", holder.toString());
//        Log.d("Msg-passAdapter", chat.getChatUsername());
        holder.TVChatUsername.setText(chat.getChatUsername());
        holder.TVChatDetail.setText(chat.getChatDetail());
        Picasso.get().load(chat.getChatProfileImage()).into(holder.IVChatAvatar);
    }

    @Override
    public int getItemCount() {
        if(chatList == null)
            return 0;
        int limit = 7;
        return Math.min(chatList.size(), limit);
    }
}
