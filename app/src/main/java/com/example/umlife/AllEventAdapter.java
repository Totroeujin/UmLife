package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.example.model.Participant;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllEventAdapter extends RecyclerView.Adapter<AllEventAdapter.MyView> {
    List<EventInfo> eventInfoList;
    FragmentActivity fragmentActivity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.TVTrendingEventName.setText(eventInfoList.get(position).getEventName());
        Picasso.get().load(eventInfoList.get(position).getmImageUrl()).into(holder.IVTrendingImage);
        //holder.TVTrendingNumberParticipants.setText(String.valueOf(AllEventNumberParticipantList.get(position)));
    }

    @Override
    public int getItemCount() {
        return  eventInfoList.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView TVTrendingEventName;
        ImageView IVTrendingImage;
        TextView TVTrendingDateVenue;
        CardView event;

        public MyView (View view){
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
                        eventDetailFragment.setPosition(eventInfoList.get(pos));
                        db.collection("participants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                boolean joined = false;
                                UserInfo user = (UserInfo) fragmentActivity.getIntent().getSerializableExtra("userInfo");
                                if(!queryDocumentSnapshots.isEmpty()){
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot d : list){
                                        Participant participant = d.toObject(Participant.class);
                                        if(participant.getUuid().equals(user.getUuid()) && participant.getEventID().equals(eventInfoList.get(pos).getEventId())){
                                            joined = true;
                                            break;
                                        }
                                    }
                                    System.out.println(joined);
                                    if(joined == true)
                                        eventDetailFragment.setStatus(1);
                                    else
                                        eventDetailFragment.setStatus(0);
                                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).addToBackStack(null).commit();
                                }
                                else{
                                    Toast.makeText(view.getContext(), "No data fetched", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }

    }

    public AllEventAdapter(FragmentActivity fragmentActivity, List<EventInfo> eventInfoList){
        this.eventInfoList =eventInfoList;
        this.fragmentActivity = fragmentActivity;
    }

}
