package com.example.umlife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.model.Reward;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class RewardListAdapter extends RecyclerView.Adapter<RewardListAdapter.RewardView> {

    FirebaseFirestore db;
    FirebaseUser curUser;

    private List<Reward> rewardList;
    private FragmentActivity fragmentActivity;
    private int tabPosition;

    public class RewardView extends RecyclerView.ViewHolder{
        TextView TVRewardName;
        TextView TVRewardDescription;
        ImageView IVRewardImage;
        Button BtnRedeem;

        public RewardView(View view){
            super(view);

            TVRewardName = view.findViewById(R.id.TVRewardName);
            TVRewardDescription = view.findViewById(R.id.TVRewardDescription);
            IVRewardImage = view.findViewById(R.id.IVRewardImage);
            BtnRedeem = view.findViewById(R.id.BtnRedeem);

            curUser = FirebaseAuth.getInstance().getCurrentUser();
            String curUserId = curUser.getUid();

            // Button
            if (tabPosition == 0 && BtnRedeem != null) {
                BtnRedeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Reward reward = rewardList.get(position);
                        String rewardName = reward.getRewardName();
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
                    }
                });
            }
        }
    }

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

        db = FirebaseFirestore.getInstance();

        Reward reward = rewardList.get(position);
        holder.TVRewardName.setText(reward.getRewardName());
        holder.TVRewardDescription.setText(reward.getRewardDescription());
        Picasso.get().load(reward.getRewardImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.IVRewardImage);

        if (tabPosition == 1) {
            holder.BtnRedeem.setVisibility(View.INVISIBLE);
            holder.BtnRedeem.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        if(rewardList == null)
            return 0;
        int limit = 5;
        return Math.min(rewardList.size(), limit);
    }
}
