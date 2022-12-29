package com.example.umlife;

import android.graphics.Color;
import android.os.Bundle;

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

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    UserInfo userInfo;
    FragmentActivity fragmentActivity;

    ImageView IVEventImage;
    TextView TVEventName;
    TextView EventOverallRating;
    RecyclerView RVShowAllReview;

    RecyclerView.LayoutManager ReviewRVLayoutManager;
    ListAllReviewAdapter listAllReviewAdapter;
    LinearLayoutManager VerticalLayout;


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
        items.add("Most relevant");
        items.add("Newest");
        items.add("All reviews");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("item", (String)adapterView.getItemAtPosition(i));
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Picasso.get().load(eventInfo.getmImageUrl()).into(IVEventImage);
        TVEventName.setText(eventInfo.getEventName());
        EventOverallRating.setText(eventInfo.getOverallRating().toString());

        ReviewRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVShowAllReview.setLayoutManager(ReviewRVLayoutManager);
        listAllReviewAdapter = new ListAllReviewAdapter(eventInfo, userInfo, fragmentActivity);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVShowAllReview.setLayoutManager(VerticalLayout);
        RVShowAllReview.setAdapter(listAllReviewAdapter);

        return view;
    }

    public void setEvent (EventInfo eventInfo, UserInfo userInfo, FragmentActivity fragmentActivity){
        this.eventInfo = eventInfo;
        this.userInfo = userInfo;
        this.fragmentActivity = fragmentActivity;
    }
}