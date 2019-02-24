package com.example.sai.girlstalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        RecyclerView storyRecycler = findViewById(R.id.storyRecycler);
        RecyclerView groupRecycler =findViewById(R.id.groupsRecycler);
        RecyclerView expertsRecycler = findViewById(R.id.expertsRecycler);

        ArrayList<StoryModel> storylist = new ArrayList<>();
        ArrayList<GroupModel> groupList = new ArrayList<>();
        ArrayList<StoryModel> expertList = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chatbot under constrution!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        storylist.add(new StoryModel("https://gobehere.com/wp-content/uploads/2017/10/Screen-Shot-2017-10-31-at-3.05.18-PM-min-2-e1510239997946.png",
                "Ami Lee’s story about taking a huge risk and falling in love with her life again"));
        storylist.add(new StoryModel("https://blogs.kent.ac.uk/kent-business-matters/files/2016/04/malala.jpg","Satuu drs hf drgd dtte hteyh"));
        storylist.add(new StoryModel("https://www.gcsp.ch/var/ezdemo_site/storage/images/courses/inspiring-women-leaders-2018/167943-1-eng-EU/Inspiring-Women-Leaders-2018_gallerylarge.jpg"
                ,"Inspiring story"));
        storylist.add(new StoryModel("https://alshindagah.com/images/article/d907c225-1f74-4c9b-9e6d-be556a96dae9.jpg","Uarr djytety jydyted  hdh"));

        storyRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        storyRecycler.setAdapter(new StoryRecyclerAdapter(storylist,this));

        groupList.add(new GroupModel("https://www.anikatherapeutics.com/assets/iStock_000020443536XLarge-e1411673189414.jpg","855",
                "Delhi","Women Rights"));

        groupList.add(new GroupModel("https://www.jetairways.com/Images/forms/group-booking.jpg","235",
                "Chennai","Online Job For Women"));

        groupList.add(new GroupModel("https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/06/09/11/group-photos-need-to-die.jpg?w968h681","235",
                "Kolkata","Local Friends"));

        //  groupList.add(new GroupModel("https://www.jetairways.com/Images/forms/group-booking.jpg","235",
        //"Chennai","Online Job For Women"));
        groupRecycler.setAdapter(new GroupRecyclerAdapter(groupList,this));
        groupRecycler.setLayoutManager(new LinearLayoutManager(this));

        expertList.add(new StoryModel("https://static.boredpanda.com/blog/wp-content/uploads/2017/08/Epic-Portraits-Shot-with-an-iPhone-and-a-Big-Mac-59a54f2641195__880.jpg","ABC Kumar"));
        expertList.add(new StoryModel("http://www.keatleyphoto.com/wp-content/uploads/2016/03/John_Keatley_iPhone_portrait_Andrew_5055.jpg","XYZ Kumar"));
        expertList.add(new StoryModel("http://farm3.static.flickr.com/2792/4285995840_72a6e4ff43.jpg","Taru Sart"));

        expertsRecycler.setAdapter(new ExpertRecyclerAdapter(expertList,this));
        expertsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_invites) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_create_group) {

        } else if (id == R.id.nav_vacancy) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_tollfree) {

        }else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Alert!")
                    .setMessage("Are you sure you want to sign out?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this,Login.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "You are signed out!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.alert)
                    .show();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
