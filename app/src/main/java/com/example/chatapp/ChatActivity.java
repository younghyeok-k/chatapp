package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MyAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        btnSend = (Button) findViewById(R.id.btnSend);
        etText = (EditText) findViewById(R.id.etText);
        Button btnFinish = (Button) findViewById(R.id.btnfinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] myDataset = {"test1", "test2", "test3", "test4"};
        madapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(madapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText=etText.getText().toString();
                Toast.makeText(ChatActivity.this,"MSG:"+stText,Toast.LENGTH_LONG).show();
            }
        });

    }


}