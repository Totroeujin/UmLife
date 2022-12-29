package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.UploadPost;

import java.util.List;
// import com.squareup.picasso.Picasso;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostView> {
    private List<UploadPost> postsList;
    private FragmentActivity fragmentActivity;

    public class PostView extends RecyclerView.ViewHolder{
        TextView TVPostUserId;
        TextView TVPostDetail;
        ImageView IVPostImageUrl;

        public PostView(View view){
            super(view);

            TVPostUserId = view.findViewById(R.id.TVPostUserId);
            TVPostDetail = view.findViewById(R.id.TVPostDetail);
            IVPostImageUrl = view.findViewById(R.id.IVPostImageUrl);
        }
    }

    public PostsListAdapter(FragmentActivity fragmentActivity, List<UploadPost> postsList){
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
        UploadPost post = postsList.get(position);
        holder.TVPostUserId.setText(post.getUserId());
        holder.TVPostDetail.setText(post.getPostDetail());
        // Picasso.get().load(post.getPostImageUrl()).into(holder.IVPostImageUrl);
    }

    @Override
    public int getItemCount() {
        if(postsList == null)
            return 0;
        int limit = 1;
        return Math.min(postsList.size(), limit);
    }
}

