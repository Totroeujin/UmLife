package com.example.umlife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.EventInfo;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EventOrganisedFragment extends Fragment {

    // Recycler View object
    RecyclerView RVEventOrg;

    List<EventInfo> events = new ArrayList<>();

    // Layout manager
    RecyclerView.LayoutManager RVEventOrgLayoutManager;

    LinearLayoutManager VerticalLayout;

    FirebaseFirestore db;

    private TabLayout tabLayout;
    ViewPager2 viewPager;
    EventOrganisedAdapter eventOrgAdapter;

    public EventOrganisedFragment() {
        // Required empty public constructor
    }

    public static EventOrganisedFragment newInstance(String param1, String param2) {
        EventOrganisedFragment fragment = new EventOrganisedFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_organised, container, false);

        tabLayout = view.findViewById(R.id.TbLEventOrg);
        viewPager = view.findViewById(R.id.PagerEventOrg);

        tabLayout.addTab(tabLayout.newTab().setText("Ongoing"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));

        eventOrgAdapter = new EventOrganisedAdapter(this);
        viewPager.setAdapter(eventOrgAdapter);
        viewPager.setSaveEnabled(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }
}