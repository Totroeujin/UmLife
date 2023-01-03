package com.example.JoinEventList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class EventJoinedAdapter extends FragmentStateAdapter {
    public EventJoinedAdapter(@NonNull Event2 fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Event1();
            case 1 :
                return new Event2();
            default:
                return new Event1();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
