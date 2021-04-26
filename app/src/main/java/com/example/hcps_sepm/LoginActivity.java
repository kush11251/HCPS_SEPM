package com.example.hcps_sepm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    ImageView mLogin;
    TextView mRegister;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail              = findViewById(R.id.userIDLoginEditText);
        mPassword           = findViewById(R.id.passwordLoginEditText);
        mLogin              = findViewById(R.id.loginLoginbutton);
        mRegister           = findViewById(R.id.registerLoginTextView);

        fAuth               = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pword = mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email) ) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pword)) {
                    mEmail.setError("Password is Required.");
                    return;
                }

                if (pword.length() < 6) {
                    mPassword.setError("Password must be more than 6 Character!!!!");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error!!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(),"Not Allowed!!!!",Toast.LENGTH_SHORT).show();
    }
}