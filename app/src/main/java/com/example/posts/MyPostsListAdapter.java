package com.example.posts;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;
import com.example.profile.ProfileFragment;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
// import com.squareup.picasso.Picasso;

public class MyPostsListAdapter extends RecyclerView.Adapter<MyPostsListAdapter.PostView> {
    private List<Post> postsList;
    private FragmentActivity fragmentActivity;

    FirebaseFirestore db;

    public class PostView extends RecyclerView.ViewHolder{
        // Post details
        CircleImageView IVPostUserImage;
        TextView TVPostUsername;
        TextView TVPostDetail;
        ImageView IVPostImageUrl;
        RecyclerView RVPostsList;

        // Navigate to comment section
        TextView TVPostCommentNum;
        com.google.android.material.textfield.TextInputEditText ETComment;

        public PostView(View view){
            super(view);

            IVPostUserImage = view.findViewById(R.id.IVPostUserImage);
            TVPostUsername = view.findViewById(R.id.TVPostUsername);
            TVPostDetail = view.findViewById(R.id.TVPostDetail);
            IVPostImageUrl = view.findViewById(R.id.IVPostImageUrl);

            TVPostCommentNum = view.findViewById(R.id.TVPostCommentNum);
            ETComment = view.findViewById(R.id.ETComment);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarProfile);
            toolbar.inflateMenu(R.menu.menu_post);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deletePost) {
                        int position = getAdapterPosition();
                        Post post = postsList.get(position);
                        // Add new redeemed rewards
                        db.collection("posts").document(post.getPostId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // "Refresh" the profile post list after post being delete
                                ProfileFragment profileFragment = new ProfileFragment();
                                FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, profileFragment);
                                ft.commit();
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });

            // Set onClickListener to navigate to comment page and open keyboard
            TVPostCommentNum.setOnClickListener(new View.OnClickListener() {
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

        }
    }

    public MyPostsListAdapter(FragmentActivity fragmentActivity, List<Post> postsList){
        this.fragmentActivity = fragmentActivity;
        this.postsList = postsList;
    }


    @NonNull
    @Override
    public PostView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_item, parent, false);
        return new PostView(itemView);
    }

    @Override
    public void onBindViewHolder(final PostView holder, final int position) {
        db = FirebaseFirestore.getInstance();
        Post post = postsList.get(position);
        holder.TVPostUsername.setText(post.getPostUsername());
        holder.TVPostDetail.setText(post.getPostDetail());
        holder.TVPostCommentNum.setText("comments ->");
        Picasso.get().load(post.getPostUserImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVPostUserImage);
        Picasso.get().load(post.getPostImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVPostImageUrl);
    }

    @Override
    public int getItemCount() {
        if(postsList == null)
            return 0;
        int limit = 30;
        return Math.min(postsList.size(), limit);
    }
}

