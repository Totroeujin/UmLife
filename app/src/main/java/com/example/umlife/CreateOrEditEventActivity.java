package com.example.umlife;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.EventInfo;
import com.example.model.UploadEvent;
import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CreateOrEditEventActivity extends AppCompatActivity {

    //local image
    private ImageView eventImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    // need to install imageUrl;
    private Uri mImageUri;

    //Storage database
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseFirestore mFirebaseRef;
    private FirebaseUser curUser;


    //editText from XML to receive string type data
    EditText eventName;
    EditText eventDate;
    EditText eventVenue;
    EditText openRegistration;
    EditText endRegistration;
    EditText eventDetail;

    ImageView IVEventImage;

    //button
    Button publish;

    //UserInfo
    UserInfo userInfo;

    // Set action
    private String action = "create";

    // Intent
    Intent intent;

    String mImageUrl = "";
    EventInfo targetEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_event);

        mFirebaseRef = FirebaseFirestore.getInstance();
        intent = getIntent();

        eventName = findViewById(R.id.eventName);
        eventDate = findViewById(R.id.eventDate);
        eventVenue = findViewById(R.id.eventVenue);
        eventDetail = findViewById(R.id.eventDetail);
        openRegistration = findViewById(R.id.openRegistration);
        endRegistration = findViewById(R.id.endRegistration);
        IVEventImage = findViewById(R.id.eventImage);

        //Get userInfo package
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseRef.collection("users").document(curUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userInfo = documentSnapshot.toObject(UserInfo.class);
                userInfo.setUuid(documentSnapshot.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase user: ", "No current user");
            }
        });

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Storage database References
        mStorageRef = FirebaseStorage.getInstance().getReference("events");
        mDatabaseRef = FirebaseDatabase.getInstance("https://umlife-41693-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("events");
        mFirebaseRef = FirebaseFirestore.getInstance();


        //Image
        eventImage = findViewById(R.id.eventImage);

        //Button
        publish = findViewById(R.id.button);

        if(intent.getStringExtra("action") != null && intent.getStringExtra("action").equals("edit")) {
            publish.setText("Edit");
        }

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //What happen after "publish" was clicked
                //Toast.makeText(CreateOrEditEventActivity.this, mImageUri.toString(),Toast.LENGTH_LONG).show();
                //Text Retrieval
//                String eventName_ = eventName.getText().toString();
//                String openRegistration_ = openRegistration.getText().toString();
//                String endRegistration_ = endRegistration.getText().toString();
//                String eventDetail_ = eventDetail.getText().toString();
//                String organiserEmail_ = organiserEmail.getText().toString();

                //mStorageRef = FirebaseStorage.getInstance().getReference("Events");
                if(intent.getStringExtra("action") != null && intent.getStringExtra("action").equals(("edit"))) {
                    EditEvent();
                } else {
                    UploadEvent();
                }
            }
        });


        if (intent.getStringExtra("action") != null && intent.getStringExtra("action").equals("edit")) {
            targetEvent = intent.getParcelableExtra("targetEvent");
            if (targetEvent != null) {
                eventName.setText(targetEvent.getEventName());
                eventDate.setText(targetEvent.getEventDate());
                eventVenue.setText(targetEvent.getEventVenue());
                eventDetail.setText(targetEvent.getEventDetail());
                openRegistration.setText(targetEvent.getOpenRegistration());
                endRegistration.setText(targetEvent.getEndRegistration());
                Picasso.get().load(targetEvent.getmImageUrl())
                    .placeholder(R.drawable.empty_photo)
                    .error(R.drawable.empty_photo)
                    .into(IVEventImage);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri)
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo)
                .into(eventImage);
            //Testing
            Toast.makeText(CreateOrEditEventActivity.this, mImageUri.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void UploadEvent(){
        if(mImageUri != null){
            //Testing to upload with .toString()
            String wantedUri = System.currentTimeMillis() + "." + getFileExtension(mImageUri);
            StorageReference fileReference = mStorageRef.child(wantedUri);

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    eventName = findViewById(R.id.eventName);
//                    eventDate = findViewById(R.id.eventDate);
//                    eventVenue = findViewById(R.id.eventVenue);
//                    eventDetail = findViewById(R.id.eventDetail);
//                    openRegistration = findViewById(R.id.openRegistration);
//                    endRegistration = findViewById(R.id.endRegistration);

                    //TODO: Check
                    String status = "Open";

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UploadEvent uploadEvent = new UploadEvent(uri.toString(), eventName.getText().toString(),
                                    openRegistration.getText().toString(), endRegistration.getText().toString(), eventDetail.getText().toString(),
                                    userInfo.getUuid(), eventDate.getText().toString(), eventVenue.getText().toString(), status);

                            //Firebase storing by upload file to "events" collection
                            mFirebaseRef.collection("events").add(uploadEvent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //What to do after putting file to Firebase
                                    Toast.makeText(CreateOrEditEventActivity.this, "Upload successfully", Toast.LENGTH_LONG).show();
                                    DirectUser(CreateOrEditEventActivity.this, HomePageActivity.class);
                                }
                            });
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    Log.i("UploadImage", "Image uploaded: " + progress);
                }
            });
        }else{
            Toast.makeText(this, "Image Upload Failed!",Toast.LENGTH_LONG).show();
        }
    }

    private void EditEvent() {
        if(targetEvent != null) {
            if(mImageUri == null) {
                mImageUrl = targetEvent.getmImageUrl();
                Log.d("Event Id", targetEvent.getEventId());
                DocumentReference eventDocRef = mFirebaseRef.collection("events").document(targetEvent.getEventId());
                Log.d("Event doc ref ", eventDocRef.toString());
                Map<String, Object> data = new HashMap<>();
                data.put("mImageUrl", mImageUrl);
                data.put("eventName", eventName.getText().toString());
                data.put("eventDate", eventDate.getText().toString());
                data.put("eventVenue", eventVenue.getText().toString());
                data.put("openRegistration", openRegistration.getText().toString());
                data.put("endRegistration", endRegistration.getText().toString());
                data.put("eventDetail", eventDetail.getText().toString());
                eventDocRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateOrEditEventActivity.this, "Event updated successfully", Toast.LENGTH_LONG).show();
                        DirectUser(CreateOrEditEventActivity.this, HomePageActivity.class);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateOrEditEventActivity.this, "Event update failed", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                String wantedUri = System.currentTimeMillis() + "." + getFileExtension(mImageUri);
                StorageReference fileReference = mStorageRef.child(wantedUri);

                fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //TODO: Check
                        String status = "Open";

                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                DocumentReference eventDocRef = mFirebaseRef.collection("events").document(targetEvent.getEventId());
                                Log.d("Event doc ref ", eventDocRef.toString());
                                Map<String, Object> data = new HashMap<>();
                                data.put("mImageUrl", uri.toString());
                                data.put("eventName", eventName.getText().toString());
                                data.put("eventDate", eventDate.getText().toString());
                                data.put("eventVenue", eventVenue.getText().toString());
                                data.put("openRegistration", openRegistration.getText().toString());
                                data.put("endRegistration", endRegistration.getText().toString());
                                data.put("eventDetail", eventDetail.getText().toString());
                                eventDocRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CreateOrEditEventActivity.this, "Event updated successfully", Toast.LENGTH_LONG).show();
                                        DirectUser(CreateOrEditEventActivity.this, HomePageActivity.class);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CreateOrEditEventActivity.this, "Event update failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        Log.i("UploadImage", "Image uploaded: " + progress);
                    }
                });
            }


        } else {
            Log.d("Bye", targetEvent.toString());
        }
    }

    private void DirectUser(android.content.Context currentPage, Class<?> nextPage) {
        Intent intent = new Intent(currentPage, nextPage);
        intent.putExtra("userInfo", userInfo);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void setAction(String action) {
        this.action = action;
    }
}