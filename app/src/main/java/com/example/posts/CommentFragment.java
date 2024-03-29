package com.example.posts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.UserInfo;
import com.example.posts.CommentAdapter;
import com.example.utils.SpamCheck;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CommentFragment extends Fragment {

    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Post currentPost;

    FragmentActivity fragmentActivity;

    RecyclerView RVCommentList;
    LinearLayoutManager VerticalLayout;
    CommentAdapter commentAdapter;
    List<Comment> commentList = new ArrayList<>();
    List<String> commenterImageUrls = new ArrayList<>();
    RecyclerView.LayoutManager CommentRVLayoutManager;
    TextView TVCommentNum;

    FirebaseFirestore db;

    TextInputEditText TIComment;
    InputMethodManager IMMComment;

    Button BtnPostComment;

    FirebaseUser curUser;
    String commenterProfileImage;

    // Spam check
    SpamCheck spamCheck;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
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

        // Request permissions to show keyboard programmatically
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }

        spamCheck = new SpamCheck();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Post comment
        BtnPostComment = view.findViewById(R.id.BtnPostComment);
        TIComment = view.findViewById(R.id.TIComment);

        RVCommentList = view.findViewById(R.id.RVCommentList);
        TVCommentNum = view.findViewById(R.id.TVCommentNum);

        // Invoke keyboard
        IMMComment = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        db = FirebaseFirestore.getInstance();

        curUser = FirebaseAuth.getInstance().getCurrentUser();


        BtnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = TIComment.getText().toString();

                if (comment.isEmpty()) return;



                // Spam check
                if (spamCheck.vulgarTrigger(comment)) {
                    Toast.makeText(getContext(), "We do not encourage vulgar words in our community", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spamCheck.salesSpamTrigger(comment)) {
                    Toast.makeText(getContext(), "We do not encourage sales promotion in our community", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spamCheck.contentIsSpam(comment) || spamCheck.comparativeCommentSpamCheck(comment, commentList)) {
                    Toast.makeText(getContext(), "We do not encourage spamming in our community", Toast.LENGTH_LONG).show();
                    return;
                }

                String userId = curUser.getUid();
                db.collection("users").document(userId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Document found in the Firestore database
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Convert the document to a User object
                                        UserInfo userInfo = document.toObject(UserInfo.class);
                                        // Access the additional fields in the 'user' document
                                        commenterProfileImage = userInfo.getProfileImage();
                                        db.collection("posts").document(currentPost.getPostId()).collection("comments")
                                                .add(new Comment(curUser.getUid(), comment, commenterProfileImage, userInfo.getUsername()))
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        CommentFragment commentFragment = new CommentFragment();
                                                        commentFragment.setCurrentPost(currentPost, fragmentActivity);
                                                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, commentFragment).commit();
                                                        TIComment.setText("");
                                                        TIComment.clearFocus();
                                                        Toast.makeText(getContext(), "Comment Posted", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), "Failed to post comment", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                });

        db.collection("posts").document(currentPost.getPostId()).collection("comments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                if(!querySnapshot.isEmpty()){
                    commentList.clear();
                    for(DocumentSnapshot d: querySnapshot) {
                        Comment comment = new Comment(d.getString("commenterId"), d.getString("commentDetail"), d.getString("commenterProfileImage"), d.getString("commenterUsername"));
                        commentList.add(comment);
                    }
                    TVCommentNum.setText(commentList.size() + " comments");
                    commentAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getContext(), "Document not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

        CommentRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVCommentList.setLayoutManager(CommentRVLayoutManager);
        commentAdapter = new CommentAdapter(getActivity(), commentList);
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RVCommentList.setLayoutManager(VerticalLayout);
        RVCommentList.setAdapter(commentAdapter);

        // Invoke keyboard
        TIComment.requestFocus();
        IMMComment.showSoftInput(TIComment, InputMethodManager.SHOW_IMPLICIT);

        return view;
    }

    //View complete created
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setCurrentPost(Post currentPost, FragmentActivity fragmentActivity){
        this.currentPost = currentPost;
        this.fragmentActivity = fragmentActivity;
    }

    // Request permission to open keyboard
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }
}