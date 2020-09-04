package com.example.contenprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    static final String uri = "content://com.example.contenprovider";
    EditText edtID,edtName,edtUnit,edtMadeIn;
    Button btnSelect,btnSave,btnDelete,btnUpdate;
    GridView gv_display;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtUnit = findViewById(R.id.edtUnit);
        edtMadeIn = findViewById(R.id.edtMadeIn);

        btnSelect = findViewById(R.id.btnSelect);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        gv_display = findViewById(R.id.gv_display);

        databaseHelper = new DatabaseHelper(ProductActivity.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();
                values.put("id",edtID.getText().toString());
                values.put("name",edtName.getText().toString());
                values.put("unit",edtUnit.getText().toString());
                values.put("madein",edtMadeIn.getText().toString());

                Uri product = Uri.parse(uri);

                Uri insert_uri = getContentResolver().insert(product,values);

                Toast.makeText(ProductActivity.this, "Luu Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list_string = new ArrayList<>();
                Uri product = Uri.parse(uri);

                Cursor cursor = getContentResolver().query(product,null,null,null,"name");
                if(cursor != null){
                    cursor.moveToFirst();
                    do{
                        list_string.add(cursor.getInt(0) + cursor.getString(1) + cursor.getString(2) + cursor.getString(3));
                    }while (cursor.moveToNext());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductActivity.this,android.R.layout.simple_list_item_1,list_string);
                    gv_display.setAdapter(adapter);
                }else {
                    Toast.makeText(ProductActivity.this, "Khong co ket qua", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}