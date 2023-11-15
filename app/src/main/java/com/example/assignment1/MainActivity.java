package com.example.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list_view;
    static ArrayList<String> listTask;
    EditText editText;
    EditText editDescription;
    Button btnAdd;
    ArrayAdapter<String> arrayAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myToolbar);//define toolbar

        setSupportActionBar(toolbar);

        setupSharedPrefs();

        loadData();//Calling method load to load the saved data on shared pref
        list_view = findViewById(R.id.list_view);//define listview
        btnAdd = findViewById(R.id.AddBtn);//define button add
        editText = findViewById(R.id.TaskEdit);//define plaintext for task
        editDescription = findViewById(R.id.DescraptionTXT);//define plaintext for description
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listTask);//define array adapter for listview
        list_view.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {//click method
            @Override
            public void onClick(View v) {

                String tasks = editText.getText().toString();//to get text from edit text

                String taskDesc = editDescription.getText().toString();//to get text from edit description
                listTask.add(tasks);//add task to listview
                saveTaskDescription(tasks, taskDesc);//this calling method to save description
                arrayAdapter.notifyDataSetChanged();//This android function notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself
                saveData();
            }
        });
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {//and this to approve the user to click on list view to go to another page and show the task decription
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTask = listTask.get(position);
                String selectedTaskDesc = getTaskDescription(selectedTask);
                Intent intent = new Intent(MainActivity.this, DescraptionView.class);
                intent.putExtra("task_description", selectedTaskDesc);
                startActivity(intent);
            }
        });
    }

    private void setupSharedPrefs() {//setup sharedpref to save and load data
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
    }

    private void saveData() {//save data method using gson
        Gson gson = new Gson();
        String json = gson.toJson(listTask);
        editor.putString("list_task", json);
        editor.apply();
    }

    private void loadData() {//load data method
        Gson gson = new Gson();
        String json = sp.getString("list_task", null);
        if (json != null) {
            listTask = gson.fromJson(json, ArrayList.class);
        } else {
            listTask = new ArrayList<>();
        }
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listTask);
        if (list_view != null) {
            list_view.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void saveTaskDescription(String task, String description) {
        editor.putString("task_description_" + task, description);
        editor.apply();
    }

    private String getTaskDescription(String task) {
        return sp.getString("task_description_" + task, "");
    }

    public static void toggleTaskStatus(String taskDescription, String updStatus) {
        for (int i = 0; i < listTask.size(); i++) {
            String currTask = listTask.get(i);
            if (currTask.equals(taskDescription)) {
                listTask.set(i, taskDescription + " - " + updStatus);
                break;
            }
        }
    }
}
