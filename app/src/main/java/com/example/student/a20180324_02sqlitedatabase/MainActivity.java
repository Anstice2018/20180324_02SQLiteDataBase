package com.example.student.a20180324_02sqlitedatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.student.a20180324_02sqlitedatabase.R.*;

public class MainActivity extends AppCompatActivity {
    File fout;
    ArrayList<String> mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fout = new File(getFilesDir(), "student.sqlite");
        mylist = new ArrayList<>();
        if (fout.exists()) {
            Toast.makeText(MainActivity.this, "檔案存在", Toast.LENGTH_SHORT).show();
        }
        else
        {
            copyDb();
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(fout, null);
        Cursor c = db.rawQuery("Select * from stu", null);
        c.moveToFirst();
//        Toast.makeText(MainActivity.this, c.getString(1), Toast.LENGTH_SHORT).show();
        do
        {
            mylist.add(c.getString(1));
            Log.d("DB", c.getString(1));
        }while (c.moveToNext());
        db.close();
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                mylist);
        lv.setAdapter(adapter);
    }


    public void copyDb()
    {
        InputStream is = getResources().openRawResource(R.raw.student);
        byte b[] = new byte[1024];
        int len;
        try {
            FileOutputStream fos = new FileOutputStream(fout);
            while((len = is.read(b)) != -1)
            {
                fos.write(b, 0, len);
            }
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add)
        {
            Intent it = new Intent(MainActivity.this, AddActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }



}

