package com.example.umlife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.Reward;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardListFragment extends Fragment {

    // Recycler View object
    RecyclerView RVRewards;

    List<Reward> rewards = new ArrayList<Reward>();

    // Layout manager
    RecyclerView.LayoutManager RVRewardsLayoutManager;

    LinearLayoutManager VerticalLayout;

    FirebaseFirestore db;

    RewardListAdapter rewardListAdapter;

    private int tabPosition;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RewardListFragment() {
        // Required empty public constructor
    }

    public RewardListFragment(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public static RewardListFragment newInstance(String param1, String param2) {
        RewardListFragment fragment = new RewardListFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reward_list, container, false);

        RVRewards = view.findViewById(R.id.rewardList);

        db = FirebaseFirestore.getInstance();

        if (tabPosition == 1) {
            db.collection("rewards").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d : list){
                            Reward reward = d.toObject(Reward.class);
                            rewards.add(reward);
                        }
                        rewardListAdapter.notifyDataSetChanged();
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
        } else if (tabPosition == 2) {
            // Get current user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();

        }


        RVRewardsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVRewards.setLayoutManager(RVRewardsLayoutManager);
        rewardListAdapter = new RewardListAdapter(getActivity(), rewards);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVRewards.setLayoutManager(VerticalLayout);
        RVRewards.setAdapter(rewardListAdapter);

        return view;
    }
}