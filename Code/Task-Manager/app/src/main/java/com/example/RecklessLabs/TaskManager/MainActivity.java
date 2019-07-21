package com.example.RecklessLabs.TaskManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private boolean running = false;
    DatabaseHelper myDB;
    ArrayList<Task> tasks;

    private View popUpView;
    private Button submitButton;
    private Button cancelButton;
    private EditText taskEdit;
    private EditText descEdit;

    private PopupWindow popupWindow;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        popUpView = getLayoutInflater().inflate(R.layout.activity_add_task, null);


        popupWindow = new PopupWindow(popUpView, Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(this.getDrawable(R.drawable.main_bg));
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);


        submitButton = popUpView.findViewById(R.id.submit_button_pu);
        cancelButton = popUpView.findViewById(R.id.cancel_button_pu);
        taskEdit = popUpView.findViewById(R.id.taskName_pu);
        descEdit = popUpView.findViewById(R.id.description_pu);


        addButton = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myDB = new DatabaseHelper(this);
        tasks = myDB.getAllData();
        taskAdapter = new TaskAdapter(tasks, new TaskAdapter.MyAdapterListener() {
            @Override
            public void startOnClick(View v, int position, Chronometer timer) {
                if(myDB.getTime(tasks.get(position).getName())==0){
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    running = true;
                }
                else{
                    timer.setBase(SystemClock.elapsedRealtime() - myDB.getTime(tasks.get(position).getName()));
                    timer.start();
                    running = true;
                }
            }

            @Override
            public void stopOnClick(View v, int position, Chronometer timer) {
                if(running){
                    timer.stop();
                    long offset = SystemClock.elapsedRealtime() - timer.getBase();
                    myDB.stop(tasks.get(position).getName(), offset);
                    running = false;
                }
            }

            @Override
            public void descOnClick(View v, int position) {
                showMessage(tasks.get(position).getName(), tasks.get(position).getDesc());
            }

            @Override
            public void statsOnClick(View v, int position) {
                startActivity(new Intent(MainActivity.this,RequestHelp.class));
            }

            @Override
            public void refreshOnClick(View v, int position) {
                myDB.resetTime(tasks.get(position).getName());

                tasks = myDB.getAllData();
                taskAdapter.setTasks(tasks);
            }

            @Override
            public void editOnClick(View v, int position) {
                popUpEdit(tasks.get(position).getName(), tasks.get(position).getDesc());
            }

        });
        recyclerView.setAdapter(taskAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                myDB.deleteData(tasks.get(pos).getName());
                taskAdapter.deleteItem(pos);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp();
            }
        });

    }

    public void showMessage(String title, String message) {
        android.support.v7.app.AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void popUp(){
        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

        taskEdit.setText(null);
        descEdit.setText(null);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDB.insertData(taskEdit.getText().toString(), descEdit.getText().toString())) {
                    tasks = myDB.getAllData();
                    taskAdapter.setTasks(tasks);
                    popupWindow.dismiss();
                }
                else
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void popUpEdit(final String name, String desc){
        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

        taskEdit.setText(name);
        descEdit.setText(desc);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDB.updateData(name,taskEdit.getText().toString(), descEdit.getText().toString())) {
                    tasks = myDB.getAllData();
                    taskAdapter.setTasks(tasks);
                    popupWindow.dismiss();

                }
                else
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }



}