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

import com.example.umlife.Event;
import com.example.umlife.EventDetailFragment;
import com.example.umlife.R;

import java.util.List;

public class TrendingEventAdapter extends RecyclerView.Adapter<TrendingEventAdapter.MyView> {
    private List<String> TrendingNameList;
    private List<Integer> TrendingImageList;
    private List<String> TrendingDateList;
    private List<String> TrendingVenueList;
    private List<Integer> TrendingNumberParticipantList;
    Event event;
    FragmentActivity fragmentActivity;

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_event_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {

        holder.TVTrendingEventName.setText(TrendingNameList.get(position));
        holder.IVTrendingImage.setImageResource(TrendingImageList.get(position));
        holder.TVTrendingDateVenue.setText(String.format("%s\n%s", TrendingDateList.get(position), TrendingVenueList.get(position)));
        holder.TVTrendingNumberParticipants.setText(String.valueOf(TrendingNumberParticipantList.get(position)));
    }

    @Override
    public int getItemCount() {
        int limit = 4;
        return Math.min(TrendingNameList.size(), limit);
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
                        eventDetailFragment.setPosition(pos, event, fragmentActivity);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMainActivity, eventDetailFragment).addToBackStack(null).commit();
                    }
                }
            });
        }

    }

    public TrendingEventAdapter(List<String> trendingNameList, List<Integer> trendingImageList, List<String> trendingDateList, List<String> trendingVenueList, List<Integer> trendingNumberParticipantList, FragmentActivity fragmentActivity, Event event) {
        this.event = event;
        this.fragmentActivity = fragmentActivity;
        TrendingNameList = trendingNameList;
        TrendingImageList = trendingImageList;
        TrendingDateList = trendingDateList;
        TrendingVenueList = trendingVenueList;
        TrendingNumberParticipantList = trendingNumberParticipantList;
    }



}
