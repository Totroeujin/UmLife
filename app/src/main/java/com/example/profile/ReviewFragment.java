package com.example.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.event.EventListFragment;
import com.example.umlife.R;

import com.example.model.EventInfo;
import com.example.model.Review;
import com.example.model.UserInfo;
import com.example.utils.SpamCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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
    }

    EventInfo eventInfo = new EventInfo();
    UserInfo userInfo = new UserInfo();
    Review review;
    private RatingBar rating;
    private TextView comment;
    Button btnSubmit;
    FirebaseFirestore db;

    // Spam checking
    SpamCheck spamCheck;

    List<Review> reviewList = new ArrayList<>();

    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        spamCheck = new SpamCheck();
        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getString("profileImage") != null) {
                        userInfo.setProfileImage(document.getString("profileImage"));
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        rating = view.findViewById(R.id.ReviewRatingBar);
        comment = view.findViewById(R.id.ETReviewComment);
        btnSubmit = view.findViewById(R.id.BtnReviewSubmit);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("reviews").whereEqualTo("organiserId", userInfo.getUuid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
                        Review r = queryDocumentSnapshot.toObject(Review.class);
                        reviewList.add(r);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rating.getRating() == 0)
                    Toast.makeText(getContext(), "Please rate us!", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(comment.getText()))
                    comment.setError("Please tell us how you feel about our event");
                else {
                    String reviewDetail = comment.getText().toString();
                    // Spam check
                    if(spamCheck.vulgarTrigger(reviewDetail) || spamCheck.salesSpamTrigger(reviewDetail) ||
                            spamCheck.contentIsSpam(reviewDetail) || spamCheck.keySmash(reviewDetail) ||
                            spamCheck.comparativeReviewSpamCheck(reviewDetail, reviewList)) {
                        Toast.makeText(getContext(), "Your review has violated our community rule", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                db = FirebaseFirestore.getInstance();
                review = new Review(rating.getRating(), comment.getText().toString(), eventInfo.getOrganiserId(), userInfo.getUuid(), userInfo.getUsername(), userInfo.getProfileImage(), eventInfo.getEventId(), new Date().toString(), 0, 0);

                db.collection("reviews").add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Thank you for the feedback", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    public void setEvent(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }

}