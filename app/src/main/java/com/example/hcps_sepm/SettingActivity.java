package com.example.hcps_sepm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SettingActivity extends AppCompatActivity {

    EditText fullName, userID, bloodGrp, dob, phone;
    String mFullName, mUserID, mBloodGrp, mDOB, mPhone;
    String userIDRef;

    Button mSubmitBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        fullName    = findViewById(R.id.fullNameSettingEditText);
        userID      = findViewById(R.id.userIDSettingEditText);
        bloodGrp    = findViewById(R.id.bloodGrpSettingEditText);
        dob         = findViewById(R.id.dobSettingEditText);
        phone       = findViewById(R.id.phoneSettingEditText);

        mSubmitBtn  = findViewById(R.id.submitSettingButton);

        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();
        userIDRef   = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users Details").document(userIDRef);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fullName.setText(value.getString("Full Name"));
                userID.setText(value.getString("Email"));
                bloodGrp.setText(value.getString("Blood Group"));
                dob.setText(value.getString("DOB"));
                phone.setText(value.getString("Phone"));
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFullName       = fullName.getText().toString().trim();
                mBloodGrp       = bloodGrp.getText().toString().trim();
                mDOB            = dob.getText().toString().trim();
                mPhone          = phone.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                user.put("Full Name",mFullName);
                user.put("Blood Group",mBloodGrp);
                user.put("DOB",mDOB);
                user.put("Phone",mPhone);

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Info","onSuccess : user details is changed for " + userID);
                        Toast.makeText(getApplicationContext(),"User Details Updated, Cannot Change User Id",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Info","onFailure : " + e.toString());
                    }
                });
            }
        });
    }
}