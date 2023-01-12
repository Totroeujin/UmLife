package com.example.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.example.model.Review;
import com.example.model.UserInfo;
import com.example.profile.ListAllReviewFragment;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventOrganisedListAdapter extends RecyclerView.Adapter<EventOrganisedListAdapter.EventOrgView> {

    private UserInfo userInfo;
    private List<EventInfo> eventList;
    private FragmentActivity fragmentActivity;
    private int tabPosition;
    private FirebaseFirestore firestore;

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
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setPosition(event);
                        eventDetailFragment.setFragmentActivity(fragmentActivity);
                        eventDetailFragment.setStatus(3);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                    } else {
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setPosition(event);
                        eventDetailFragment.setStatus(4);
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                    }
                }
            });

            BtnEventOrgReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userInfo = (UserInfo) fragmentActivity.getIntent().getSerializableExtra("userInfo");
                    List<Review> reviewList = new ArrayList<>();
                    firestore = FirebaseFirestore.getInstance();
                    firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.getString("profileImage") != null) {
                                    userInfo.setProfileImage(document.getString("profileImage"));
                                }
                                firestore.collection("reviews").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if(!queryDocumentSnapshots.isEmpty()){
                                            reviewList.clear();
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for(DocumentSnapshot d : list){
                                                Review review = d.toObject(Review.class);
                                                review.setReviewId(d.getId());
                                                if(review.getOrganiserId().equals(userInfo.getUuid()))
                                                    reviewList.add(review);
                                            }
                                            ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                                            listAllReviewFragment.setEvent(userInfo, reviewList, fragmentActivity);
                                            fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).addToBackStack(null).commit();
                                        }
                                        else{
                                            Toast.makeText(fragmentActivity.getApplicationContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(fragmentActivity.getApplicationContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
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
        holder.TVEventOrgNum.setText("Total Participant : "+String.valueOf(event.getParticipation()));
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
        int limit = 10;
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

