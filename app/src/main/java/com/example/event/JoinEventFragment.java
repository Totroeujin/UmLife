package com.example.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.EventInfo;
import com.example.model.Participant;
import com.example.model.UserInfo;
import com.example.umlife.HomePageActivity;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class JoinEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JoinEventFragment() {
        // Required empty public constructor
    }

    public static JoinEventFragment newInstance(String param1, String param2) {
        JoinEventFragment fragment = new JoinEventFragment();
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

    //EditText
    EditText name;
    EditText email;
    EditText age;
    EditText phoneNum;
    EditText course;
    EditText address;

    UserInfo userInfo;
    EventInfo eventInfo;

    //ImageView
    ImageView eventImage;
    //TextView
    TextView eventName;
    TextView description;
    TextView dueDate;
    //Btn
    Button button;

    //FirebaseFirestore
    FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_event, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //FindView Category
        name = view.findViewById(R.id.joinName);
        email = view.findViewById(R.id.joinEmail);
        eventName = view.findViewById(R.id.eventname);
        eventImage = view.findViewById(R.id.eventImage);
        dueDate = view.findViewById(R.id.duedate);
        description = view.findViewById(R.id.description);
        age = view.findViewById(R.id.joinAge);
        phoneNum = view.findViewById(R.id.joinPhone);
        course = view.findViewById(R.id.joinCourse);
        address = view.findViewById(R.id.joinAddress);

        button = view.findViewById(R.id.button);

        //Set page
        name.setText(userInfo.getUsername());
        email.setText(userInfo.getEmail());
        eventName.setText(eventInfo.getEventName());
        description.setText(eventInfo.getEventDetail());
        dueDate.setText(eventInfo.getEndRegistration());
        Picasso.get().load(eventInfo.getmImageUrl())
            .placeholder(R.drawable.empty_photo)
            .error(R.drawable.empty_photo)
            .into(eventImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create participant object and obtain information
                Participant participant = new Participant(userInfo.getUuid(),eventInfo.getEventId(),name.getText().toString(),age.getText().toString(),email.getText().toString(),
                        phoneNum.getText().toString(),course.getText().toString(),address.getText().toString());

                //Upload participant object
                firestore = FirebaseFirestore.getInstance();
                firestore.collection("participants").add(participant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //This is the code to get document id
                        //documentReference.getId();
                        AddPoints();
                        DocumentReference eventDocRef = firestore.collection("events").document(eventInfo.getEventId());
                        eventDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Map<String, Object> data = new HashMap<>();
                                if(documentSnapshot.contains("participation"))
                                    data.put("participation", Integer.toString(Integer.parseInt((String) documentSnapshot.getString("participation"))+1));
                                else
                                    data.put("participation", "1");
                                eventDocRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                
                                            }
                                        });
                                Toast.makeText(getActivity(),"200 Reward points added!",Toast.LENGTH_LONG).show();
                                //finish fragment activity
                                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                                eventDetailFragment.setPosition(eventInfo);
                                eventDetailFragment.setStatus(1);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, eventDetailFragment).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });

            }
        });

        return view;
    }

    private void AddPoints() {
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    int temp = Integer.parseInt(document.getString("points")) + 200;
                    firestore.collection("users").document(userInfo.getUuid()).update("points", Integer.toString(temp));

                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //EditText Object
        name = view.findViewById(R.id.joinName);
        email = view.findViewById(R.id.joinEmail);
        eventImage = view.findViewById(R.id.eventImage);
        dueDate = view.findViewById(R.id.duedate);
        description = view.findViewById(R.id.description);
        age = view.findViewById(R.id.joinAge);
        phoneNum = view.findViewById(R.id.joinPhone);
        course = view.findViewById(R.id.joinCourse);
        address = view.findViewById(R.id.joinAddress);

        //auto load information in user profile
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(task.isSuccessful()){
                    if(document.contains("username"))
                        name.setText(document.getString("username"));
                    if(document.contains("age"))
                        age.setText(document.getString("age"));
                    if(document.contains("phone"))
                        phoneNum.setText(document.getString("phone"));
                    if(document.contains("course"))
                        course.setText(document.getString("course"));
                    if(document.contains("address"))
                        address.setText(document.getString("address"));
                }
            }
        });
    }
    public void setEvent(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }

}