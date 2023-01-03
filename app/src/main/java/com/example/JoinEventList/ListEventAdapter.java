package com.example.JoinEventList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umlife.R;

import java.util.ArrayList;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.MyViewHolder> {
    private ArrayList<listEventTest> eventList;

    public ListEventAdapter(ArrayList<listEventTest> eventList){
        this.eventList = eventList;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView nameText;

        public MyViewHolder(final View view){
            super(view);
            nameText = view.findViewById(R.id.num);
        }
    }

    @NonNull
    @Override
    public ListEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listevent_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = eventList.get(position).getEventName();
        holder.nameText.setText(name);
    }

    //Limit how many for List
    @Override
    public int getItemCount() {
        return 10;
    }
}
