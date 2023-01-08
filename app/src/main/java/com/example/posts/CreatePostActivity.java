package com.example.posts;

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

import com.example.model.Post;
import com.example.model.UserInfo;
import com.example.umlife.HomePageActivity;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreatePostActivity extends AppCompatActivity {

    //local image
    private ImageView postImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    // need to install imageUrl;
    private Uri mPostImageUri;

    //Storage database
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseFirestore mFirebaseRef;


    //editText from XML to receive string type data
    EditText postDetail;

    //button
    Button post;

    //UserInfo
    UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //Get userInfo package
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Storage database References
        mStorageRef = FirebaseStorage.getInstance().getReference("posts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("posts");
        mFirebaseRef = FirebaseFirestore.getInstance();

        //Image
        postImage = findViewById(R.id.postImage);

        //Button
        post = findViewById(R.id.button);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadUserPost();
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
            mPostImageUri = data.getData();

            Picasso.get().load(mPostImageUri)
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo)
                .into(postImage);
            //Testing
            Toast.makeText(CreatePostActivity.this, mPostImageUri.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void UploadUserPost(){
        if(mPostImageUri != null){
            //Testing to upload with .toString()
            String wantedUri = System.currentTimeMillis() + "." + getFileExtension(mPostImageUri);
            StorageReference fileReference = mStorageRef.child(wantedUri);

            fileReference.putFile(mPostImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    postDetail = findViewById(R.id.postDetail);
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Post uploadPost = new Post(userInfo.getUuid(), postDetail.getText().toString(), uri.toString());

                            //Firebase storing by upload file to "posts" collection
                            mFirebaseRef.collection("posts").add(uploadPost).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //What to do after putting file to Firebase
                                    Toast.makeText(CreatePostActivity.this, "Upload successfully", Toast.LENGTH_LONG).show();
                                    DirectUser(CreatePostActivity.this, HomePageActivity.class);
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
            Toast.makeText(this, "Upload Failed!",Toast.LENGTH_LONG).show();
        }
    }

    private void DirectUser(android.content.Context currentPage, Class<?> nextPage) {
        Intent intent = new Intent(currentPage, nextPage);
        intent.putExtra("userInfo", userInfo);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}