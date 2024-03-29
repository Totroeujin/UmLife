package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.UserInfo;
import com.example.umlife.HomePageActivity;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //Register Acc initialisation
    EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    //Firebase auth module implementation
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //Initialise firestore as connection to firebase
    FirebaseFirestore firestore;

    //Initialised an "user" object, and collection
    Map<String,Object> user = new HashMap<>();
    CollectionReference users;

    //UserInfo package
    UserInfo userInfo = new UserInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fragment);

        //Navigate to Login
        TextView haveAcc = findViewById(R.id.goLogin);
        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //let firestore get instance from firebase
        firestore = FirebaseFirestore.getInstance();


        //Register Acc
        inputUsername = findViewById(R.id.username);
        inputEmail    = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputConfirmPassword = findViewById(R.id.confirmPassword);
        register   = findViewById(R.id.registerBtn);

        //Progress box
        progressDialog = new ProgressDialog(this);

        //Auth mode
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterAuth();
            }
        });

    }

        private void RegisterAuth() {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            String username = inputUsername.getText().toString();
            String confirmPassword = inputConfirmPassword.getText().toString();



            if(!email.matches(emailPattern)){
                inputEmail.setError("Please enter a valid email!");
            }else if(password.isEmpty() || password.length()<6){
                inputPassword.setError("Password must be longer than 6 characters!");
            }else if(!password.equals(confirmPassword)){
                inputConfirmPassword.setError("Both password field not match!");
            }else if(username.equals("")){
                inputUsername.setError("Username cannot be empty!");
            }else{
                progressDialog.setMessage("Registration on going....");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                users = firestore.collection("users");
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//Acc being inside Auth module
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String TAG = "Debug";
                        if(task.isSuccessful()){
                            //Testing on upload username to firebase
                            /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            mUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        System.out.println("Username updated!");
                                    }
                                }
                            });*/
                            user.put("email",email);
                            user.put("password",password);
                            user.put("username", username);
                            //users.document(mUser.getUid()).set(user);
                            users.document(mUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this,"Register successful", Toast.LENGTH_LONG).show();
                                }
                            });
                            //Try to path to correct db

                            //Above is testing area
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();

                            //upload to database


                            //push the object into the firestore database
                            firestore.collection("users").document(mUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DocumentSnapshot document = task.getResult();
                                        userInfo.setEmail(document.getString("email"));
                                        userInfo.setUsername(document.getString("username"));
                                        userInfo.setUuid(mUser.getUid());
                                        DirectUser(RegisterActivity.this, HomePageActivity.class);
                                    }
                                }
                            });
                        }else{
                            progressDialog.dismiss();
                            //Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }

        private void DirectUser(android.content.Context currentPage, Class<?> nextPage) {
            Intent intent = new Intent(currentPage, nextPage);
            intent.putExtra("userInfo", userInfo);

            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }