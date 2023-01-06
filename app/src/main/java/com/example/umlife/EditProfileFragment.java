package com.example.umlife;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //userInfo
    UserInfo userInfo;

    //Firebase
    FirebaseFirestore firestore;

    //Storage database
    StorageReference mStorageRef;

    //Flow control
    Boolean refExists;
    Boolean clickExists;
    Uri wantedUri;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        mStorageRef = FirebaseStorage.getInstance().getReference("profiles");
        refExists = Boolean.FALSE;
        clickExists = Boolean.FALSE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    //ImageUpload
    ImageView profileImage;
    Uri uriProfileImage;

    //EditText
    EditText profileName;
    EditText profileAge;
    EditText profileEmail;
    EditText profilePhone;
    EditText profileCourse;
    EditText profileAddress;
    EditText profileDes;
    EditText profileBio;

    //Button
    Button button;

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

        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileAge = view.findViewById(R.id.profileAge);
        profilePhone = view.findViewById(R.id.profilePhone);
        profileCourse = view.findViewById(R.id.profileCourse);
        profileAddress = view.findViewById(R.id.profileAddress);
        profileDes = view.findViewById(R.id.profileDescription);
        profileBio =view.findViewById(R.id.profileBio);
        button = view.findViewById(R.id.profilebutton);

        profileImage.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {openFileChooser();clickExists = Boolean.TRUE;}});

        profileName.setText(userInfo.getUsername());
        profileEmail.setText(userInfo.getEmail());
        firestore = FirebaseFirestore.getInstance();
        //Find if age exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("age") != null){
                        profileAge.setText(document.getString("age"));
                    }else {
                        profileAge.setError("Input AGE");
                    }
                }
            }
        });
        //Find if phone exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("phone") != null){
                        profilePhone.setText(document.getString("phone"));
                    }else {
                        profilePhone.setError("Input PHONE");
                    }
                }
            }
        });
        //Find if course exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("course") != null){
                        profileCourse.setText(document.getString("course"));
                    }else {
                        profileCourse.setError("Input COURSE");
                    }
                }
            }
        });
        //Find if Address exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("address") != null){
                        profileAddress.setText(document.getString("address"));
                    }else {
                        profileAddress.setError("Input ADDRESS");
                    }
                }
            }
        });
        //Find if imageURL exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("profileImage")!= null){
                        String temp = document.getString("profileImage");

                        wantedUri = Uri.parse(temp);
                        Picasso.get().load(wantedUri)
                            .placeholder(R.drawable.empty_photo)
                            .error(R.drawable.empty_photo)
                            .into(profileImage);
//                        Glide.with(view).load(Uri.parse(temp)).into(profileImage);
//                        profileImage.setImageURI(Uri.parse(temp));
//                        Log.d("EditProfile_Image", temp);
                        refExists = Boolean.TRUE;
                    }else{
                        Toast.makeText(getActivity(),"Please Upload Profile Image",Toast.LENGTH_LONG).show();
                        refExists = Boolean.FALSE;
                    }
                }
            }
        });
        //If description exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("description") != null){
                        profileDes.setText(document.getString("description"));
                    }
                }
            }
        });
        //If Bio exists
        firestore.collection("users").document(userInfo.getUuid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getString("bio") != null){
                        profileAddress.setText(document.getString("bio"));
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {UpdateProfile();}});}

    private void UpdateProfile() {
        //If any validation, put here

        //Connect to database
        if(refExists && clickExists){
            FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(wantedUri)).delete();
        }
        firestore.collection("users").document(userInfo.getUuid()).update("username",profileName.getText().toString(),"age",profileAge.getText().toString(),
                "phone",profilePhone.getText().toString(),"course",profileCourse.getText().toString(),"address",profileAddress.getText().toString(),
                "description",profileDes.getText().toString(),"bio",profileBio.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if(uriProfileImage!=null){
                //Put image into storage
                String wantedUri = System.currentTimeMillis() + "." + getFileExtension(uriProfileImage);
                StorageReference fileReferences = mStorageRef.child(wantedUri);

                fileReferences.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                firestore.collection("users").document(userInfo.getUuid()).update("profileImage",uri);
                            }
                        });
                    }
                });}
                Toast.makeText(getActivity(),"Update Success!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Update Failed",Toast.LENGTH_LONG).show();
            }
        });
        //Force re-login
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1 && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            Picasso.get().load(uriProfileImage)
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo)
                .into(profileImage);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void setUserInfo(UserInfo userInfo1){
        this.userInfo = userInfo1;
    }
}