package com.example.umlife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Comment;
import com.example.model.Post;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentView> {

    private List<Comment> commentList;
    private FragmentActivity fragmentActivity;

    public class CommentView extends RecyclerView.ViewHolder{
        TextView TVCommentUsername;
        TextView TVCommentDetail;
        CircleImageView IVCommentAvatar;

        public CommentView(View view){
            super(view);
            TVCommentUsername = view.findViewById(R.id.TVCommentUsername);
            TVCommentDetail = view.findViewById(R.id.TVCommentDetail);
            IVCommentAvatar = view.findViewById(R.id.IVCommentAvatar);
        }
    }

    public CommentAdapter(FragmentActivity fragmentActivity, List<Comment> commentList){
        this.fragmentActivity = fragmentActivity;
        this.commentList = commentList;
    }


    @NonNull
    @Override
    public CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentView(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentView holder, final int position) {
        Comment comment = commentList.get(position);
        holder.TVCommentUsername.setText(comment.getCommenterUsername());
        holder.TVCommentDetail.setText(comment.getCommentDetail());
        Picasso.get().load(comment.getCommenterProfileImage())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVCommentAvatar);
    }

    @Override
    public int getItemCount() {
        if(commentList == null)
            return 0;
        int limit = 30;
        return Math.min(commentList.size(), limit);
    }
}
