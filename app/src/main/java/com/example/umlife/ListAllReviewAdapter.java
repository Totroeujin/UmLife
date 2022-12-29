package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.EventInfo;
import com.example.model.UserInfo;

import java.util.List;

public class ListAllReviewAdapter extends RecyclerView.Adapter<ListAllReviewAdapter.MyView> {

    EventInfo eventInfo;
    UserInfo userInfo;
    FragmentActivity fragmentActivity;

    public ListAllReviewAdapter(EventInfo eventInfo, UserInfo userInfo, FragmentActivity fragmentActivity){
        this.eventInfo = eventInfo;
        this.userInfo = userInfo;
        this.fragmentActivity = fragmentActivity;
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView ReviewUsername;
        ImageView ReviewUserImage;
        RatingBar ReviewUserRating;
        TextView ReviewUserDate;
        TextView ReviewUserComment;

        public MyView (View view){
            super(view);
            ReviewUsername = view.findViewById(R.id.TVReviewUsername);
            ReviewUserImage = view.findViewById(R.id.IVReviewUserImage);
            ReviewUserRating = view.findViewById(R.id.ReviewUserRating);
            ReviewUserDate = view.findViewById(R.id.TVReviewUserDate);
            ReviewUserComment = view.findViewById(R.id.TVReviewUserComment);
        }
    }

    @NonNull
    @Override
    public ListAllReviewAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_review_item, parent, false);
        return new ListAllReviewAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllReviewAdapter.MyView holder, int position) {

        //holder.ReviewUsername.setText();
        //holder.ReviewUserImage.setImageResource();
        holder.ReviewUserRating.setRating(eventInfo.getReview().get(position).getRating());
        holder.ReviewUserDate.setText(eventInfo.getReview().get(position).getDate());
        holder.ReviewUserComment.setText(eventInfo.getReview().get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return eventInfo.getReview().size();
    }
}
