package com.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper;
    Button btnInsert, btnView, btnUpdate, btnDelete;
    EditText editID,editName, editSurname, editMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);
        helper.getWritableDatabase();

        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        editMarks = findViewById(R.id.editMarks);
        editName = findViewById(R.id.editName);
        editID = findViewById(R.id.editID);
        editSurname = findViewById(R.id.editSurname);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = helper.insertData(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = helper.getAllData();
                if (cursor.getCount() == 0) {

                    showMessage("Error","No Data Found");
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID : " + cursor.getString(0) + "\n");
                    buffer.append("NAME : " + cursor.getString(1) + "\n");
                    buffer.append("SURNAME : " + cursor.getString(2) + "\n");
                    buffer.append("MARKS : " + cursor.getString(3) + "\n\n\n");

                }
                showMessage("Data",buffer.toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdate = helper.updateData(editID.getText().toString(),editName.getText().toString(),
                        editSurname.getText().toString(),editMarks.getText().toString());

                if (isUpdate == true)
                {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deleteRow = helper.deleteData(editID.getText().toString());

                if (deleteRow > 0)
                {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}
