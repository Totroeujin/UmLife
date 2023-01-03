package com.example.umlife;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
    RecyclerView RVTrendingEvent;

    List<EventInfo> eventInfoList = new ArrayList<>();

    // Layout Manager
    RecyclerView.LayoutManager TrendingRVLayoutManager;

    // adapter class object
    TrendingEventAdapter trendingEventAdapter;

    // Linear Layout Manager
    LinearLayoutManager VerticalLayout;

    Button btnTrendingViewAll;

    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        RVTrendingEvent = view.findViewById(R.id.trendingEventList);
        db = FirebaseFirestore.getInstance();
        db.collection("events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                eventInfoList.clear();
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        EventInfo event = d.toObject(EventInfo.class);
                        event.setEventId(d.getId());
                        eventInfoList.add(event);
                    }
                    trendingEventAdapter.notifyDataSetChanged();
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

        TrendingRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVTrendingEvent.setLayoutManager(TrendingRVLayoutManager);
        trendingEventAdapter = new TrendingEventAdapter(getActivity(), eventInfoList);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVTrendingEvent.setLayoutManager(VerticalLayout);
        RVTrendingEvent.setAdapter(trendingEventAdapter);

        btnTrendingViewAll = view.findViewById(R.id.BtnTrendingViewAll);
        btnTrendingViewAll.setPaintFlags(btnTrendingViewAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnTrendingViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllEventListFragment allEventListFragment = new AllEventListFragment();
                allEventListFragment.setEvent(eventInfoList, getActivity());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, allEventListFragment).addToBackStack(null).commit();

            }
        });

        return view;
    }

}