package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingEventAdapter extends RecyclerView.Adapter<TrendingEventAdapter.MyView> {
    private List<EventInfo> eventInfoList;
    private FragmentActivity fragmentActivity;

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_event_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        EventInfo eventInfo = eventInfoList.get(position);
        holder.TVTrendingEventName.setText(eventInfo.getEventName());
        holder.TVTrendingDateVenue.setText(String.format("%s\n%s", eventInfo.getEventDate(), eventInfo.getEventVenue()));
        Picasso.get().load(eventInfo.getmImageUrl()).into(holder.IVTrendingImage);
    }

    @Override
    public int getItemCount() {
        if(eventInfoList == null)
            return 0;
        else {
            int limit = 4;
            return Math.min(eventInfoList.size(), limit);
        }
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView TVTrendingEventName;
        ImageView IVTrendingImage;
        TextView TVTrendingDateVenue;
        TextView TVTrendingNumberParticipants;
        ImageButton btnEventDetail;

        public MyView (View view){
            super(view);
            TVTrendingEventName = view.findViewById(R.id.TVTrendingName);
            IVTrendingImage = view.findViewById(R.id.IVTrendingImage);
            TVTrendingDateVenue = view.findViewById(R.id.TVDateVenue);
            TVTrendingNumberParticipants = view.findViewById(R.id.TVNumberParticipants);

            btnEventDetail = view.findViewById(R.id.BtnEventDetail);
            btnEventDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos > RecyclerView.NO_POSITION){
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setPosition(eventInfoList.get(pos), fragmentActivity);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                    }
                }
            });
        }

    }

    public TrendingEventAdapter(FragmentActivity fragmentActivity, List<EventInfo> eventInfoList){
        this.fragmentActivity = fragmentActivity;
        this.eventInfoList = eventInfoList;
    }

}
