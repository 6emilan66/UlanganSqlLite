package com.example.ulangansqllite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class EditActivity extends AppCompatActivity {
    Button btnUpdate;
    Content content;
    Bundle intentData;
    TextInputLayout edtJudul, edtDesk;
    private String judul;
    private String desk;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtJudul = findViewById(R.id.edtInput);
        edtDesk = findViewById(R.id.edtInput2);

        intentData = getIntent().getExtras();
        edtJudul.getEditText().setText(intentData.getString("judul"));
        edtDesk.getEditText().setText(intentData.getString("deskripsi"));

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            content = new Content();
            judul = edtJudul.getEditText().getText().toString();
            desk = edtDesk.getEditText().getText().toString();

            if (judul.isEmpty() && desk.isEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                content.setId(intentData.getInt("id"));
                content.setJudul(judul);
                content.setDesk(desk);

                dbHelper.btnUpdate(content);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                HomeActivity home = new HomeActivity();
                home.setupRecyclerView();
                finish();
            }
        });
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
        }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        int ID = item.getItemId();

        if(ID == R.id.delete_action){
            dbHelper.delete(intentData.getInt("id"));

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_LONG).show();

            return true;
            }
            return super.onOptionsItemSelected(item);
        }
}
