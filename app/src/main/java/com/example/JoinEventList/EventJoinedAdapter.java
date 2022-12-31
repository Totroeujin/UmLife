package com.example.JoinEventList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class EventJoinedAdapter extends FragmentStateAdapter {
    public EventJoinedAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new event_act_1();
            case 1 :
                return new event_act_2();
            default:
                return new event_act_1();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
