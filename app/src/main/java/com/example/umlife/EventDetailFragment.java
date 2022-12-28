package com.example.umlife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailFragment newInstance(String param1, String param2) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Event event;
    Button btnContact;
    Button btnReview;
    Button btnJoin;
    int position;
    FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        btnContact = view.findViewById(R.id.BtnEventDetailContact);
        btnReview = view.findViewById(R.id.BtnEventDetailReview);
        btnJoin = view.findViewById(R.id.BtnEventDetailJoin);

        ImageView EventDetailImage = view.findViewById(R.id.IVEventDetailImage);
        TextView EventDetailName = view.findViewById(R.id.TVEventDetailTitle);
        ImageView OrganiserImage = view.findViewById(R.id.IVEventDetailOrganiser);
        TextView OrganiserName = view.findViewById(R.id.TVOrganiserName);
        TextView EventDetailDate = view.findViewById(R.id.TVEventDetailDate);
        TextView EventDetailVenue = view.findViewById(R.id.TVEventDetailVenue);
        TextView EventDetailRegistrationDate = view.findViewById(R.id.TVEventDetailRegistrationDate);
        RatingBar EventDetailRating = view.findViewById(R.id.EventDetailRatingBar);
        TextView Rating = view.findViewById(R.id.TVEventDetailRating);
        TextView EventRatingNumber = view.findViewById(R.id.EventDetailRatingNumber);
        TextView EventDetailInfo = view.findViewById(R.id.TVEventDetailInfo);


        EventDetailImage.setImageResource(event.getImage().get(position));
        EventDetailName.setText(event.getName().get(position));
        OrganiserImage.setImageResource(event.getOrganiserImage().get(position));
        OrganiserName.setText(event.getOrganiserName().get(position));
        EventDetailDate.setText(event.getDate().get(position));
        EventDetailVenue.setText(event.getVenue().get(position));
        EventDetailRegistrationDate.setText(event.getRegistrationDate().get(position));
        EventDetailRating.setRating(event.getRating().get(position).floatValue());
        Rating.setText(event.getRating().get(position).toString());
        EventRatingNumber.setText(event.getNumberOfRatings().get(position).toString());
        EventDetailInfo.setText(event.getInfo().get(position));

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                listAllReviewFragment.setPos(position, event);
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentEventDetail, listAllReviewFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void setPosition(int pos, Event event, FragmentActivity fragmentActivity){

        this.event = event;
        this.position = pos;
        this.fragmentActivity = fragmentActivity;
    }
}