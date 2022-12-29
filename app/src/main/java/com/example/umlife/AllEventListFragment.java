package com.example.umlife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.EventInfo;
import com.example.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllEventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllEventListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllEventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllEventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllEventListFragment newInstance(String param1, String param2) {
        AllEventListFragment fragment = new AllEventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView RVAllEvent;
    LinearLayoutManager AllEventLayoutManager;
    AllEventAdapter allEventAdapter;
    List<EventInfo> eventInfoList;
    FragmentActivity fragmentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_event_list, container, false);

        RVAllEvent = view.findViewById(R.id.allEventList);
        AllEventLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        allEventAdapter = new AllEventAdapter(eventInfoList, fragmentActivity);
        RVAllEvent.setLayoutManager(AllEventLayoutManager);
        RVAllEvent.setAdapter(allEventAdapter);

        return view;
    }

    public void setEvent(List<EventInfo> eventInfoList, FragmentActivity fragmentActivity){
        this.eventInfoList = eventInfoList;
        this.fragmentActivity = fragmentActivity;
    }
}