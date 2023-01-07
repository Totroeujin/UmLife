package com.example.umlife;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.callbacks.QueryCompleteCallback;
import com.example.model.Post;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //UserInfo
    UserInfo userInfo = new UserInfo();

    ImageView loadChatIcon;

    CircleImageView createPostIcon;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Retrieve bundle from activity
        Bundle bundle = this.getArguments();
        if (bundle != null){
            userInfo = (UserInfo) bundle.getSerializable("userInfo");
        }
    }

    RecyclerView RVPostsList;
    LinearLayoutManager VerticalLayout;
    PostsListAdapter postsListAdapter;
    List<Post> postsList = new ArrayList<>();
    RecyclerView.LayoutManager PostsListRVLayoutManager;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create callbacks for postList to be generated before passing into adapter
        QueryCompleteCallback<Post> queryCompleteCallback = new QueryCompleteCallback<Post>() {
            @Override
            public void onQueryComplete(List<Post> postList) {
                postsListAdapter = new PostsListAdapter(getActivity(), postsList);
                RVPostsList.setAdapter(postsListAdapter);
            }
        };

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        // clear the post list to initial value
        postsList.clear();

        RVPostsList = view.findViewById(R.id.RVPostsLists);
        db = FirebaseFirestore.getInstance();

        db.collection("posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d: documentSnapshots) {
                        // Log.d("Post document", d.toString());
                        Post post = d.toObject(Post.class);
                        post.setPostId(d.getId());

                        db.collection("users").document(post.getPostUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()) {
                                    UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                                    Log.d("User post image: ", post.getPostImageUrl());
                                    post.setPostUserImageUrl(userInfo.getProfileImage());
                                    post.setPostUsername(userInfo.getUsername());
                                    // Add post to post list
                                    postsList.add(post);
                                    queryCompleteCallback.onQueryComplete(postsList);
                                    Log.d("Postlist size 1: ", String.valueOf(postsList.size()));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Fail to get post user data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    postsListAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("Postlist size: ", String.valueOf(postsList.size()));
        PostsListRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        postsListAdapter = new PostsListAdapter(getActivity(), postsList);
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        RVPostsList.setLayoutManager(PostsListRVLayoutManager);
        RVPostsList.setItemAnimator(new DefaultItemAnimator());
        RVPostsList.setAdapter(postsListAdapter);
        RVPostsList.setLayoutManager(VerticalLayout);

        return view;
    }

    //View complete created
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            //Assign id to variable
            createPostIcon = view.findViewById(R.id.IVCreatePost);

            //Define action onClick
            createPostIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}