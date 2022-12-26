package com.example.umlife;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends AppCompatActivity {
    FirebaseFirestore firestore;

    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;

    // show all post from all user
    PostFragment postFragment = new PostFragment();

    // show all ongoing event
    EventListFragment eventListFragment = new EventListFragment();

    // reward system
    RewardSystemFragment rewardSystemFragment = new RewardSystemFragment();

    // profile of the user
    ProfileFragment profileFragment = new ProfileFragment();

    //Storepreferences
//    SharedPreferences sharedPreferences = (SharedPreferences) getSharedPreferences("myFile", MODE_PRIVATE);
//    String email = sharedPreferences.getString("email","");
//    String password = sharedPreferences.getString("password", "");
//    String uuid;
//    String email;
    //UserInfo
    UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        //email = getIntent().getStringExtra("email");
        //System.out.println(email+" "+password);
        //Testing get data from server
        firestore = FirebaseFirestore.getInstance();
        //set path to find specific documents
        DocumentReference documentReference = firestore.collection("users").document(userInfo.getUuid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String Tag = "DocSnippets";
                if (task.isSuccessful()){
                    //Copy down the information from firebase
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        //Do casting
                        //String temp = documentSnapshot.getData().toString();
                        //Toast.makeText((Context) HomePageActivity.this, temp, Toast.LENGTH_LONG).show();
                    }else{
                        Log.d(Tag, "No such document");
                    }
                }else{
                    Log.d(Tag, "get failed with ", task.getException());
                }
            }
        });

        // search relative layout
        relativeLayout = findViewById(R.id.Main);

        try {
            // bottom navigation
            bottomNavigationView = findViewById(R.id.bottomnavigation);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, postFragment).commit();

            bottomNavigateListener();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void bottomNavigateListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //Setup bundle to pass userInfo
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", userInfo);
                switch (item.getItemId()) {
                    case R.id.home:
                        postFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, postFragment).commit();
                        return true;
                    case R.id.event:
                        eventListFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, eventListFragment).commit();
                        return true;
                    case R.id.reward:
                        rewardSystemFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, rewardSystemFragment).commit();
                        return true;
                    case R.id.profile:
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}