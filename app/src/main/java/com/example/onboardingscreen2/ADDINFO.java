package com.example.onboardingscreen2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ADDINFO extends AppCompatActivity {
    Button add,view,delete;
    EditText number;
    ListView listView;
    SQLiteOpenHelper s1;
    SQLiteDatabase sqlitedb;
    DatabaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d_d_i_n_f_o);
        add=findViewById(R.id.add);
        view= findViewById(R.id.view);
        delete=findViewById(R.id.delete);
        number =findViewById(R.id.phone);
        myDB = new DatabaseHandler(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sr=number.getText().toString();
                addData(sr);
                Toast.makeText(ADDINFO.this, "Data Added", Toast.LENGTH_SHORT).show();
                number.setText("");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlitedb = myDB.getWritableDatabase();
                String x= number.getText().toString();
                deleteData(x);
                Toast.makeText(ADDINFO.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

    }

    private void loadData() {
        ArrayList<String> theList = new ArrayList<>();
        Cursor data=myDB.getListContents();
        if(data.getCount()==0){
            Toast.makeText(this, "There is no content", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }

    }

    private boolean deleteData(String x) {
        return sqlitedb.delete(DatabaseHandler.TABLE_NAME,DatabaseHandler.COL2 + "=?" ,new String[]{x})>0;
    }

    private void addData(String newEntry) {
        boolean insertData= myDB.addData(newEntry);

        if(insertData == true){
            Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Unsuccessfull", Toast.LENGTH_SHORT).show();
        }


    }
}