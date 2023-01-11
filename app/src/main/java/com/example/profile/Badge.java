package com.example.profile;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.umlife.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Badge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Badge extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FirebaseFirestore db;
    TextView userPoint;

    TextView statusgold;
    TextView statusplat;
    TextView statusmas;

    // badges image
    ImageView goldBadges;
    ImageView platinumBadges;
    ImageView masterBadges;

    public Badge() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment badge.
     */
    // TODO: Rename and change types and number of parameters
    public static Badge newInstance(String param1, String param2) {
        Badge fragment = new Badge();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_badge, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        userPoint = view.findViewById(R.id.userPoint);
        statusgold = view.findViewById(R.id.statusgold);
        statusplat = view.findViewById(R.id.statusplat);
        statusmas = view.findViewById(R.id.statusmas);

        goldBadges = view.findViewById(R.id.gold_badges);
        platinumBadges = view.findViewById(R.id.badge_platinum);
        masterBadges = view.findViewById(R.id.badge_master);

        SetRewardPoints();


    }

    private void SetRewardPoints() {
        db = FirebaseFirestore.getInstance();
        UserInfo userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");
        db.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceType")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    int temp = Integer.parseInt(document.getString("points"));
                    userPoint.setText(temp +" Points");

                    if(temp>=1000 && temp<=5000){
                        statusgold.setText(" Achieved");
                        statusgold.setTextColor(Color.parseColor("#03C04A"));
                        statusplat.setText(" Not Achieved");
                        statusplat.setTextColor(Color.parseColor("#FF0000"));
                        statusmas.setText(" Not Achieved");
                        statusmas.setTextColor(Color.parseColor("#FF0000"));
                    }
                    else if(temp>=5000 && temp<=10000){
                        statusgold.setText(" Achieved");
                        statusgold.setTextColor(Color.parseColor("#03C04A"));
                        statusplat.setText(" Achieved");
                        statusplat.setTextColor(Color.parseColor("#03C04A"));
                        statusmas.setText(" Not Achieved");
                        statusmas.setTextColor(Color.parseColor("#FF0000"));
                    }
                    else if(temp>=10000){
                        statusgold.setText(" Achieved");
                        statusgold.setTextColor(Color.parseColor("#03C04A"));
                        statusplat.setText(" Achieved");
                        statusplat.setTextColor(Color.parseColor("#03C04A"));
                        statusmas.setText(" Achieved");
                        statusmas.setTextColor(Color.parseColor("#03C04A"));
                    }else{
                        statusgold.setText(" Not Achieved");
                        statusgold.setTextColor(Color.parseColor("#FF0000"));
                        statusplat.setText(" Not Achieved");
                        statusplat.setTextColor(Color.parseColor("#FF0000"));
                        statusmas.setText(" Not Achieved");
                        statusmas.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
            }

        });
    }
}