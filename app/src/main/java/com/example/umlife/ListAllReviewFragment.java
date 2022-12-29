package com.example.umlife;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.EventInfo;
import com.example.model.Review;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAllReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAllReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListAllReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAllReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    EventInfo eventInfo;
    List<UserInfo> userInfoList;
    FragmentActivity fragmentActivity;

    ImageView IVEventImage;
    TextView TVEventName;
    TextView EventOverallRating;
    RecyclerView RVShowAllReview;

    RecyclerView.LayoutManager ReviewRVLayoutManager;
    ListAllReviewAdapter listAllReviewAdapter;
    LinearLayoutManager VerticalLayout;

    List<Review> reviewList = new ArrayList<>();
    String choice = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_all_review, container, false);

        IVEventImage = view.findViewById(R.id.IVAllReviewEventImage);
        TVEventName = view.findViewById(R.id.TVAllReviewEventName);
        EventOverallRating = view.findViewById(R.id.TVAllReviewOverallRating);
        RVShowAllReview = view.findViewById(R.id.RVShowAllReview);

        spinner = view.findViewById(R.id.spinner);

        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();
        items.add("All reviews");
        items.add("Most relevant");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        Picasso.get().load(eventInfo.getmImageUrl()).into(IVEventImage);
        TVEventName.setText(eventInfo.getEventName());
        double overallRating = 0;
        if(reviewList.size() >0) {
            for (int i = 0; i < reviewList.size(); i++) {
                overallRating += reviewList.get(i).getRating();
            }
            overallRating /= reviewList.size();
        }

        EventOverallRating.setText(String.format("%.2f", overallRating));

        ReviewRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVShowAllReview.setLayoutManager(ReviewRVLayoutManager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = adapterView.getItemAtPosition(i).toString();
                listAllReviewAdapter = new ListAllReviewAdapter(eventInfo, userInfoList, reviewList, choice, fragmentActivity);
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

    public void setEvent (EventInfo eventInfo, List<UserInfo> userInfoList, List<Review> reviewList, FragmentActivity fragmentActivity){
        this.eventInfo = eventInfo;
        this.userInfoList = userInfoList;
        this.reviewList = reviewList;
        this.fragmentActivity = fragmentActivity;
    }
}