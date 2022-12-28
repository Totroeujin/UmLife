package com.example.umlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyView> {
    private List<String> MyEventNameList;
    private List<Integer> MyEventImageList;


    public class MyView extends RecyclerView.ViewHolder{
        TextView TVMyEventName;
        ImageView IVMyeventImage;


        public MyView(View view){
            super(view);

            TVMyEventName = view.findViewById(R.id.TVMyEventName);
            IVMyeventImage = view.findViewById(R.id.IVMyEventImage);


        }
    }

    public MyEventAdapter(List<String> horizontalNameList, List<Integer> horizontalImageList){
        this.MyEventNameList = horizontalNameList;
        this.MyEventImageList = horizontalImageList;
    }


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_event_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.TVMyEventName.setText(MyEventNameList.get(position));
        holder.IVMyeventImage.setImageResource(MyEventImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return MyEventNameList.size();
    }
}
