package com.example.umlife;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

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

    //Button
    Button editProfile;
    Button myReview;

    //ProfileImage
    CircleImageView profilePicture;

    //Database ref
    FirebaseFirestore firestore;


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
            editProfile = view.findViewById(R.id.editProfile);
            profilePicture = view.findViewById(R.id.profilePageImage);
            myReview = view.findViewById(R.id.myReviewIcon);

            //get string from firestore
            firestore = FirebaseFirestore.getInstance();
            firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.getString("profileImage")!= null){
                            Uri temp = Uri.parse(document.getString("profileImage"));
                            //Toast.makeText(getActivity(),temp.toString(),Toast.LENGTH_LONG).show();
                            Picasso.get().load(temp).into(profilePicture);
                            //profilePicture.setImageURI(temp);
                        }
                    }
                }
            });

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

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditProfileFragment editProfileFragment = new EditProfileFragment();
                    editProfileFragment.setUserInfo(userInfo);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, editProfileFragment).addToBackStack(null).commit();
                }
            });

            myReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListAllReviewFragment listAllReviewFragment = new ListAllReviewFragment();
                    listAllReviewFragment.myReview(userInfo);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, listAllReviewFragment).addToBackStack(null).commit();
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