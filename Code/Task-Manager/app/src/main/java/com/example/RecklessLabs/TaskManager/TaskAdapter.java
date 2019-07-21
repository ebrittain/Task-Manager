package com.example.RecklessLabs.TaskManager;

import android.annotation.SuppressLint;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.CustomViewHolder> {

    MyAdapterListener onClickListener;
    ArrayList<Task> tasks;

    TaskAdapter(ArrayList<Task> tasks, MyAdapterListener listener){
        onClickListener = listener;
        this.tasks = tasks;
    }

    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view,parent,false);
        return new CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.taskname.setText(tasks.get(position).getName());
        if(tasks.get(position).getTotalTime()==0)
            holder.tasktime.setBase(SystemClock.elapsedRealtime());
        else
            holder.tasktime.setBase(SystemClock.elapsedRealtime()-tasks.get(position).getTotalTime());

    }

    public void deleteItem(int position){
        tasks.remove(position);
        notifyItemRemoved(position);
    }



    public interface MyAdapterListener{

        void startOnClick(View v, int position, Chronometer timer);
        void stopOnClick(View v, int position, Chronometer timer);
        void descOnClick(View v, int position);
        void statsOnClick(View v, int position);
        void refreshOnClick(View v, int position);
        void editOnClick(View v, int position);

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView taskname;
        Chronometer tasktime;

        ImageButton start;
        ImageButton stop;
        ImageButton desc;
        ImageButton stats;
        ImageButton refresh;
        ImageButton edit;

        CustomViewHolder(View view){
            super(view);
            taskname = view.findViewById(R.id.taskName_rv);
            tasktime = view.findViewById(R.id.taskTime_rv);
            start = view.findViewById(R.id.start_button_rv);
            stop = view.findViewById(R.id.stop_button_rv);
            desc = view.findViewById(R.id.info_button_rv);
            stats = view.findViewById(R.id.stats_button_rv);
            refresh = view.findViewById(R.id.refresh_button_rv);
            edit = view.findViewById(R.id.edit_button_rv);


            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.startOnClick(v,getAdapterPosition(), tasktime);
                    start.setClickable(false);
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.stopOnClick(v,getAdapterPosition(), tasktime);
                    start.setClickable(true);
                }
            });

            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.descOnClick(v,getAdapterPosition());
                }
            });

            stats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.statsOnClick(v,getAdapterPosition());
                }
            });

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.refreshOnClick(v, getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.editOnClick(v, getAdapterPosition());
                }
            });

        }

    }
}
