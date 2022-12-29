package com.example.umlife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Reward;
import com.example.model.UploadPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardSystemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardSystemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RewardSystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RewardSystemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardSystemFragment newInstance(String param1, String param2) {
        RewardSystemFragment fragment = new RewardSystemFragment();
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
    RecyclerView RVRewards;

    List<Reward> rewards = new ArrayList<Reward>();

    // Layout manager
    RecyclerView.LayoutManager RVRewardsLayoutManager;

    // Adapter
    RewardsAdapter rewardsAdapter;

    LinearLayoutManager VerticalLayout;

    FirebaseFirestore db;

    ViewPager2 viewPager;
    RewardsCollectionAdapter rewardsCollectionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reward_system, container, false);

        RVRewards = view.findViewById(R.id.rewardList);

        db = FirebaseFirestore.getInstance();
        db.collection("rewards").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        Reward reward = d.toObject(Reward.class);
                        rewards.add(reward);
                    }
                    rewardsAdapter.notifyDataSetChanged();
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

        RVRewardsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVRewards.setLayoutManager(RVRewardsLayoutManager);
        rewardsAdapter = new RewardsAdapter(getActivity(), rewards);
        VerticalLayout = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        RVRewards.setLayoutManager(VerticalLayout);
        RVRewards.setAdapter(rewardsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            rewardsCollectionAdapter = new RewardsCollectionAdapter(this);
            viewPager = view.findViewById(R.id.rewardsPager);
            viewPager.setAdapter(rewardsCollectionAdapter);

            TabLayout tabLayout = view.findViewById(R.id.tab_layout);
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText("OBJECT " + (position + 1))
            ).attach();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static class RewardsCollectionAdapter extends FragmentStateAdapter {
        public RewardsCollectionAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Return a NEW fragment instance in createFragment(int)
            Fragment fragment = new RewardsObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(RewardsObjectFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    // Instances of this class are fragments representing a single
// object in our collection.
    public static class RewardsObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_collection_object, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        }
    }
}