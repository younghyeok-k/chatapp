package com.example.chatapp.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Chat;
import com.example.chatapp.MyAdapter;
import com.example.chatapp.R;
import com.example.chatapp.User;
import com.example.chatapp.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//import com.example.chatapp.databinding.FragmentDashboardBinding;

public class UsersFragment extends Fragment {
    private static final String TAG = "UsersFragment";
    private DashboardViewModel dashboardViewModel;
    private RecyclerView recyclerView;
    UserAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
FirebaseDatabase database;
    ArrayList<User> userArraylist;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        View root =inflater.inflate(R.layout.fragment_users,container,false);


 /*       final TextView textView =root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        SharedPreferences sharedPref = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
       String stEamil = sharedPref.getString("email", "");

        userArraylist=new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"test1", "test2", "test3", "test4"};
        madapter = new UserAdapter(userArraylist,stEamil);
        recyclerView.setAdapter(madapter);

        DatabaseReference databaseReference = database.getReference("message");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG,"onDataChange"+snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1 :  snapshot.getChildren()){
                    Log.d(TAG,"onDataChange1"+dataSnapshot1.getValue().toString());
                User user=dataSnapshot1.getValue(User.class);
                Log.d(TAG,"user:"+user.getEmail());
                userArraylist.add(user);

                }
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
//

}