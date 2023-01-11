package com.example.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Review;
import com.example.model.UserInfo;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAllReviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListAllReviewFragment() {
        // Required empty public constructor
    }

    public static ListAllReviewFragment newInstance(String param1, String param2) {
        ListAllReviewFragment fragment = new ListAllReviewFragment();
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

    private Spinner spinner;
    FragmentActivity fragmentActivity;

    ImageView IVOrganiserImage;
    TextView TVOrganiserName;
    TextView OrganiserOverallRating;
    RecyclerView RVShowAllReview;

    RecyclerView.LayoutManager ReviewRVLayoutManager;
    ListAllReviewAdapter listAllReviewAdapter;
    LinearLayoutManager VerticalLayout;

    List<Review> reviewList = new ArrayList<>();
    String choice = "";
    UserInfo userInfo;

    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_all_review, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        IVOrganiserImage = view.findViewById(R.id.IVAllReviewOrganiserImage);
        TVOrganiserName = view.findViewById(R.id.TVAllReviewOrganiserName);
        OrganiserOverallRating = view.findViewById(R.id.TVAllReviewOverallRating);
        RVShowAllReview = view.findViewById(R.id.RVShowAllReview);

        spinner = view.findViewById(R.id.spinner);

        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();
        items.add("All reviews");
        items.add("Most relevant");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        Picasso.get().load(userInfo.getProfileImage())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(IVOrganiserImage);
        TVOrganiserName.setText(userInfo.getUsername());
        double overallRating = 0;
        if(reviewList.size() >0) {
            for (int i = 0; i < reviewList.size(); i++) {
                overallRating += reviewList.get(i).getRating();
            }
            overallRating /= reviewList.size();
        }

        OrganiserOverallRating.setText(String.format("%.2f", overallRating));

        ReviewRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVShowAllReview.setLayoutManager(ReviewRVLayoutManager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = adapterView.getItemAtPosition(i).toString();
                listAllReviewAdapter = new ListAllReviewAdapter(userInfo, reviewList, choice, fragmentActivity);
                VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                RVShowAllReview.setLayoutManager(VerticalLayout);
                RVShowAllReview.setAdapter(listAllReviewAdapter);
                Toast.makeText(getActivity(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void setEvent (UserInfo userInfo, List<Review> reviewList, FragmentActivity fragmentActivity){
        this.userInfo = userInfo;
        this.reviewList = reviewList;
        this.fragmentActivity = fragmentActivity;
    }

    public void myReview (UserInfo userInfo){
        this.userInfo = userInfo;
        db = FirebaseFirestore.getInstance();
        db.collection("reviews").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    reviewList.clear();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        Review review = d.toObject(Review.class);
                        review.setReviewId(d.getId());
                        if(review.getOrganiserId().equals(userInfo.getUuid()))
                            reviewList.add(review);
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
    }
}