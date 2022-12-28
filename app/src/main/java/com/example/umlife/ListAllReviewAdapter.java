package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAllReviewAdapter extends RecyclerView.Adapter<ListAllReviewAdapter.MyView> {

    private List<String> ReviewUsername;
    private List<Integer> ReviewUserImage;
    private List<Double> ReviewUserRating;
    private List<String> ReviewUserDate;
    private List<String> ReviewUserComment;

    public ListAllReviewAdapter(List<String> reviewUsername, List<Integer> reviewUserImage, List<Double> reviewUserRating, List<String> reviewUserDate, List<String> reviewUserComment) {
        ReviewUsername = reviewUsername;
        ReviewUserImage = reviewUserImage;
        ReviewUserRating = reviewUserRating;
        ReviewUserDate = reviewUserDate;
        ReviewUserComment = reviewUserComment;
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

        holder.ReviewUsername.setText(ReviewUsername.get(position));
        holder.ReviewUserImage.setImageResource(ReviewUserImage.get(position));
        holder.ReviewUserRating.setRating(ReviewUserRating.get(position).floatValue());
        holder.ReviewUserDate.setText(ReviewUserDate.get(position));
        holder.ReviewUserComment.setText(ReviewUserComment.get(position));
    }

    @Override
    public int getItemCount() {
        return ReviewUsername.size();
    }
}
