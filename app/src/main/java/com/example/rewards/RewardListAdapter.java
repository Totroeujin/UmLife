package com.example.rewards;

import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Reward;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RewardListAdapter extends RecyclerView.Adapter<RewardListAdapter.RewardView> {

    FirebaseFirestore db;
    FirebaseUser curUser;
    int curUserPoints;
    String curUserId;

    private List<Reward> rewardList;
    private FragmentActivity fragmentActivity;
    private int tabPosition;

    AlertDialog.Builder builder;

    public RewardListAdapter(FragmentActivity fragmentActivity, List<Reward> rewardList, int tabPosition){
        this.fragmentActivity = fragmentActivity;
        this.rewardList = rewardList;
        this.tabPosition = tabPosition;
    }

    @NonNull
    @Override
    public RewardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item, parent, false);
        return new RewardView(itemView);
    }

    @Override
    public void onBindViewHolder(final RewardView holder, final int position) {

        Reward reward = rewardList.get(position);
        holder.TVRewardName.setText(reward.getRewardName());
        holder.TVRewardDescription.setText(reward.getRewardDescription());
        Picasso.get().load(reward.getRewardImageUrl())
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo)
                .into(holder.IVRewardImage);


//        if (tabPosition == 1) {
//            holder.BtnRedeem.setText("View");
//        } else if(tabPosition == 0) {
//            if (curUserPoints < Integer.parseInt(reward.getRequiredPoints())) {
//                holder.BtnRedeem.setBackgroundColor(Color.GRAY);
//            } else {
//                holder.BtnRedeem.setBackgroundColor(Color.parseColor("#FF6600"));
//            }
//        }
    }

    @Override
    public int getItemCount() {
        if(rewardList == null)
            return 0;
        int limit = 5;
        return Math.min(rewardList.size(), limit);
    }

    public class RewardView extends RecyclerView.ViewHolder{
        TextView TVRewardName;
        TextView TVRewardDescription;
        ImageView IVRewardImage;
        Button BtnRedeem;


        public RewardView(View view){
            super(view);

            db = FirebaseFirestore.getInstance();

            curUser = FirebaseAuth.getInstance().getCurrentUser();
            curUserId = curUser.getUid();

            db.collection("users").document(curUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        curUserPoints = Integer.parseInt(documentSnapshot.getString("points"));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("User status", "Unable to fetch user");
                }
            });

            // Pop up box
            builder = new AlertDialog.Builder(view.getContext());

            TVRewardName = view.findViewById(R.id.TVRewardName);
            TVRewardDescription = view.findViewById(R.id.TVRewardDescription);
            IVRewardImage = view.findViewById(R.id.IVRewardImage);
            BtnRedeem = view.findViewById(R.id.BtnRedeem);

            if(BtnRedeem != null) {
                BtnRedeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Reward reward = rewardList.get(position);
                        String rewardName = reward.getRewardName();

                        if(position == 0) {
                            if(curUserPoints < Integer.parseInt(reward.getRequiredPoints())) {
                                Toast.makeText(fragmentActivity.getApplicationContext(), "You do not have enough points to redeem", Toast.LENGTH_LONG).show();
                                builder.setTitle("Error");
                                String msg = curUserPoints + " " + Integer.parseInt(reward.getRequiredPoints());
                                builder.setMessage(msg);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                //
                                // Add new redeemed rewards
                                db.collection("users").document(curUserId).update("redeemedRewards", FieldValue.arrayUnion(rewardName)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // "Refresh" the reward list to display updated data
                                        RewardSystemFragment rewardSystemFragment = new RewardSystemFragment();
                                        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.container, rewardSystemFragment);
                                        ft.commit();
                                    }
                                });
                                builder.setTitle("Quote");
                                builder.setMessage(reward.getQuote());
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }
                });
            }
        }
    }
}
