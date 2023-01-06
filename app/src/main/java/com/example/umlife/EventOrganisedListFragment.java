package com.example.umlife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callbacks.QueryCompleteCallback;
import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventOrganisedListFragment extends Fragment {

    // Recycler View object
    RecyclerView RVEventOrg;

    List<EventInfo> events = new ArrayList<>();

    // Layout manager
    RecyclerView.LayoutManager RVEventOrgLayoutManager;

    LinearLayoutManager VerticalLayout;

    FirebaseFirestore db;
    FirebaseUser curUser;
    private UserInfo curUserInfo;

    EventOrganisedListAdapter eventOrganisedListAdapter;

    private int tabPosition;

    public EventOrganisedListFragment() {
        // Required empty public constructor
    }

    public EventOrganisedListFragment(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public static EventOrganisedListFragment newInstance(String param1, String param2) {
        EventOrganisedListFragment fragment = new EventOrganisedListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_organised_list, container, false);

        // Callback to update event list
        QueryCompleteCallback<EventInfo> queryCompleteCallback = new QueryCompleteCallback<EventInfo>() {
            @Override
            public void onQueryComplete(List<EventInfo> list) {
                eventOrganisedListAdapter.updateData(list);
            }
        };

        RVEventOrg = view.findViewById(R.id.RVEventOrg);

        db = FirebaseFirestore.getInstance();

        // Get current user
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        String curUserId = curUser.getUid();

        db.collection("users").document(curUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    curUserInfo = documentSnapshot.toObject(UserInfo.class);
                    eventOrganisedListAdapter.setUserInfo(curUserInfo);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        db.collection("events").whereEqualTo("organiserId", curUserId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots ) {
                        EventInfo event = documentSnapshot.toObject(EventInfo.class);
                        event.setEventId(documentSnapshot.getId());
                        events.add(event);
                        queryCompleteCallback.onQueryComplete(events);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to fetch events ", e.toString());
            }
        });

        RVEventOrgLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVEventOrg.setLayoutManager(RVEventOrgLayoutManager);
        eventOrganisedListAdapter = new EventOrganisedListAdapter(getActivity(), events, tabPosition);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVEventOrg.setLayoutManager(VerticalLayout);
        RVEventOrg.setAdapter(eventOrganisedListAdapter);

        return view;
    }
}