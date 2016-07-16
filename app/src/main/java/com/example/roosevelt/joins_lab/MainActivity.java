package com.example.roosevelt.joins_lab;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity {
    ListView listView ;

    Button btnAddData;
    Button btnSameCompany;
    Button btnBoston;
    Button btnHighestSalary;

    TextView txtResult;

    CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        btnAddData = (Button) findViewById(R.id.btnAdd);
        btnSameCompany = (Button) findViewById(R.id.btnSameCompany);
        btnBoston = (Button) findViewById(R.id.btnCompaniesInBoston);
        btnHighestSalary = (Button) findViewById(R.id.btnHighestSalary);

        txtResult = (TextView) findViewById(R.id.txtResult);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecords();
            }
        });

        btnSameCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = DatabaseHelper.getInstance(MainActivity.this).getEmployeesInSameCompany();
                displayEmployeeNamesInListView(cursor);
            }
        });

        btnBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = DatabaseHelper.getInstance(MainActivity.this).getCompaniesFromBoston();
                displayCompanyNamesInListView(cursor);
            }
        });

        btnHighestSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(String.format(Locale.ENGLISH, "Result: %s",
                        DatabaseHelper.getInstance(MainActivity.this).getCompanyWithHighestSalary()));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View v = getLayoutInflater().from(MainActivity.this)
                        .inflate(R.layout.dialog_employee_input, null);
                builder.setTitle("Add New Employee Record")
                        .setView(v)
                        .create();
                final AlertDialog ad = builder.show();

                final Button btnAdd = (Button) v.findViewById(R.id.btnAdd);
                final Button btnCancel = (Button) v.findViewById(R.id.btnCancel);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //check input, set errors and save to database
                        EditText txtSSN = (EditText) v.findViewById(R.id.txtSSN);
                        EditText txtFName = (EditText) v.findViewById(R.id.txtFName);
                        EditText txtLName = (EditText) v.findViewById(R.id.txtLName);
                        EditText txtYOB = (EditText) v.findViewById(R.id.txtYear);
                        EditText txtCity = (EditText) v.findViewById(R.id.txtCity);

                        List<EditText> txtInputs = new LinkedList<>();
                        txtInputs.add(txtSSN);
                        txtInputs.add(txtFName);
                        txtInputs.add(txtLName);
                        txtInputs.add(txtYOB);
                        txtInputs.add(txtCity);
                        List<String> inputs = new LinkedList<>();
                        for (EditText txtInput : txtInputs)
                            inputs.add(txtInput.getText().toString().trim());

                        if (inputs.get(0).equals("") || inputs.get(1).equals("")
                                || inputs.get(2).equals("") || inputs.get(3).equals("")
                                || inputs.get(4).equals("")) {
                            for (EditText txtInput : txtInputs)
                                if (txtInput.getText().toString().trim().equals(""))
                                    txtInput.setError("Required");
                        }
                        else {
                            //save object here
                            Employee newEmployee = new Employee(inputs.get(0),
                                    inputs.get(1), inputs.get(2),
                                    Integer.parseInt(inputs.get(3)),
                                    inputs.get(4));
                            DatabaseHelper.getInstance(MainActivity.this)
                                    .insertRowEmployee(newEmployee);

                            //make toast or snack bar
                            Toast.makeText(MainActivity.this, "You have added a new employee.",
                                    Toast.LENGTH_SHORT).show();

                            //display updated employee list
                            Cursor cursor = DatabaseHelper.getInstance(MainActivity.this)
                                    .getAllEmployees();
                            displayEmployeeNamesInListView(cursor);
                            ad.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();

                    }
                });


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

    private void displayEmployeeNamesInListView(Cursor cursor){
        cursorAdapter = new CursorAdapter(this, cursor, android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return getLayoutInflater().from(MainActivity.this)
                        .inflate(R.layout.employee_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txtName = (TextView) view.findViewById(R.id.txtName);

                String fName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME));
                String lName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME));

                Log.i("asdasdasd", fName + " " + lName);
                txtName.setText(fName + " " + lName);
            }
        };

        listView.setAdapter(cursorAdapter);

    }
    private void displayCompanyNamesInListView(Cursor cursor){
        cursorAdapter = new CursorAdapter(this, cursor, android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return getLayoutInflater().from(MainActivity.this)
                        .inflate(R.layout.employee_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txtName = (TextView) view.findViewById(R.id.txtName);

                String companyName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPANY));

                Log.i("asdasdasd", companyName);
                txtName.setText(companyName);
            }
        };

        listView.setAdapter(cursorAdapter);

    }

    private void displayCompanyNameAndSalary(Cursor cursor){

        cursorAdapter = new CursorAdapter(this, cursor, android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return getLayoutInflater().from(MainActivity.this)
                        .inflate(R.layout.job_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txtName = (TextView) view.findViewById(R.id.txtCompanyName);
                TextView txtSalary = (TextView) view.findViewById(R.id.txtSalary);

                String companyName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPANY));
                int salary = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_SALARY));

                Log.i("asdasdasd", companyName);
                txtName.setText(companyName);
                txtSalary.setText(String.valueOf(salary));
            }
        };

        listView.setAdapter(cursorAdapter);

    }



}
