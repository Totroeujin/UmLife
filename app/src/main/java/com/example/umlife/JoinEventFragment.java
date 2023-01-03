package com.example.umlife;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.EventInfo;
import com.example.model.Participant;
import com.example.model.Review;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinEventFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        //FindView Category
        name = view.findViewById(R.id.joinName);
        email = view.findViewById(R.id.joinEmail);
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
        description.setText(eventInfo.getEventDetail());
        dueDate.setText(eventInfo.getEndRegistration());
        Picasso.get().load(eventInfo.getmImageUrl()).into(eventImage);

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

                        //After upload participant entry
                        //Below is the action after upload
                    }
                });
            }
        });

        return view;
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