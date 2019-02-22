package com.example.sai.girlstalk;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.github.loadingview.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class OTP extends AppCompatActivity {

    private static final String TAG = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private TextView waitTxt;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    TextView t1;
    ImageView i1,i2;
    LoadingDialog dialog;
    private Animation bounceanime;
    EditText e1;
    Button b1;
    private TextView exampleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        exampleTxt = findViewById(R.id.exampletxt);
        e1 =  findViewById(R.id.Phonenoedittext);
        b1 = findViewById(R.id.PhoneVerify);
        t1 = findViewById(R.id.textView2Phone);
        i1 = findViewById(R.id.imageView2Phone);
        i2 = findViewById(R.id.logo);
        waitTxt = findViewById(R.id.waittxt);
        bounceanime = AnimationUtils.loadAnimation(this,R.anim.bounce);
        BounceInterpolar interpolator = new BounceInterpolar(0.2, 20);
        bounceanime.setInterpolator(interpolator);
        i2.startAnimation(bounceanime);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.startAnimation(bounceanime);
            }
        });
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                Toast.makeText(OTP.this,"Verification Complete",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTP.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTP.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(OTP.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                e1.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                i1.setVisibility(View.GONE);
                i2.setVisibility(View.GONE);
                exampleTxt.setVisibility(View.GONE);
                waitTxt.setVisibility(View.VISIBLE);
                dialog= LoadingDialog.Companion.get(OTP.this).show();

            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.getText().toString().isEmpty()){
                    Toast.makeText(OTP.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
                }

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            e1.getText().toString(),
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            OTP.this,
                            mCallbacks);


            }
        });

//
//                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, e2.getText().toString());
//                // [END verify_with_code]
//                signInWithPhoneAuthCredential(credential);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(OTP.this,login1.class));
                            Toast.makeText(OTP.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            dialog.hide();
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTP.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                                dialog.hide();
                                e1.setVisibility(View.VISIBLE);
                                b1.setVisibility(View.VISIBLE);
                                t1.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.VISIBLE);
                                i2.setVisibility(View.VISIBLE);
                                waitTxt.setVisibility(View.GONE);

                            }
                        }
                    }
                });
    }
}
