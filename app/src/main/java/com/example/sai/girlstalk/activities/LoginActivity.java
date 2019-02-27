package com.example.sai.girlstalk.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.girlstalk.R;
import com.example.sai.girlstalk.viewModels.UserViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        TextView registerBTN = findViewById(R.id.btnToRegister);
        Button googleSignInBtn = findViewById(R.id.btnSignupGoogle);

        registerBTN.setOnClickListener(v -> startActivity(new Intent(this, Register1.class)));
        googleSignInBtn.setOnClickListener(v -> startActivityForResult(mGoogleSignInClient.getSignInIntent(), 101));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                userViewModel.googleLogin(account).observe(LoginActivity.this, isSuccessful -> {
                    if (isSuccessful != null) if (isSuccessful) {
                        Toast.makeText(LoginActivity.this, "Sign in succesful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else
                        Toast.makeText(LoginActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                });
            } catch (ApiException e) {
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
