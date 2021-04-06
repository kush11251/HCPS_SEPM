package com.example.hcps_sepm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView mProfile,mSetting,mTimer,mMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfile            = findViewById(R.id.profileImageView);
        mSetting            = findViewById(R.id.settingImageView);
        mTimer              = findViewById(R.id.timerImageView);
        mMedicine           = findViewById(R.id.medicineImageView);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });

        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingActivity.class));
            }
        });

        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TimerActivity.class));
            }
        });

        mMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MedicineActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Not Allowed!!!!",Toast.LENGTH_SHORT).show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}