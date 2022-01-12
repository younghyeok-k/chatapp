package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MyAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;
    String stEamil;
    FirebaseDatabase database;
    private static final String TAG = "ChatActivity";
    ArrayList<Chat> chatArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        chatArraylist=new ArrayList<>();
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
        madapter = new MyAdapter(chatArraylist,stEamil);
        recyclerView.setAdapter(madapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                String commentKey = dataSnapshot.getKey();
                Chat chat = dataSnapshot.getValue(Chat.class);
                String stEmail=chat.getEmail();
                String stText=chat.getText();
                Log.d(TAG,"stEamil:"+stEmail+"stText"+stText);
               chatArraylist.add(chat);
               madapter.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.


                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.


                // ...
            }

            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference databaseReference = database.getReference("message");
        databaseReference.addChildEventListener(childEventListener);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText = etText.getText().toString();
                Toast.makeText(ChatActivity.this, "MSG:" + stText, Toast.LENGTH_LONG).show();


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