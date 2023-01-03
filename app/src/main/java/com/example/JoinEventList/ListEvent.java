package com.example.JoinEventList;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.model.EventInfo;
import com.example.model.UserInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.umlife.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ListEvent extends Fragment {

//    ArrayList<ListEventJoinedRow> listEventJoinedRows = new ArrayList<>();

    private static final int NUM_PAGES = 2;

    private TabLayout tabLayout;
    ViewPager2 viewPager;
    EventJoinedAdapter eventJoinedAdapter;

    private ArrayList<listEventTest> eventList;
    private RecyclerView eventView;

    private FragmentStateAdapter pagerAdapter;
    private int tabPosition;

    public ListEvent() {

    }

    public ListEvent(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public static ListEvent newInstance(String param1, String param2) {
        ListEvent fragment = new ListEvent();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_list_event, container, false);



        tabLayout = view.findViewById(R.id.joineventTab);
        viewPager = view.findViewById(R.id.listeventPager);
        eventView = view.findViewById(R.id.eventJoinedList);

//        tabLayout.addTab(tabLayout.newTab().setText("Joined"));
//        tabLayout.addTab(tabLayout.newTab().setText("History"));


        eventJoinedAdapter = new EventJoinedAdapter(this);
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

    private void setAdapter() {
        ListEventAdapter adapter = new ListEventAdapter(eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
          eventView.setLayoutManager(layoutManager);
        eventView.setItemAnimator(new DefaultItemAnimator());
        eventView.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
    }


}