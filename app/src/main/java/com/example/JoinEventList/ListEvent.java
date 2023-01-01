package com.example.JoinEventList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.model.EventInfo;
import com.example.model.UserInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.umlife.R;
import com.google.android.material.tabs.TabLayout;


public class ListEvent extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    EventJoinedAdapter eventJoinedAdapter;

    EventInfo eventInfo;
    UserInfo userInfo;

    public ListEvent() {

    }

    public static ListEvent newInstance(String param1, String param2) {
        ListEvent fragment = new ListEvent();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_event, container, false);
    }

//    Bundle bundle = new Bundle();
//                bundle.putSerializable("userInfo", userInfo);
//                bundle.putSerializable("eventInfo", eventInfo);
//
//                Fragment JoinEvent = new JoinEventFragment();
//                JoinEvent.setArguments(bundle);
//                getParentFragmentManager().beginTransaction().replace(R.id.container, JoinEvent).commit();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        tabLayout = view.findViewById(R.id.TabLayout);
//        viewPager2 = view.findViewById(R.id.)
//        EventJoinedAdapter = new EventJoinedAdapter(this);
    }
}