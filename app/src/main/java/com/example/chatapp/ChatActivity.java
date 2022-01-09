package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MyAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;
    String stEamil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        stEamil = getIntent().getStringExtra("email");
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
                String stText = etText.getText().toString();
                Toast.makeText(ChatActivity.this, "MSG:" + stText, Toast.LENGTH_LONG).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String datetime = dateFormat.format(c.getTime());
                System.out.println(datetime);

                DatabaseReference myRef = database.getReference("message").child(datetime);


                Hashtable<String, String> numbers
                        = new Hashtable<String, String>();
                numbers.put("email", stEamil);
                numbers.put("text", stText);


                myRef.setValue(numbers);
            }
        });

    }


}