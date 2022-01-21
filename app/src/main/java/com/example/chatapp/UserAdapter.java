package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> localDataSet;
    String stMyEmail="";

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
       public TextView tvUser;
public ImageView ivUser;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvUser = (TextView) view.findViewById(R.id.tvUser);
            ivUser=view.findViewById(R.id.ivUser);
        }

        public TextView getTextView() {
            return tvUser;
        }
    }

    @Override
    public int getItemViewType(int position) {
   return super.getItemViewType(position);

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param myDataset String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public UserAdapter(ArrayList<User> myDataset, String stEmail) {

        localDataSet = myDataset;
        this.stMyEmail=stEmail;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item_view, viewGroup, false);


        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvUser.setText(localDataSet.get(position).getEmail());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

