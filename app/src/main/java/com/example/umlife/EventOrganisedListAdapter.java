package com.example.umlife;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventOrganisedListAdapter extends RecyclerView.Adapter<EventOrganisedListAdapter.EventOrgView> {

    private UserInfo userInfo;
    private List<EventInfo> eventList;
    private FragmentActivity fragmentActivity;
    private int tabPosition;

    public class EventOrgView extends RecyclerView.ViewHolder{
        TextView TVEventOrgName;
        TextView TVEventOrgNum;
        ImageView IVEventOrgImage;

        Button BtnEventOrgEdit;
        Button BtnEventOrgReview;

        public EventOrgView(View view){
            super(view);

            TVEventOrgName = view.findViewById(R.id.TVEventOrgName);
            TVEventOrgNum = view.findViewById(R.id.TVEventOrgNum);
            IVEventOrgImage = view.findViewById(R.id.IVEventOrgImg);
            BtnEventOrgEdit = view.findViewById(R.id.BtnEventOrgEdit);
            BtnEventOrgReview = view.findViewById(R.id.BtnEventOrgReview);

            BtnEventOrgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    EventInfo event = eventList.get(position);
                    if (tabPosition == 0) {
                        Intent intent = new Intent(fragmentActivity, CreateOrEditEventActivity.class);
                        intent.putExtra("targetEvent", event);
                        intent.putExtra("action", "edit");
                        fragmentActivity.startActivity(intent);
                    } else {
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setPosition(event);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                    }
                }
            });

            BtnEventOrgReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                    listAllReviewFragment.myReview(userInfo);
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    public EventOrganisedListAdapter(FragmentActivity fragmentActivity, List<EventInfo> eventList, int tabPosition){
        this.fragmentActivity = fragmentActivity;
        this.tabPosition = tabPosition;
        this.eventList = filterEvent(eventList);
    }

    @NonNull
    @Override
    public EventOrgView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_org_item, parent, false);
        return new EventOrgView(itemView);
    }

    @Override
    public void onBindViewHolder(final EventOrgView holder, final int position) {

        EventInfo event = eventList.get(position);
        holder.TVEventOrgName.setText(event.getEventName());
        holder.TVEventOrgNum.setText(String.valueOf(event.getParticipation()));
        Picasso.get().load(event.getmImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVEventOrgImage);

        if (tabPosition == 0) {
            holder.BtnEventOrgEdit.setText("Edit");
        } else if (tabPosition == 1) {
            holder.BtnEventOrgEdit.setText("Detail");
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void updateData(List<EventInfo> eventList) {
        this.eventList = filterEvent(eventList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(eventList == null)
            return 0;
        int limit = 5;
        return Math.min(eventList.size(), limit);
    }

    private List<EventInfo> filterEvent(List<EventInfo> eventList) {
        List<EventInfo> filteredEvents = new ArrayList<>();
        if(tabPosition == 0) {
            for (EventInfo event : eventList) {
                if (event.getStatus().equals("Open") || event.getStatus().equals("Closed")) {
                    filteredEvents.add(event);
                }
            }
        } else {
            for (EventInfo event : eventList) {
                if (event.getStatus().equals("Ended")) {
                    filteredEvents.add(event);
                }
            }
        }
        return filteredEvents;
    }

}

