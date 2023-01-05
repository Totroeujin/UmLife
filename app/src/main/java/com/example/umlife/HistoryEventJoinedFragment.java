package com.example.umlife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.model.EventInfo;
import com.example.model.Participant;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OngoingEventJoinedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryEventJoinedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryEventJoinedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventJoinedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OngoingEventJoinedFragment newInstance(String param1, String param2) {
        OngoingEventJoinedFragment fragment = new OngoingEventJoinedFragment();
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
    RecyclerView MyEventList;

    List<EventInfo> eventJoinedList = new ArrayList<>();
    UserInfo user;
    List<String> eventJoinedID = new ArrayList<>();
    List<Participant> participantList = new ArrayList<>();

    // Layout Manager
    GridLayoutManager layoutManager;

    // adapter class object
    HistoryEventJoinedAdapter eventJoinedAdapter;

    Button BtnOngoing;
    Button BtnHistory;

    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_event_joined, container, false);

        user = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");

        MyEventList = view.findViewById(R.id.eventJoinedList);
        //BtnOngoing = view.findViewById(R.id.BtnEventJoinedOngoing);
        //BtnHistory = view.findViewById(R.id.BtnEventJoinedHistory);
        //BtnHistory.setBackgroundResource(R.drawable.rounded_corner_orange);
        //BtnOngoing.setBackgroundResource(R.drawable.rounded_corner_grey);

        db = FirebaseFirestore.getInstance();

        db.collection("participants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        Participant participant = d.toObject(Participant.class);
                        participantList.add(participant);
                    }

                    for(int i=0; i<participantList.size(); i++){
                        if(participantList.get(i).getUuid().equals(user.getUuid())){
                            eventJoinedID.add(participantList.get(i).getEventID());
                        }
                    }

                    db.collection("events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty()){
                                for(int i=0; i<eventJoinedID.size(); i++) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {
                                        if(d.getId().equals(eventJoinedID.get(i))){
                                            EventInfo event = d.toObject(EventInfo.class);
                                            event.setEventId(d.getId());
                                            try {
                                                Date eventDate = new SimpleDateFormat("dd-MM-yy").parse(event.getEventDate());
                                                Date today = new Date();
                                                if(eventDate.before(today)){
                                                    eventJoinedList.add(event);
                                                    break;
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    eventJoinedAdapter = new HistoryEventJoinedAdapter(eventJoinedList, getActivity());
                                    layoutManager = new GridLayoutManager(getContext(), 1) {
                                        @Override
                                        public boolean canScrollVertically() {
                                            return true;
                                        }
                                    };
                                    MyEventList.setLayoutManager(layoutManager);
                                    MyEventList.setAdapter(eventJoinedAdapter);
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
        BtnOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OngoingEventJoinedFragment ongoing = new OngoingEventJoinedFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, ongoing).addToBackStack(null).commit();
            }
        });
         **/

        return view;
    }



}