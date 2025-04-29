package com.example.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitle, editTextDescription;
    Button buttonSave;
    EditText editTextUpdateId;
    Button buttonUpdate;




    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextUpdateId = findViewById(R.id.editTextUpdateId);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSave = findViewById(R.id.buttonSave);


        dbHelper = new DatabaseHelper(this);
        Button buttonShow;
        buttonShow = findViewById(R.id.buttonShow);
        TextView textViewResult;
        textViewResult = findViewById(R.id.textViewResult);


        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getAllData();
                if (cursor.getCount() == 0) {
                    textViewResult.setText("داده پیدا نشد");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (cursor.moveToNext()) {

                    buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
                    buffer.append("Title: ").append(cursor.getString(1)).append("\n");
                    buffer.append("Description: ").append(cursor.getString(2)).append("\n\n");
                }

                textViewResult.setText(buffer.toString());
            }
        });
        EditText editTextDeleteId = findViewById(R.id.editTextDeleteId);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = editTextDeleteId.getText().toString().trim();
                if (!idText.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    int deletedRows = dbHelper.deleteData(id);
                    if (deletedRows > 0) {
                        Toast.makeText(MainActivity.this, "رکورد حذف شد", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "رکوردی با این ایدی پیدا نشد", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "لطفا ایدی رکورد مورد نطر را وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();


                if (!title.isEmpty() && !description.isEmpty()) {
                    long result = dbHelper.insertData(title, description);
                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "داده با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "خطا در ذخیره داده", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "لطفا هر دو بخش عنوان و توضیحات را وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonUpdate.setOnClickListener(v -> {
            String idText = editTextUpdateId.getText().toString().trim();
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (idText.isEmpty() || title.isEmpty() || description.isEmpty()) {
                Toast.makeText(MainActivity.this, "لطفا تمامی رکورد هارا پر کن", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idText);
            int updatedRows = dbHelper.updateData(id, title, description);

            if (updatedRows > 0) {
                Toast.makeText(MainActivity.this, "رکورد آپدیت شد", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "رکورد آپدیت نشد", Toast.LENGTH_SHORT).show();
            }
        });
        buttonUpdate.setOnClickListener(v -> {
            String idText = editTextUpdateId.getText().toString().trim();
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (idText.isEmpty() || title.isEmpty() || description.isEmpty()) {
                Toast.makeText(MainActivity.this, "لطفا تمامی رکورد هارا پر کن", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idText);
            int rowsAffected = dbHelper.updateData(id, title, description);

            if (rowsAffected > 0) {
                Toast.makeText(MainActivity.this, "رکورد مورد نظر با موفقیت اپدیت شد", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "خطا!رکوردی با این ایدی وجود ندارد", Toast.LENGTH_SHORT).show();
            }
        });




    }
}