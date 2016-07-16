package com.example.roosevelt.joins_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.Locale;

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
            COL_CITY + " TEXT" + ")";

    private static final String SQL_CREATE_ENTRIES_JOB = "CREATE TABLE " +
            JOB_TABLE_NAME + " (" +
            COL_ID_JOB + " INTEGER PRIMARY KEY," +
            COL_COMPANY + " TEXT," +
            COL_SALARY + " INT," +
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

    public long insertRowJob(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY, job.getCompany());
        values.put(COL_SALARY, job.getSalary());
        values.put(COL_EXPERIENCE, job.getExperience());
        values.put(COL_EMP_ID, job.getEmpSSN());
        return db.insertOrThrow(JOB_TABLE_NAME, null, values);
    }
    /**
     builder.setTables(String.format(Locale.ENGLISH, "%s JOIN %s ON %s.%s=%s.%s",
     EMPLOYEE_TABLE_NAME, DEPARTMENT_TABLE_NAME,
     EMPLOYEE_TABLE_NAME,COL_ID,
     DEPARTMENT_TABLE_NAME, COL_EMP_ID));
     Cursor cursor = builder.query(getReadableDatabase(), null, null, null, null, null, null);


     if (cursor.moveToFirst()){
     while(!cursor.isAfterLast()){

     cursor.moveToNext();
     }

     */

    public Cursor getAllEmployees(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(EMPLOYEE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllJobs(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(JOB_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

    }
//
//    public Cursor getEmployeesInSameCompany(){
//        SQLiteDatabase db = getReadableDatabase();
//        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
//        sqLiteQueryBuilder.setTables(String.format(Locale.ENGLISH, "%s JOIN %s ON %s.%s=%s.%s",
//                EMPLOYEE_TABLE_NAME, JOB_TABLE_NAME,
//                EMPLOYEE_TABLE_NAME,COL_ID_EMP,
//                JOB_TABLE_NAME,COL_EMP_ID));
//        String[] projection = {EMPLOYEE_TABLE_NAME + "." + COL_ID_EMP,
//                COL_FIRST_NAME, COL_LAST_NAME, COL_COMPANY, COL_SALARY,
//                "COUNT(1) AS COMPCOUNT"};
//
//        Cursor cursor = sqLiteQueryBuilder.query(db,
//                projection,
//                null,
//                null,
//                COL_COMPANY,
//                "COMPCOUNT > 1",
//                null);
//
//        Log.i("asdasdasdasdasd", String.valueOf(cursor.getCount()));
//
//        return cursor;
//    }

    public Cursor getEmployeesInSameCompany(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(String.format(Locale.ENGLISH, "%s JOIN %s ON %s.%s=%s.%s",
                EMPLOYEE_TABLE_NAME, JOB_TABLE_NAME,
                EMPLOYEE_TABLE_NAME,COL_ID_EMP,
                JOB_TABLE_NAME,COL_EMP_ID));
        String where = COL_COMPANY + "=? ";
        String[] whereArgs = {"Macy's"};

        return sqLiteQueryBuilder.query(db,
                null,
                where,
                whereArgs,
                null,
                null,
                null,
                null
                );
    }

    public Cursor getCompaniesFromBoston(){
        SQLiteDatabase db = getReadableDatabase();
        String where = COL_CITY + " = ?";
        String[] whereArgs = {"Boston"};

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(String.format(Locale.ENGLISH, "%s JOIN %s ON %s.%s=%s.%s",
                EMPLOYEE_TABLE_NAME, JOB_TABLE_NAME,
                EMPLOYEE_TABLE_NAME,COL_ID_EMP,
                JOB_TABLE_NAME,COL_EMP_ID));

        Cursor cursor = sqLiteQueryBuilder.query(db,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
        return cursor;
    }

    public String getCompanyWithHighestSalary(){

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COL_COMPANY, "MAX("+COL_SALARY+")"};

//        Cursor cursor = db.query(JOB_TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        Log.i("asdasdasd", String.valueOf(cursor.getCount()));
//        String company = "";
        Cursor cursor = db.rawQuery(String.format(Locale.ENGLISH,
                "SELECT * FROM %s WHERE %s=(SELECT MAX(%s) AS highestSalary FROM %s)",
                JOB_TABLE_NAME,
                COL_SALARY,
                COL_SALARY,
                JOB_TABLE_NAME), null);

        String company = "No value";


        if(cursor.moveToFirst()){
            Log.i("asdasasd", String.valueOf(cursor.getCount()));
            company = cursor.getString(cursor.getColumnIndex(COL_COMPANY)) + " (Salary:" +
                        cursor.getInt(cursor.getColumnIndex(COL_SALARY)) + ")";
        }

        cursor.close();
        return company;
    }

}
