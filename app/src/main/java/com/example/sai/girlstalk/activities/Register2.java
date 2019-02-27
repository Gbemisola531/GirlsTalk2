package com.example.sai.girlstalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sai.girlstalk.R;

public class Register2 extends AppCompatActivity {

    TextView name;
    LinearLayout input1, input2, input3;
    Animation slideleft, slideright;
    private EditText pass, confirmpass;
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        name = findViewById(R.id.name);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        pass = findViewById(R.id.pass1);
        confirmpass = findViewById(R.id.pass2);

        mFloatingActionButton = findViewById(R.id.faBtnlg2);

        String nameTxt = getIntent().getStringExtra("name");

        if (nameTxt.length() > 6) nameTxt = nameTxt.substring(0, 6) + "...";

        slideleft = AnimationUtils.loadAnimation(this, R.anim.slideleft);
        slideright = AnimationUtils.loadAnimation(this, R.anim.slideright);

        input1.setAnimation(slideleft);
        input2.setAnimation(slideleft);
        input3.setAnimation(slideleft);

        name.setText(String.format("Hi, %s !", nameTxt));
        name.setAnimation(slideright);

        mFloatingActionButton.setOnClickListener(v ->
        {
            if (!pass.getText().toString().equals(confirmpass.getText().toString()))
                Snackbar.make(v, "Passwords do not match!", Snackbar.LENGTH_SHORT).show();
            else if (pass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty())
                Snackbar.make(v, "Empty fields!", Snackbar.LENGTH_SHORT).show();
            else startActivity(new Intent(Register2.this, MainActivity.class));
            finish();
        });
    }
}
