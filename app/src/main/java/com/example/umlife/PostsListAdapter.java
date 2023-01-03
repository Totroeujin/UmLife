package com.example.umlife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;
import com.example.model.UploadPost;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.List;
// import com.squareup.picasso.Picasso;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostView> {
    private List<Post> postsList;
    private FragmentActivity fragmentActivity;

    public class PostView extends RecyclerView.ViewHolder{
        TextView TVPostUserId;
        TextView TVPostDetail;
        ImageView IVPostImageUrl;
        TextView TVCommentNum;
        TextInputLayout TILComment;
        com.google.android.material.textfield.TextInputEditText ETComment;

        public PostView(View view){
            super(view);

            TVPostUserId = view.findViewById(R.id.TVPostUserId);
            TVPostDetail = view.findViewById(R.id.TVPostDetail);
            IVPostImageUrl = view.findViewById(R.id.IVPostImageUrl);
            TVCommentNum = view.findViewById(R.id.TVCommentNum);
            ETComment = view.findViewById(R.id.ETComment);
            TILComment = view.findViewById(R.id.TILComment);

            TVCommentNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos > RecyclerView.NO_POSITION){
                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setCurrentPost(postsList.get(pos), fragmentActivity);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, commentFragment).addToBackStack(null).commit();
                    }
                }
            });

            ETComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos > RecyclerView.NO_POSITION){
                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setCurrentPost(postsList.get(pos), fragmentActivity);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, commentFragment).addToBackStack(null).commit();
                    }
                }
            });

            TILComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos > RecyclerView.NO_POSITION){
                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setCurrentPost(postsList.get(pos), fragmentActivity);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, commentFragment).addToBackStack(null).commit();
                    }
                }
            });
        }
    }

    public PostsListAdapter(FragmentActivity fragmentActivity, List<Post> postsList){
        this.fragmentActivity = fragmentActivity;
        this.postsList = postsList;
    }


    @NonNull
    @Override
    public PostView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostView(itemView);
    }

    @Override
    public void onBindViewHolder(final PostView holder, final int position) {
        Post post = postsList.get(position);
        holder.TVPostUserId.setText(post.getUserName());
        holder.TVPostDetail.setText(post.getPostDetail());
        Picasso.get().load(post.getPostImageUrl()).into(holder.IVPostImageUrl);
    }

    @Override
    public int getItemCount() {
        if(postsList == null)
            return 0;
        int limit = 1;
        return Math.min(postsList.size(), limit);
    }
}

