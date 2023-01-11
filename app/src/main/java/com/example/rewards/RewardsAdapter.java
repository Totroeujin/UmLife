package com.example.rewards;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.model.Reward;

import java.util.List;

public class RewardsAdapter extends FragmentStateAdapter {

    private List<Reward> rewards;

    public RewardsAdapter(Fragment fragment) {
        super(fragment);
    }

    public RewardsAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Reward> rewards) {
        super(fragmentManager, lifecycle);
        this.rewards = rewards;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new RewardListFragment(position);
    }

    // Set limit for tab list items
    @Override
    public int getItemCount() {
        return 2;
    }
}

