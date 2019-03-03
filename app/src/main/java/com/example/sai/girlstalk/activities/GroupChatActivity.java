package com.example.sai.girlstalk.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.sai.girlstalk.R;
import com.example.sai.girlstalk.adapters.GroupMessagesAdapter;
import com.example.sai.girlstalk.viewModels.GroupViewModel;

public class GroupChatActivity extends AppCompatActivity
{
    private EditText message;
    private RecyclerView groupMessagesList;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        message = findViewById(R.id.message);
        groupMessagesList = findViewById(R.id.groupMessages);
        sendBtn = findViewById(R.id.sendMessageBtn);

        String groupTitle = getIntent().getStringExtra("GROUP TITLE");
        GroupViewModel groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        groupViewModel.getAllMessages(groupTitle).observe(this,groupMessages ->
        {
            if (groupMessages != null)
            {
                groupMessagesList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                groupMessagesList.setHasFixedSize(true);
                groupMessagesList.setAdapter(new GroupMessagesAdapter(groupMessages,getApplicationContext()));
            }
        });

    }
}
