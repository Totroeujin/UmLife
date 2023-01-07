package com.example.umlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPassActivity extends AppCompatActivity {

    //EditText
    EditText forgotEmail;

    //Button
    Button sendEmailBtn;

    //Firebase
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        forgotEmail = findViewById(R.id.forgotEmail);
        sendEmailBtn = findViewById(R.id.sendEmailBtn);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotEmail.getText().toString().trim();
                if(email != ""){
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPassActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(),"Enter your email",Toast.LENGTH_LONG).show();
                    forgotEmail.setError("Please fill in this field!");
                }
            }
        });
    }
}