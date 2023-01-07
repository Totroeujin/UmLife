package com.example.umlife;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.model.EventInfo;

import java.util.List;

public class EventOrganisedAdapter extends FragmentStateAdapter {

    private List<EventInfo> events;

    public EventOrganisedAdapter(Fragment fragment) {
        super(fragment);
    }

    public EventOrganisedAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<EventInfo> events) {
        super(fragmentManager, lifecycle);
        this.events = events;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new EventOrganisedListFragment(position);
    }

    // Set limit for tab list items
    @Override
    public int getItemCount() {
        return 10;
    }
}

