package com.example.sai.girlstalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.girlstalk.utils.BounceInterpolar;
import com.example.sai.girlstalk.R;

import com.example.sai.girlstalk.utils.FirebaseUtils;
import com.github.loadingview.LoadingDialog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;

import java.util.concurrent.TimeUnit;


public class OTPActivity extends AppCompatActivity {

    private TextView waitTxt;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private TextView t1;
    private ImageView i1, i2;
    private LoadingDialog dialog;
    private Animation bounceAnimation, moveAnimation;
    private EditText e1;
    private Button b1;
    private TextView exampleTxt;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // getting first run boolean value(false) after 1st run
        preferences = getSharedPreferences("FIRST_RUN", MODE_PRIVATE);
        if (preferences.contains("checkrunstatus"))
            // if its not the 1st run
            startActivity(new Intent(this, LoginActivity.class));
        finish();

        setUpViews();

        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        moveAnimation = AnimationUtils.loadAnimation(this, R.anim.down);

        bounceAnimation.setInterpolator(new BounceInterpolar(0.2, 20));
        i2.startAnimation(bounceAnimation);

        i2.setOnClickListener(v -> i2.startAnimation(bounceAnimation));

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseUtils.getInstance().getAuthInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(OTPActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
                mAuth.signInWithCredential(credential).addOnCompleteListener(OTPActivity.this, task -> {
                    if (task.isSuccessful()) {
                        dialog.hide();
                        // saving status of first run as false if the task is successful.
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("checkrunstatus", false);
                        editor.apply();
                        startActivity(new Intent(OTPActivity.this, LoginActivity.class));
                        finish();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(OTPActivity.this, "Invalid Verification", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                        e1.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.VISIBLE);
                        t1.setVisibility(View.VISIBLE);
                        i1.setVisibility(View.VISIBLE);
                        i2.setVisibility(View.VISIBLE);
                        waitTxt.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTPActivity.this, "Verification Failed! Try again..", Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException)
                    Toast.makeText(OTPActivity.this, "InValid Phone Number", Toast.LENGTH_SHORT).show();
                else if (e instanceof FirebaseTooManyRequestsException)
                    Toast.makeText(OTPActivity.this, "Something went wong...please try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId, ForceResendingToken token) {
                Toast.makeText(OTPActivity.this, "Verification code has been send on your number", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                e1.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);  // making some elements as invisible and some visible
                i1.setVisibility(View.GONE);
                i2.setVisibility(View.GONE);
                exampleTxt.setVisibility(View.GONE);
                waitTxt.setVisibility(View.VISIBLE);
                waitTxt.startAnimation(moveAnimation);

                dialog = LoadingDialog.Companion.get(OTPActivity.this).show();
            }
        };

        b1.setOnClickListener(v ->
        {
            if (e1.getText().toString().isEmpty())
                Toast.makeText(OTPActivity.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(e1.getText().toString(), 60,
                    TimeUnit.SECONDS, OTPActivity.this, mCallbacks);   // this wil verify phone number
        });
    }

    private void setUpViews() {
        exampleTxt = findViewById(R.id.exampletxt);
        e1 = findViewById(R.id.Phonenoedittext);
        b1 = findViewById(R.id.PhoneVerify);
        t1 = findViewById(R.id.textView2Phone);
        i1 = findViewById(R.id.imageView2Phone);
        i2 = findViewById(R.id.logo);
        waitTxt = findViewById(R.id.waittxt);
    }

}
