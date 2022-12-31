package com.example.umlife;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.UserInfo;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //UserInfo
    UserInfo userInfo = new UserInfo();

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        //Retrieve bundle from activity
        Bundle bundle = this.getArguments();
        if (bundle != null){
            userInfo = (UserInfo) bundle.getSerializable("userInfo");
        }
    }

    //Log out
    TextView logout;
    CircleImageView logoutIcon;

    //Create Event
    TextView createEvent;
    CircleImageView createEventIcon;

    //Event Joined
    TextView joinedEvent;
    CircleImageView joinedEventIcon;

    //Creating View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        //this is the Testing fragment clickable site

        // Inflate the layout for this fragment
        return v;
    }

    //View object to change
    TextView username;
    TextView email;

    //View complete created
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            //Assign id to variable
            logout = view.findViewById(R.id.logOut);
            logoutIcon = view.findViewById(R.id.logOutIcon);
            createEvent = view.findViewById(R.id.createEvent);
            createEventIcon = view.findViewById(R.id.createEventIcon);

            String FILE_NAME = "myFile";

            //Define action onClick
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Delete shared preferences to avoid auto login
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME, 0);
                    sharedPreferences.edit().clear().commit();

                    getActivity().finish();
                }
            });

            logoutIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Delete shared preferences to avoid auto login
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME, 0);
                    sharedPreferences.edit().clear().commit();

                    getActivity().finish();
                }
            });

            //Define action onClick
            createEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CreateOrEditEventActivity.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);
                }
            });

            createEventIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CreateOrEditEventActivity.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);
                }
            });

            //Define action onClick
            joinedEventIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), list_event.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);
                }
            });

            joinedEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), list_event.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);
                }
            });

            /////Changing text in XML
            username = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            username.setText("Username: " + userInfo.getUsername());
            email.setText("Email: "+ userInfo.getEmail());

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}