package com.example.roosevelt.joins_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roosevelt on 7/15/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db.db";
    public static final int DATABASE_VERSION = 1;

    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COL_ID_EMP = "_id";
    public static final String COL_FIRST_NAME = "fname";
    public static final String COL_LAST_NAME = "lname";
    public static final String COL_CITY = "city";
    public static final String COL_YEAR_OF_BIRTH = "yob";

    public static final String JOB_TABLE_NAME = "job";
    public static final String COL_ID_JOB = "_id";
    public static final String COL_COMPANY = "company";
    public static final String COL_SALARY = "salary";
    public static final String COL_EXPERIENCE = "experience";
    public static final String COL_EMP_ID = "emp_ssn";

    private static DatabaseHelper ourInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new DatabaseHelper(context);
        return ourInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    private static final String SQL_CREATE_ENTRIES_EMPLOYEE = "CREATE TABLE " +
            EMPLOYEE_TABLE_NAME + " (" +
            COL_ID_EMP + " TEXT PRIMARY KEY," +
            COL_FIRST_NAME + " TEXT," +
            COL_LAST_NAME + " TEXT," +
            COL_YEAR_OF_BIRTH + " INTEGER," +
            COL_CITY + " TEXT," + ")";

    private static final String SQL_CREATE_ENTRIES_JOB = "CREATE TABLE " +
            JOB_TABLE_NAME + " (" +
            COL_ID_JOB + " INTEGER PRIMARY KEY," +
            COL_COMPANY + " TEXT," +
            COL_SALARY + " TEXT," +
            COL_EXPERIENCE + " TEXT," +
            COL_EMP_ID + " TEXT" + "," +
            "FOREIGN KEY("+ COL_EMP_ID +") REFERENCES "+ EMPLOYEE_TABLE_NAME+"("+ COL_ID_EMP +") )";

    private static final String SQL_DELETE_ENTRIES_EMPLOYEE = "DROP TABLE IF EXISTS " +
            EMPLOYEE_TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_JOB = "DROP TABLE IF EXISTS " +
            JOB_TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_EMPLOYEE);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_JOB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_EMPLOYEE);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_JOB);
        onCreate(sqLiteDatabase);
    }


    public void insertRowEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID_EMP, employee.getEmpSSN());
        values.put(COL_FIRST_NAME, employee.getFirstName());
        values.put(COL_LAST_NAME, employee.getLastName());
        values.put(COL_YEAR_OF_BIRTH, employee.getYearOfBirth());
        values.put(COL_CITY, employee.getCity());
        db.insertOrThrow(EMPLOYEE_TABLE_NAME, null, values);
    }

    public void insertRowJob(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY, job.getCompany());
        values.put(COL_SALARY, job.getSalary());
        values.put(COL_EXPERIENCE, job.getExperience());
        values.put(COL_EMP_ID, job.getEmpSSN());
        db.insertOrThrow(JOB_TABLE_NAME, null, values);
    }


}
