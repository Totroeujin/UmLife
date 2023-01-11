package com.example.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.message.ChatFragment;
import com.example.model.EventInfo;
import com.example.model.Participant;
import com.example.model.Review;
import com.example.model.UserInfo;
import com.example.profile.ListAllReviewFragment;
import com.example.umlife.R;
import com.example.profile.ReviewFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailFragment newInstance(String param1, String param2) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Storage database
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseFirestore mFirebaseRef;
    private FirebaseUser curUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            //Get userInfo package
            curUser = FirebaseAuth.getInstance().getCurrentUser();
            mFirebaseRef.collection("users").document(curUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userInfo = documentSnapshot.toObject(UserInfo.class);
                    userInfo.setUuid(documentSnapshot.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Storage database References
            mStorageRef = FirebaseStorage.getInstance().getReference("events");
            mDatabaseRef = FirebaseDatabase.getInstance("https://umlife-41693-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("events");
            mFirebaseRef = FirebaseFirestore.getInstance();

        }
    }
    EventInfo eventInfo;
    List<UserInfo> userInfoList = new ArrayList<>();
    List<Review> reviewList = new ArrayList<>();
    UserInfo user;
    UserInfo userInfo;
    Button btnContact;
    CardView organiserImage;
    Button btnJoin;
    FragmentActivity fragmentActivity;
    FirebaseFirestore db;



    int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventListFragment eventListFragment = new EventListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventListFragment).addToBackStack(null).commit();
            }
        });
        btnContact = view.findViewById(R.id.BtnEventDetailContact);
        btnJoin = view.findViewById(R.id.BtnEventDetailJoin);

        organiserImage = view.findViewById(R.id.OrganiserCardView);

        ImageView EventDetailImage = view.findViewById(R.id.IVEventDetailImage);
        TextView EventDetailName = view.findViewById(R.id.TVEventDetailTitle);
        ImageView OrganiserImage = view.findViewById(R.id.IVEventDetailOrganiser);
        TextView OrganiserName = view.findViewById(R.id.TVOrganiserName);
        TextView EventDetailDate = view.findViewById(R.id.TVEventDetailDate);
        TextView EventDetailVenue = view.findViewById(R.id.TVEventDetailVenue);
        TextView EventDetailRegistrationDate = view.findViewById(R.id.TVEventDetailRegistrationDate);
        RatingBar EventDetailRating = view.findViewById(R.id.EventDetailRatingBar);
        TextView Rating = view.findViewById(R.id.TVEventDetailRating);
        TextView EventDetailInfo = view.findViewById(R.id.TVEventDetailInfo);
        TextView AppBarEventName = view.findViewById(R.id.appBarEventName);

        db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    userInfoList.clear();
                    for(DocumentSnapshot d : list){
                        String id = d.getId();
                        UserInfo user = d.toObject(UserInfo.class);
                        user.setUuid(id);
                        userInfoList.add(user);
                        if(id.equals(eventInfo.getOrganiserId())){
                            userInfo = d.toObject(UserInfo.class);
                            OrganiserName.setText(userInfo.getUsername());
                            Picasso.get().load(userInfo.getProfileImage())
                                .placeholder(R.drawable.empty_photo)
                                .error(R.drawable.empty_photo)
                                .into(OrganiserImage);
                            break;
                        }
                    }
                }
                else{
                    Toast.makeText(getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("reviews").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    reviewList.clear();
                    for(DocumentSnapshot d : list){
                        Review review = d.toObject(Review.class);
                        db.collection("users").document(review.getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.getString("profileImage")!= null){
                                        //Toast.makeText(getActivity(),temp.toString(),Toast.LENGTH_LONG).show();
                                        review.setUserImage(document.getString("profileImage"));
                                        //profilePicture.setImageURI(temp);
                                    }
                                }
                            }
                        });
                        review.setReviewId(d.getId());
                        if(review.getOrganiserId().equals(eventInfo.getOrganiserId()))
                            reviewList.add(review);
                    }
                }
                else{
                    Toast.makeText(getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                }
                double overallRating = 0;
                for(int i=0; i<reviewList.size(); i++){
                    overallRating+=reviewList.get(i).getRating();
                }
                overallRating /= reviewList.size();
                EventDetailRating.setRating(Float.parseFloat(String.valueOf(overallRating)));
                Rating.setText(String.format("%.2f", overallRating));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.get().load(eventInfo.getmImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(EventDetailImage);
        AppBarEventName.setText(eventInfo.getEventName());
        EventDetailName.setText(eventInfo.getEventName());
        EventDetailDate.setText(eventInfo.getEventDate());
        EventDetailVenue.setText(eventInfo.getEventVenue());
        EventDetailRegistrationDate.setText(String.format("%s - %s", eventInfo.getOpenRegistration(), eventInfo.getEndRegistration()));
        EventDetailInfo.setText(eventInfo.getEventDetail());

        organiserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                listAllReviewFragment.setEvent(userInfo, reviewList, getActivity());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).addToBackStack(null).commit();
            }
        });

        if(status == 0){
            btnJoin.setText("Join");
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JoinEventFragment joinEventFragment = new JoinEventFragment();
                    joinEventFragment.setEvent(eventInfo);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, joinEventFragment).addToBackStack(null).commit();
                }
            });
        }
        else if (status == 1){
            btnJoin.setText("Quit");
            user = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("participants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String participantID = "";
                            if(!queryDocumentSnapshots.isEmpty()){
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d : list){
                                    Participant participant = d.toObject(Participant.class);
                                    if(participant.getUuid().equals(user.getUuid()) && participant.getEventID().equals(eventInfo.getEventId())){
                                        participantID = d.getId();
                                        break;
                                    }
                                }
                                // TEst
                                if(!participantID.equals("")) {
                                    db.collection("participants").document(participantID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            MinusPoints();
                                            Toast.makeText(getContext(), "You have quit this event", Toast.LENGTH_SHORT).show();
                                            EventListFragment eventListFragment = new EventListFragment();
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventListFragment).addToBackStack(null).commit();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            });
        }
        else if (status == 2){
            btnJoin.setText("Review");
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReviewFragment reviewFragment = new ReviewFragment();
                    reviewFragment.setEvent(eventInfo);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reviewFragment).addToBackStack(null).commit();
                }
            });
        }
        else if (status == 3) {
            btnJoin.setText("Edit");
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(fragmentActivity, CreateOrEditEventActivity.class);
                    intent.putExtra("targetEvent", eventInfo);
                    intent.putExtra("action", "edit");
                    fragmentActivity.startActivity(intent);
                }
            });
        }
        else if (status == 4) {
            btnJoin.setVisibility(View.GONE);
        }

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatFragment chatFragment = new ChatFragment();
                chatFragment.setEvent(eventInfo);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void MinusPoints() {
        UserInfo tempUser = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");
        FirebaseFirestore.getInstance().collection("users").document(tempUser.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    int temp = Integer.parseInt(document.getString("points")) - 200;
                    FirebaseFirestore.getInstance().collection("users").document(tempUser.getUuid()).update("points", Integer.toString(temp));
//                    Toast.makeText(EventDetailFragment.this,"200 Reward points Minus!",Toast.LENGTH_LONG).show();
                    EventListFragment eventListFragment = new EventListFragment();
//                    eventListFragment.setEvent(eventInfo);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventListFragment).addToBackStack(null).commit();
                }
            }
        });
    }

    public void setPosition(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
    }

    public void setStatus(int status){
        //Didn't join before == 0
        //Joined == 1
        //Review == 2
        //Organiser Edit == 3
        //Organiser View == 4
        this.status = status;
    }
}