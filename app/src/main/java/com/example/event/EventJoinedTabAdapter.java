package com.example.event;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class EventJoinedTabAdapter extends FragmentStateAdapter {

    public EventJoinedTabAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return new OngoingEventJoinedFragment();
        }
        return new HistoryEventJoinedFragment();
    }

    @Override
    public int getItemCount() {
        // Hardcoded, use lists
        return 10;
    }
}