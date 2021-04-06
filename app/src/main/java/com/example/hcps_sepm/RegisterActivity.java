package com.example.hcps_sepm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword;
    Button mRegister;
    TextView mLogin;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName           = findViewById(R.id.fullNameRegisterEditText);
        mEmail              = findViewById(R.id.userIDRegisterEditText);
        mPassword           = findViewById(R.id.passwordRegisterEditText);

        mRegister           = findViewById(R.id.registerRegisterbutton);
        mLogin              = findViewById(R.id.loginRegisterTextView);

        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String pword = mPassword.getText().toString().trim();
                String fname = mFullName.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pword)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (pword.length() < 6) {
                    mPassword.setError("Password must be more than 6 Character!!!!");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"User Created!!!",Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users Details").document(userID);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Full Name",fname);
                            user.put("Email",email);
                            user.put("Blood Group",null);
                            user.put("DOB",null);
                            user.put("Phone",null);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("Info","OnSuccess : User Profile is created for " + userID);
                                }
                            });

                            documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Info","onFailure : "+ e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),MoreDetailActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),"Error!!! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Not Allowed!!!!",Toast.LENGTH_SHORT).show();
    }
}