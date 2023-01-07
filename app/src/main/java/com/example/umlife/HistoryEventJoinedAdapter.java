package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryEventJoinedAdapter extends RecyclerView.Adapter<HistoryEventJoinedAdapter.MyView>{

    List<EventInfo> joinedEventList = new ArrayList<>();
    List<String> eventJoinedID;
    FragmentActivity fragmentActivity;


    @NonNull
    @Override
    public HistoryEventJoinedAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new HistoryEventJoinedAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.TVTrendingEventName.setText(joinedEventList.get(position).getEventName());
        Picasso.get().load(joinedEventList.get(position).getmImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVTrendingImage);
    }


    @Override
    public int getItemCount() {
        return joinedEventList.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView TVTrendingEventName;
        ImageView IVTrendingImage;
        CardView event;
        public MyView (View view) {
            super(view);
            TVTrendingEventName = view.findViewById(R.id.TVTrendingName);
            IVTrendingImage = view.findViewById(R.id.IVTrendingImage);

            event = view.findViewById(R.id.trendingEventCardView);
            event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos > RecyclerView.NO_POSITION){
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setStatus(2);
                        eventDetailFragment.setPosition(joinedEventList.get(pos));
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                    }
                }
            });

        }
    }

    FirebaseFirestore db;
    public HistoryEventJoinedAdapter(List<EventInfo> joinedEventList, FragmentActivity fragmentActivity){
        this.joinedEventList = joinedEventList;
        this.fragmentActivity = fragmentActivity;

    }
}
