package com.example.sai.girlstalk.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sai.girlstalk.R;
import com.example.sai.girlstalk.viewModels.UserViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class CustomLoginActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editTextEmail, editTextPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.mail);
        editTextPassword = findViewById(R.id.pass);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        findViewById(R.id.btnLogin).setOnClickListener(v -> userLogin());
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        userViewModel.signIn(email, password).observe(this, result ->
        {
            progressBar.setVisibility(View.GONE);
            if (result != null) if (result.equalsIgnoreCase(" ")) {
                Intent intent = new Intent(CustomLoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

}
