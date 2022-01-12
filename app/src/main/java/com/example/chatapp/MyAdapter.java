package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Chat> localDataSet;
    String stMyEmail="";

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.mytextView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public int getItemViewType(int position) {
   //     return super.getItemViewType(position);
        if(localDataSet.get(position).email.equals(stMyEmail)){
            return 1;
        }else{
            return  2;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param myDataset String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public MyAdapter(ArrayList<Chat> myDataset,String stEmail) {

        localDataSet = myDataset;
        this.stMyEmail=stEmail;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_text_view, viewGroup, false);

        if(viewType==1){
            view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.right_my_text_view, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

