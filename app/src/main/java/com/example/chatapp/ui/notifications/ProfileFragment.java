package com.example.chatapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.R;
//import com.example.chatapp.databinding.FragmentNotificationsBinding;

public class ProfileFragment extends Fragment {

    int REQUEST_IMAGE_CODE=1001;
    private NotificationsViewModel notificationsViewModel;
    // private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                new ViewModelProvider(this).get(NotificationsViewModel.class);

//        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ImageView ivUser=root.findViewById(R.id.ivUser);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(in,1001);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}  //asdasdasd
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}