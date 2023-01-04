package com.example.umlife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.Reward;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

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

    List<Reward> rewards = new ArrayList<>();
    List<Reward> redeemedRewards = new ArrayList<>();
    List< String> redeemedRewardsName = new ArrayList<>();

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

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("User document: ", document.toString());

                    if(document.exists()) {
                        UserInfo userInfo = document.toObject(UserInfo.class);
                        Log.d("User document: ", userInfo.toString());
                        redeemedRewardsName = userInfo.getRedeemedRewards();

                        db.collection("rewards").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if(!querySnapshot.isEmpty()) {
                                        if(tabPosition == 0) {
                                            for(DocumentSnapshot d : querySnapshot){
                                                if (!d.exists()) continue;
                                                Reward reward = d.toObject(Reward.class);
                                                if (!redeemedRewardsName.contains(reward.getRewardName()))
                                                    rewards.add(reward);
                                            }
                                        } else {
                                            for(DocumentSnapshot d : querySnapshot){
                                                if (!d.exists()) continue;
                                                Reward reward = d.toObject(Reward.class);
                                                if (redeemedRewardsName.contains(reward.getRewardName()))
                                                    redeemedRewards.add(reward);
                                            }
                                        }
                                        rewardListAdapter.notifyDataSetChanged();
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Failed to fetch rewards", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed to fetch rewards", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
        });

        RVRewardsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVRewards.setLayoutManager(RVRewardsLayoutManager);
        if (tabPosition == 0)
            rewardListAdapter = new RewardListAdapter(getActivity(), rewards);
        else
            rewardListAdapter = new RewardListAdapter(getActivity(), redeemedRewards);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVRewards.setLayoutManager(VerticalLayout);
        RVRewards.setAdapter(rewardListAdapter);

        return view;
    }
}