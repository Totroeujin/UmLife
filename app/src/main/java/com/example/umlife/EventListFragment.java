package com.example.umlife;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.umlife.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
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

    // Recycler View object
    RecyclerView RVMyEvent;
    RecyclerView RVTrendingEvent;

    Event event;

    // Layout Manager
    RecyclerView.LayoutManager MyEventRVLayoutManager;
    RecyclerView.LayoutManager TrendingRVLayoutManager;

    // adapter class object
    MyEventAdapter myEventAdapter;
    TrendingEventAdapter trendingEventAdapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    LinearLayoutManager VerticalLayout;

    Button btnMyEventViewAll;
    Button btnTrendingViewAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        //createEventArrayList();

        btnMyEventViewAll = view.findViewById(R.id.BtnMyEventViewAll);
        btnMyEventViewAll.setPaintFlags(btnMyEventViewAll.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        btnTrendingViewAll = view.findViewById(R.id.BtnTrendingViewAll);
        btnTrendingViewAll.setPaintFlags(btnTrendingViewAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnTrendingViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllEventListFragment allEventListFragment = new AllEventListFragment();
                allEventListFragment.setEvent(event, getActivity());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMainActivity, allEventListFragment).addToBackStack(null).commit();

            }
        });

        RVMyEvent = view.findViewById(R.id.myEventList);
        RVTrendingEvent = view.findViewById(R.id.trendingEventList);

        if(event != null) {
            MyEventRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            RVMyEvent.setLayoutManager(MyEventRVLayoutManager);
            myEventAdapter = new MyEventAdapter(event.getName(), event.getImage());
            HorizontalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
            RVMyEvent.setLayoutManager(HorizontalLayout);
            RVMyEvent.setAdapter(myEventAdapter);

            TrendingRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            RVTrendingEvent.setLayoutManager(TrendingRVLayoutManager);
            trendingEventAdapter = new TrendingEventAdapter(event.getName(), event.getImage(), event.getDate(), event.getVenue(), event.getNumberOfParticipants(), getActivity(), event);
            VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
            RVTrendingEvent.setLayoutManager(VerticalLayout);
            RVTrendingEvent.setAdapter(trendingEventAdapter);

        }

        return view;
    }

    public void createEventArrayList(){
        ArrayList<String> eventName = new ArrayList<>();
        for(int i=0; i<3; i++){
            eventName.add("EduFair");
            eventName.add("UMH");
            eventName.add("Odyssey");
        }
        ArrayList<Integer> eventImage = new ArrayList<>();
        ArrayList<String> eventDate = new ArrayList<>();
        ArrayList<String> eventVenue = new ArrayList<>();
        ArrayList<Integer> numOfParticipants = new ArrayList<>();
        ArrayList<String> organiserName = new ArrayList<>();
        ArrayList<Integer> organiserImage = new ArrayList<>();
        ArrayList<String> registrationDate = new ArrayList<>();
        ArrayList<Double> rating = new ArrayList<>();
        ArrayList<Integer> numOfRatings = new ArrayList<>();
        ArrayList<String> info = new ArrayList<>();

        ArrayList<String> review_username = new ArrayList<>();
        review_username.add("Eren");
        review_username.add("Ringmaster");
        review_username.add("KingKong");

        ArrayList<Integer> review_image = new ArrayList<>();
        review_image.add(R.drawable.minions);
        review_image.add(R.drawable.ic_baseline_person_24);
        review_image.add(R.drawable.icon_event_joined);

        ArrayList<Double> review_rating = new ArrayList<>();
        review_rating.add(3.5);
        review_rating.add(4.0);
        review_rating.add(2.0);

        ArrayList<String> review_date = new ArrayList<>();
        review_date.add("23 November 2022");
        review_date.add("14 December 2022");
        review_date.add("30 December 2022");

        ArrayList<String> review_comment = new ArrayList<>();
        review_comment.add("This event is fun but not too many titans. I hope you can add more of this for next event.");
        review_comment.add("I love this event. It fit for my styles and the music really is a jam!");
        review_comment.add("I hope this event can improve more");

        ArrayList<Review> reviews = new ArrayList<>();

        for (int i=0; i<9; i++){
            reviews.add(new Review(review_username, review_image, review_rating, review_date, review_comment));
        }


        for(int i=0; i<9; i++){
            eventImage.add(R.drawable.eventimage);
            eventDate.add("31 December 2023");
            eventVenue.add("DTC, UM");
            numOfParticipants.add(100);
            organiserName.add("DonkeyKong");
            organiserImage.add(R.drawable.minions);
            registrationDate.add("5 - 11 December 2023");
            rating.add(5.0);
            numOfRatings.add(15);
            info.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
        }


        event = new Event(eventName, eventImage, eventDate, eventVenue, numOfParticipants, organiserName, organiserImage, registrationDate, rating,
                numOfRatings, info, reviews);
    }

}