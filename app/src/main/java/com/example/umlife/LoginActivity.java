package com.example.umlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    //Storing preferences
    private static final String FILE_NAME = "myFile";

    //Register Acc initialisation
    EditText inputEmail, inputPassword;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    //Firebase auth module implementation
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //UserInfo
    UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*//Use for empty content login
        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
            }
        });*/

        //Login Acc
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);


        //Progress box
        progressDialog = new ProgressDialog(this);

        //Auth mode
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        inputEmail.setText(email);
        inputPassword.setText(password);

        //Auto login
        AutoLogin(sharedPreferences);

        //Login button
        /*MaterialButton login = (MaterialButton) findViewById(R.id.loginBtn);*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAuth();
            }
        });

        //Go Register Page
        TextView register = findViewById(R.id.goToRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void AutoLogin(SharedPreferences information) {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(!email.equals("")&&!password.equals("")){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        //Notification on success

                        //Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                        String uuid = mAuth.getCurrentUser().getUid();

                        //UserInfo being completed
                        userInfo.setEmail(email);
                        userInfo.setUuid(uuid);

                        //Retrieve the info of user by uuid from firebase
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        DocumentReference documentReference = firestore.collection("users").document(uuid);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String Tag = "DocSnippets";
                                if (task.isSuccessful()){
                                    //Copy down the information from firebase
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()){
                                        //Do casting
                                        String name = (String) documentSnapshot.getData().get("username");
                                        Toast.makeText((Context) LoginActivity.this, name, Toast.LENGTH_LONG).show();
                                        userInfo.setUsername(name);
                                    }else{
                                        Log.d(Tag, "No such document");
                                    }
                                }else{
                                    Log.d(Tag, "get failed with ", task.getException());
                                }
                            }
                        });



                        //Store preferences
                        StoreDataWithSharedPreferences(email, password);



                        //Direct to Home page
                        DirectUser(LoginActivity.this, HomePageActivity.class);

                    }else{
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_LONG).show();
                        information.edit().clear();
                    }
                }
            });
        }
    }

    private void StoreDataWithSharedPreferences(String email, String password) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void LoginAuth() {//Perform login algo
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Please enter a valid email!");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Password must be longer than 6 characters!");
        } else {
            progressDialog.setMessage("Login on going....");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        //Notification on success

                        String uuid =  mAuth.getCurrentUser().getUid();
                        //UserInfo being completed
                        userInfo.setEmail(email);
                        userInfo.setUuid(uuid);

                        //Retrieve the info of user by uuid from firebase
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        DocumentReference documentReference = firestore.collection("users").document(uuid);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String Tag = "DocSnippets";
                                if (task.isSuccessful()){
                                    //Copy down the information from firebase
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()){
                                        //Do casting
                                        String name = (String) documentSnapshot.getData().get("username");
                                        Toast.makeText((Context) LoginActivity.this, name, Toast.LENGTH_LONG).show();
                                        userInfo.setUsername(name);
                                    }else{
                                        Log.d(Tag, "No such document");
                                    }
                                }else{
                                    Log.d(Tag, "get failed with ", task.getException());
                                }
                            }
                        });

                        //Store preferences
                        StoreDataWithSharedPreferences(email, password);

                        //Direct to Home page
                        DirectUser(LoginActivity.this, HomePageActivity.class);

                    } else {
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_LONG).show();
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