package com.example.sai.girlstalk.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sai.girlstalk.R;

public class Register1 extends AppCompatActivity {

    EditText inputName;
    FloatingActionButton lgprogress1;
    Animation slideleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);

        inputName = findViewById(R.id.inputName);
        lgprogress1 = findViewById(R.id.lgprogress1);

        slideleft = AnimationUtils.loadAnimation(this,R.anim.slideup);
        inputName.setAnimation(slideleft);

        lgprogress1.setOnClickListener(v ->
        {
            String name = inputName.getText().toString();
            if(TextUtils.isEmpty(name))
                Toast.makeText(Register1.this,"Please Fill Up Your Name",Toast.LENGTH_LONG).show();
            else{
                Intent i = new Intent(Register1.this,Register2.class);
                i.putExtra("name",name);
                startActivity(i);
                finish();
            }
        });
    }
}
