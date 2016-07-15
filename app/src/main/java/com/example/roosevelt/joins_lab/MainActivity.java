package com.example.roosevelt.joins_lab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addRecords(){
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        //Create Employee objects
        Employee emp1 = new Employee("123-04-5678", "John", "Smith", 1973, "NY");
        Employee emp2 = new Employee("123-04-5679", "David", "McWill", 1982, "Seattle");
        Employee emp3 = new Employee("123-04-5680", "Katerina", "Wise", 1973, "Boston");
        Employee emp4 = new Employee("123-04-5681", "Donald", "Lee", 1992, "London");
        Employee emp5 = new Employee("123-04-5682", "Gary", "Henwood", 1987, "Las Vegas");
        Employee emp6 = new Employee("123-04-5683", "Anthony", "Bright", 1963, "Seattle");
        Employee emp7 = new Employee("123-04-5684", "William", "Newey", 1995, "Boston");
        Employee emp8 = new Employee("123-04-5685", "Melony", "Smith", 1970, "Chicago");

        //Create Job objects
        Job job1 = new Job("123-04-5678", "Fuzz", 60, 1);
        Job job2 = new Job("123-04-5679", "GA", 70, 2);
        Job job3 = new Job("123-04-5680", "Little Place", 120, 5);
        Job job4 = new Job("123-04-5681", "Macy's", 78, 3);
        Job job5 = new Job("123-04-5682", "New Life", 65, 1);
        Job job6 = new Job("123-04-5683", "Believe", 158, 6);
        Job job7 = new Job("123-04-5684", "Macy's", 200, 8);
        Job job8 = new Job("123-04-5685", "Stop", 299, 12);

        //insert Employee objects to db
        db.insertRowEmployee(emp1);
        db.insertRowEmployee(emp2);
        db.insertRowEmployee(emp3);
        db.insertRowEmployee(emp4);
        db.insertRowEmployee(emp5);
        db.insertRowEmployee(emp6);
        db.insertRowEmployee(emp7);
        db.insertRowEmployee(emp8);

        //insert Job objects to db
        db.insertRowJob(job1);
        db.insertRowJob(job2);
        db.insertRowJob(job3);
        db.insertRowJob(job4);
        db.insertRowJob(job5);
        db.insertRowJob(job6);
        db.insertRowJob(job7);
        db.insertRowJob(job8);

        //close db
        db.close();
    }
}
