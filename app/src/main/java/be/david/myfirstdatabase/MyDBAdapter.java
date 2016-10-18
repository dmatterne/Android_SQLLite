package be.david.myfirstdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.*;

/**
 * Created by David on 18/10/2016.
 */

public class MyDBAdapter {

    private Context context;

    private MyDBHelper myDBHelper;

    private SQLiteDatabase db;

    private String DATABASE_NAME = "data";

    private int DATABSE_VERSION = 1;

    public MyDBAdapter(Context context) {
        this.context = context;

        myDBHelper = new MyDBHelper(context, DATABASE_NAME, null, DATABSE_VERSION);

    }

    public void open () {
        db = myDBHelper.getWritableDatabase();
    }


    public class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE students (ID integer primary key autoincrement, name text, faculty integer);";
            db.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String query = "DROP TABLE IF EXISTS students;";

            db.execSQL(query);

            onCreate(db);

        }


    }


    public void insertStudent(String name, int faculty) {

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("faculty", faculty);
        db.insert("students",null,cv);


    }

    public ArrayList<String> selectAllStudents() {

        ArrayList<String> studentList = new ArrayList<>();

        Cursor cursor = db.query("students",null, null, null, null, null,null, null);

        if (cursor != null && cursor.moveToFirst()) {

            do {

                studentList.add(cursor.getString(1));

            } while (cursor.moveToNext());

        }

        return studentList;

    }

    public void deleteAllEngineers() {

        db.delete("students", "faculty = 1", null);

    }

    public void deleteAllStudents() {

        db.delete("students", "", null);

    }
}
