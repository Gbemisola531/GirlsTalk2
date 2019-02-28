package com.example.sai.girlstalk.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.girlstalk.R;
import com.example.sai.girlstalk.models.User;
import com.example.sai.girlstalk.viewModels.UserViewModel;

public class Register2 extends AppCompatActivity {

    private EditText password, confirmPassword, email;

    private UserViewModel userViewModel;
    private String usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        TextView name = findViewById(R.id.name);
        password = findViewById(R.id.signUpPassword);
        confirmPassword = findViewById(R.id.signUpConfirmPassword);
        email = findViewById(R.id.signUpEmail);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        String nameTxt = getIntent().getStringExtra("name");
        usernameText = getIntent().getStringExtra("name");

        if (nameTxt.length() > 6) nameTxt = nameTxt.substring(0, 6) + "...";

        findViewById(R.id.signUpPasswordParent).setAnimation(AnimationUtils.loadAnimation(this, R.anim.slideleft));
        findViewById(R.id.signUpEmailParent).setAnimation(AnimationUtils.loadAnimation(this, R.anim.slideleft));
        findViewById(R.id.signUpConfirmPasswordParent).setAnimation(AnimationUtils.loadAnimation(this, R.anim.slideleft));

        name.setText(String.format("Hi, %s !", nameTxt));
        name.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slideright));

        findViewById(R.id.faBtnlg2).setOnClickListener(v ->
        {
            String passwordText = password.getText().toString();
            String emailText = email.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            if (passwordText.isEmpty() || confirmPasswordText.isEmpty() || usernameText.isEmpty() || emailText.isEmpty())
                Snackbar.make(v, "Empty fields!", Snackbar.LENGTH_SHORT).show();
            else if (!passwordText.equals(confirmPasswordText))
                Snackbar.make(v, "Passwords do not match!", Snackbar.LENGTH_SHORT).show();
            else
                userViewModel.signUp(new User(usernameText, emailText, passwordText)).observe(this, isSuccessful ->
                {
                    if (isSuccessful != null) if (isSuccessful) {
                        Toast.makeText(this, "A Confirmation Email Has Been Sent To You", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(this, "An Error Occurred Please Try Again", Toast.LENGTH_SHORT).show();
                });
        });
    }
}
