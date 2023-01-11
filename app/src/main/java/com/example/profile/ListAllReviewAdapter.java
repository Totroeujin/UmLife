package com.example.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Review;
import com.example.model.UserInfo;
import com.example.umlife.MainActivity;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ListAllReviewAdapter extends RecyclerView.Adapter<ListAllReviewAdapter.MyView> {

    List<Review> reviewList;
    UserInfo organiserInfo;
    FragmentActivity fragmentActivity;
    String choice;

    public ListAllReviewAdapter(UserInfo organiserInfo, List<Review> review, String choice, FragmentActivity fragmentActivity){
        this.reviewList = review;
        this.choice = choice;
        this.organiserInfo = organiserInfo;
        this.fragmentActivity = fragmentActivity;

    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView ReviewUsername;
        ImageView ReviewUserImage;
        RatingBar ReviewUserRating;
        TextView ReviewUserDate;
        TextView ReviewUserComment;
        TextView ReviewLikeCount;
        TextView ReviewDislikeCount;
        ImageButton LikeButton;
        ImageButton DislikeButton;
        FirebaseFirestore db;

        public MyView (View view){
            super(view);
            ReviewUsername = view.findViewById(R.id.TVReviewUsername);
            ReviewUserImage = view.findViewById(R.id.IVReviewUserImage);
            ReviewUserRating = view.findViewById(R.id.ReviewUserRating);
            ReviewUserDate = view.findViewById(R.id.TVReviewUserDate);
            ReviewUserComment = view.findViewById(R.id.TVReviewUserComment);
            ReviewLikeCount = view.findViewById(R.id.TVReviewLikeCount);
            ReviewDislikeCount = view.findViewById(R.id.TVReviewDislikeCount);
            LikeButton = view.findViewById(R.id.BtnReviewUserLike);
            DislikeButton = view.findViewById(R.id.BtnReviewUserDislike);

            LikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db = FirebaseFirestore.getInstance();
                    int pos = getAdapterPosition();
                    if (pos > RecyclerView.NO_POSITION){
                        db.collection("reviews").document(reviewList.get(pos).getReviewId()).
                                set(new Review(reviewList.get(pos).getRating(),
                                        reviewList.get(pos).getComment(),  reviewList.get(pos).getOrganiserId(), reviewList.get(pos).getUserId(), reviewList.get(pos).getUsername(),
                                        reviewList.get(pos).getEventId(),
                                        reviewList.get(pos).getDate(), reviewList.get(pos).getLikeCount()+1, reviewList.get(pos).getDislikeCount())).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(fragmentActivity, "You have like this comment!", Toast.LENGTH_SHORT).show();
                                        ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                                        listAllReviewFragment.setEvent(organiserInfo, reviewList, fragmentActivity);
                                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).commit();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(fragmentActivity, "Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });

            DislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db = FirebaseFirestore.getInstance();
                    int pos = getAdapterPosition();
                    if (pos > RecyclerView.NO_POSITION){
                        db.collection("reviews").document(reviewList.get(pos).getReviewId()).
                                set(new Review(reviewList.get(pos).getRating(),
                                        reviewList.get(pos).getComment(), reviewList.get(pos).getOrganiserId(), reviewList.get(pos).getUserId(), reviewList.get(pos).getUsername(),
                                        reviewList.get(pos).getEventId(), reviewList.get(pos).getDate(), reviewList.get(pos).getLikeCount(),
                                        reviewList.get(pos).getDislikeCount()+1)).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(fragmentActivity, "You have dislike this comment!", Toast.LENGTH_SHORT).show();
                                        ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                                        listAllReviewFragment.setEvent(organiserInfo, reviewList, fragmentActivity);
                                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).commit();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(fragmentActivity, "Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });

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

        Picasso.get().load(reviewList.get(position).getUserImage())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(holder.ReviewUserImage);
        //holder.ReviewUserImage.setImageResource();
        holder.ReviewUsername.setText(reviewList.get(position).getUsername());
        holder.ReviewUserRating.setRating(reviewList.get(position).getRating());
        holder.ReviewUserDate.setText(reviewList.get(position).getDate());
        holder.ReviewUserComment.setText(reviewList.get(position).getComment());
        holder.ReviewLikeCount.setText(String.valueOf(reviewList.get(position).getLikeCount()));
        holder.ReviewDislikeCount.setText(String.valueOf(reviewList.get(position).getDislikeCount()));
    }

    @Override
    public int getItemCount() {
        if (choice.equals("Most relevant")) {
            int limit = 10;
            Collections.sort(reviewList);
            Collections.reverse(reviewList);
            return Math.min(reviewList.size(), limit);
        }
        else
            return reviewList.size();
    }
}
