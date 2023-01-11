package com.example.umlife;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.lights.Light;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.event.EventListFragment;
import com.example.model.UserInfo;
import com.example.posts.PostFragment;
import com.example.profile.ProfileFragment;
import com.example.rewards.RewardSystemFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myTheme";

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

    public void setWhichTheme(Integer whichTheme) {
        this.whichTheme = whichTheme;
    }

    Integer whichTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        whichTheme = sharedPreferences.getInt("theme", -1);

        setTheme(whichTheme == 1 ? R.style.Theme_UmLife_Purple : R.style.Theme_UmLifeOrange);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        //Toast.makeText(HomePageActivity.this, userInfo.toString(), Toast.LENGTH_LONG).show();
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
                        if(!documentSnapshot.contains("points")){
                            firestore.collection("users").document(userInfo.getUuid()).update("points","0");
                        }
                        //Toast.makeText((Context) HomePageActivity.this, temp, Toast.LENGTH_LONG).show();
                    }else{

                    }
                }else{
                    
                }
            }
        });

        // search relative layout
        relativeLayout = findViewById(R.id.Main);

        try {
            // bottom navigation
            bottomNavigationView = findViewById(R.id.bottomnavigation);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userInfo", userInfo);
            postFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, postFragment).commit();

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
                int count = getSupportFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < count; i++) {
                    getSupportFragmentManager().popBackStack();
                }
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}