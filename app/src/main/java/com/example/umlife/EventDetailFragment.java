package com.example.umlife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    EventInfo eventInfo;
    UserInfo userInfo;
    Button btnContact;
    Button btnReview;
    Button btnJoin;
    FragmentActivity fragmentActivity;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        btnContact = view.findViewById(R.id.BtnEventDetailContact);
        btnReview = view.findViewById(R.id.BtnEventDetailReview);
        btnJoin = view.findViewById(R.id.BtnEventDetailJoin);


        TextView EventDetailName = view.findViewById(R.id.TVEventDetailTitle);
        ImageView OrganiserImage = view.findViewById(R.id.IVEventDetailOrganiser);
        TextView OrganiserName = view.findViewById(R.id.TVOrganiserName);
        TextView EventDetailDate = view.findViewById(R.id.TVEventDetailDate);
        TextView EventDetailVenue = view.findViewById(R.id.TVEventDetailVenue);
        TextView EventDetailRegistrationDate = view.findViewById(R.id.TVEventDetailRegistrationDate);
        RatingBar EventDetailRating = view.findViewById(R.id.EventDetailRatingBar);
        TextView Rating = view.findViewById(R.id.TVEventDetailRating);
        TextView EventRatingNumber = view.findViewById(R.id.EventDetailRatingNumber);
        TextView EventDetailInfo = view.findViewById(R.id.TVEventDetailInfo);

        db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        String id = d.getId();
                        if(id.equals(eventInfo.getUuid())){
                            userInfo = d.toObject(UserInfo.class);
                            OrganiserName.setText(userInfo.getUsername());
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

        //Picasso.get().load(mImageUri).into(eventImage);

        EventDetailName.setText(eventInfo.getEventName());
        EventDetailDate.setText(eventInfo.getEventDate());
        EventDetailVenue.setText(eventInfo.getEventVenue());
        EventDetailRegistrationDate.setText(String.format("%s - %s", eventInfo.getOpenRegistration(), eventInfo.getEndRegistration()));
        //EventDetailRating.setRating(event.getRating().get(position).floatValue());
        //Rating.setText(event.getRating().get(position).toString());
        //EventRatingNumber.setText(event.getNumberOfRatings().get(position).toString());
        EventDetailInfo.setText(eventInfo.getEventDetail());

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                listAllReviewFragment.setEvent(eventInfo, userInfo, fragmentActivity);
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ImageView EventDetailImage = view.findViewById(R.id.IVEventDetailImage);
        Picasso.get().load(eventInfo.getmImageUrl()).into(EventDetailImage);
    }

    public void setPosition(EventInfo eventInfo, FragmentActivity fragmentActivity){
        this.eventInfo = eventInfo;
        this.fragmentActivity = fragmentActivity;
    }
}