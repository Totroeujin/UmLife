package com.example.umlife;

import static com.google.common.io.Files.getFileExtension;

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

import com.example.model.UploadImage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreateOrEditEventActivity extends AppCompatActivity {

    //local image
    private ImageView eventImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    // need to install imageUrl;
    private Uri mImageUri;

    //Storage database
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    //editText from XML to receive string type data
    EditText eventName;
    EditText openRegistration;
    EditText endRegistration;
    EditText eventDetail;
    EditText organiserEmail;

    //button
    Button publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_event);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Storage database References
        mStorageRef = FirebaseStorage.getInstance().getReference("Events");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Events");



        //Image
        eventImage = findViewById(R.id.eventImage);

        //Button
        publish = findViewById(R.id.button);

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

                UploadImage();
            }
        });
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

            Picasso.get().load(mImageUri).into(eventImage);
            //Testing
            Toast.makeText(CreateOrEditEventActivity.this, mImageUri.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void UploadImage(){
        if(mImageUri != null){
            //Testing to upload with .toString()
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    EventInfo eventInfo = new EventInfo();
//                    eventInfo.setEventImage(mImageUri);
//                    String uploadId = ;
                    UploadImage uploadImage = new UploadImage(eventName.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(uploadImage);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    Log.i("UploadImage", "Image uploaded: " + progress);
                }
            });
        }else{
            Toast.makeText(this, "Upload Failed!",Toast.LENGTH_LONG).show();
        }
    }
}