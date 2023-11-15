package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DescraptionView extends AppCompatActivity {
    private String taskDescription;
    private RadioButton radioButtonDue;
    private RadioButton radioButtonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descraption_view);

        taskDescription = getIntent().getStringExtra("task_description");//to show the decription of task that selected
        TextView desctxt = findViewById(R.id.desctxt);//define textview that will be print the description task
        desctxt.setText(taskDescription);

        radioButtonDue = findViewById(R.id.radioButtonDue);//define
        radioButtonDone = findViewById(R.id.radioButtonDone);//define

        Button togglebtn = findViewById(R.id.btnToggleStatus);//define
        togglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updStatus = radioButtonDone.isChecked() ? "Done" : "Due";
                MainActivity.toggleTaskStatus(taskDescription, updStatus);
                String msg = "Task status updated to " + updStatus;//this message will be shown whe user click on status button
                showToast(msg);
            }


        });
        Button backbtn=findViewById(R.id.backbtn);//this button to let user back to previous page
        backbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void showToast(String message) {//this toast its notify message to show what the status of task while user choose between due or done
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
