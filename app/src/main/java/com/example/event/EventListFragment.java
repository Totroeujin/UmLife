package com.example.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CircleImageView CIVEventOrg;

    public EventListFragment() {
        // Required empty public constructor
    }

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
    RecyclerView RVEventList;

    List<EventInfo> eventInfoList = new ArrayList<>();

    // Layout Manager
    GridLayoutManager layoutManager;

    // adapter class object
    AllEventAdapter eventListAdapter;

    FirebaseFirestore db;

    UserInfo userInfo;

    CircleImageView eventJoined;
    CircleImageView eventCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        //Retrieve bundle from activity
        Bundle bundle = this.getArguments();
        if (bundle != null){
            userInfo = (UserInfo) bundle.getSerializable("userInfo");
        }

        eventJoined = view.findViewById(R.id.EventJoinedIcon);
        eventCreate = view.findViewById(R.id.createEventIcon);

        eventJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventJoinedFragment eventJoinedFragment = new EventJoinedFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventJoinedFragment).addToBackStack(null).commit();
            }
        });

        eventCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateOrEditEventActivity.class);
                intent.putExtra("userInfo", userInfo);
                startActivity(intent);
            }
        });

        RVEventList = view.findViewById(R.id.eventList);
        db = FirebaseFirestore.getInstance();
        db.collection("events").whereEqualTo("status", "Open").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                eventInfoList.clear();
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        EventInfo event = d.toObject(EventInfo.class);
                        event.setEventId(d.getId());
                        try{
                            Date eventDate = new SimpleDateFormat("ddMMyyyy").parse(event.getEventDate());
                            Date today = new Date();
                            if(eventDate.after(today))
                                eventInfoList.add(event);
                        } catch (ParseException e){
                            System.out.println(e);
                        }
                        try{
                            Date eventDate = new SimpleDateFormat("dd-MM-y").parse(event.getEventDate());
                            Date today = new Date();
                            if(eventDate.after(today))
                                eventInfoList.add(event);
                        } catch (ParseException e){
                            System.out.println(e);
                        }
                        try{
                            Date eventDate = new SimpleDateFormat("dd/M/y").parse(event.getEventDate());
                            Date today = new Date();
                            if(eventDate.after(today))
                                eventInfoList.add(event);
                        } catch (ParseException e){
                            System.out.println(e);
                        }
                    }
                    eventListAdapter = new AllEventAdapter(getActivity(), eventInfoList);
                    layoutManager = new GridLayoutManager(getContext(), 1) {
                        @Override
                        public boolean canScrollVertically() {
                            return true;
                        }
                    };
                    RVEventList.setLayoutManager(layoutManager);
                    RVEventList.setAdapter(eventListAdapter);
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


        CIVEventOrg = view.findViewById(R.id.CIVEventOrg);
        CIVEventOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventOrganisedFragment eventOrganisedFragment = new EventOrganisedFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventOrganisedFragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

}