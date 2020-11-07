package com.plamy.taskforcer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDatabaseCallback, AutoPermissionsListener {
    private static final String TAG = "MainActivity";

    Button addButton;
    Button searchButton;
    Button cancelButton;
    LinearLayout searchBar;
    Animation slide_down;
    Animation slide_up;
    EditText searchText;

    TaskDatabase database;
    TaskListFragment taskListFragment;
    TaskListFragment taskAddFragment;

    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Fragment Implementation
        taskListFragment = new TaskListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.task_list_container, taskListFragment).commit();
        //endregion

        //region Initialization
        inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        addButton = findViewById(R.id.addButton);
        searchButton = findViewById(R.id.searchButton);
        cancelButton = findViewById(R.id.cancelButton);

        searchText = findViewById(R.id.searchText);
        searchBar = findViewById(R.id.SearchBar);
        searchBar.setVisibility(View.GONE);

        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        //endregion

        //region OnClickListener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Search Bar
                Log.d(TAG, " Show Search Bar");
                searchBar.setVisibility(View.VISIBLE);
                searchBar.startAnimation(slide_down);
                searchText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide Search Bar
                Log.d(TAG, " Hide Search Bar");
                InputMethodManager immhide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                searchBar.startAnimation(slide_up);
                searchBar.setVisibility(View.GONE);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskAddFragment e = TaskAddFragment.getInstance();
                e.setTargetFragment(taskListFragment, 1337);
                e.show(getSupportFragmentManager(), TaskAddFragment.TAG_EVENT_DIALOG);
            }
        });
        //endregion OnClickListener

        //region Database Configurations
        if (database != null) {
            database.close();
            database = null;
        }

        database = TaskDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "Book database is open.");
        } else {
            Log.d(TAG, "Book database is not open.");
        }

        AutoPermissions.Companion.loadAllPermissions(this, 101);
        //endregion
    }

    //region AutoPermission Methods
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }
    @Override
    public void onDenied(int i, String[] strings) { }
    @Override
    public void onGranted(int i, String[] strings) { }
    //endregion

    //region OnDatabaseCallback implementations
    @Override
    public void insert(String title, String startDate, String endDate, String content, int completedFlag) {
        database.insertRecord(title, startDate, endDate, content, completedFlag);
        Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<TaskInfo> selectAll() {
        ArrayList<TaskInfo> result = database.selectAll();
        Toast.makeText(getApplicationContext(), "Tasks Loaded.", Toast.LENGTH_SHORT).show();
        return result;
    }
    //endregion

}