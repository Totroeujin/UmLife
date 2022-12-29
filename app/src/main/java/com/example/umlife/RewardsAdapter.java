package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Reward;

import java.util.Date;
import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardView> {
    private List<Reward> rewardList;
    private FragmentActivity fragmentActivity;

    public class RewardView extends RecyclerView.ViewHolder{
        TextView TVRewardName;
        TextView TVRewardDescription;
        ImageView IVRewardImage;

        public RewardView(View view){
            super(view);

            TVRewardName = view.findViewById(R.id.TVRewardName);
            TVRewardDescription = view.findViewById(R.id.TVRewardDescription);
            IVRewardImage = view.findViewById(R.id.IVRewardImage);
        }
    }

    public RewardsAdapter(FragmentActivity fragmentActivity, List<Reward> rewardList){
        this.fragmentActivity = fragmentActivity;
        this.rewardList = rewardList;
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
        // Picasso.get().load(reward.getImageUrl()).into(holder.IVTrendingImage);
    }

    @Override
    public int getItemCount() {
        if(rewardList == null)
            return 0;
        int limit = 4;
        return Math.min(rewardList.size(), limit);
    }
}

