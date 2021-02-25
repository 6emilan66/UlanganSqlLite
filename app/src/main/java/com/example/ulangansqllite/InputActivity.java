package com.example.ulangansqllite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.widget.EditText;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.snackbar.Snackbar;

public class InputActivity extends AppCompatActivity {
    TextInputLayout inputJudul, inputDesk;
    Button fab;
    Content content;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputJudul = findViewById(R.id.input);
        inputDesk = findViewById(R.id.input2);

        fab = findViewById(R.id.btnSubmit);
        fab.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(this);
            content = new Content();
            String judul = inputJudul.getEditText().getText().toString();
            String desk = inputDesk.getEditText().getText().toString();
            content.setJudul(judul);
            content.setDesk(desk);

            db.insert(content);

            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }
}
