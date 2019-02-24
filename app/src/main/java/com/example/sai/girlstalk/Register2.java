package com.example.sai.girlstalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Register2 extends AppCompatActivity {

    TextView name;
    LinearLayout input1,input2,input3;
    Animation slideleft,slideright;
    private EditText mailid,pass,confirmpass;
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        name= findViewById(R.id.name);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        mailid = findViewById(R.id.mail);
        pass = findViewById(R.id.pass1);
        confirmpass = findViewById(R.id.pass2);

        mFloatingActionButton = findViewById(R.id.faBtnlg2);

        Intent i = getIntent();

        String nametxt= i.getStringExtra("name");

        if (nametxt.length()>6) {
            nametxt = nametxt.substring(0,6)+"...";
         /*   for (int j= name.length()-1;j>6;j--){
                nametxt.charAt(j) =
            }*/
        }

        slideleft = AnimationUtils.loadAnimation(this,R.anim.slideleft);
        slideright = AnimationUtils.loadAnimation(this,R.anim.slideright);

        input1.setAnimation(slideleft);
        input2.setAnimation(slideleft);
        input3.setAnimation(slideleft);

        name.setText("Hi, "+ nametxt+" !");
        name.setAnimation(slideright);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass.getText().toString().equals(confirmpass.getText().toString())){
                    Snackbar snackbar = Snackbar.make(v,"Passwords do not match!",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else if(pass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty()
                       ||  pass.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,"Empty fields!",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    startActivity(new Intent(Register2.this,MainActivity.class));
                    finish();
                }

            }
        });
    }
}
