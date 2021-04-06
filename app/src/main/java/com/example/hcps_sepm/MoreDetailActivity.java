package com.example.hcps_sepm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MoreDetailActivity extends AppCompatActivity {

    EditText mBloodGroup, mDOB, mPhone;
    Button mSubmit;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);

        mBloodGroup             = findViewById(R.id.bloodGrpMoreDetsEditText);
        mDOB                    = findViewById(R.id.dobMoreDetsEditText);
        mPhone                  = findViewById(R.id.phoneMoreDetsEditText);

        mSubmit                 = findViewById(R.id.submitMoreDetsButton);

        fAuth                   = FirebaseAuth.getInstance();
        fStore                  = FirebaseFirestore.getInstance();

        userID                  = fAuth.getCurrentUser().getUid();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String blood = mBloodGroup.getText().toString();
                String dob   = mDOB.getText().toString();
                String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(blood)) {
                    mBloodGroup.setError("Blood Group is Required.");
                    return;
                }

                if (TextUtils.isEmpty(dob)) {
                    mDOB.setError("DOB is Required.");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone Number is Required");
                    return;
                }

                DocumentReference documentReference = fStore.collection("Users Details").document(userID);
                Map<String, Object> user = new HashMap<>();

                user.put("Blood Group",blood);
                user.put("DOB",dob);
                user.put("Phone",phone);

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Info","onSuccess : user details is added for " + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Info","onFailure : " + e.toString());
                    }
                });

                /*documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Info","onFailure : " + e.toString());
                    }
                });*/

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Not Allowed!!!!",Toast.LENGTH_SHORT).show();
    }
}