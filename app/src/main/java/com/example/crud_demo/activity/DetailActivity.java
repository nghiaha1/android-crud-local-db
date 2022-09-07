package com.example.crud_demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud_demo.R;
import com.example.crud_demo.database.AppDatabase;
import com.example.crud_demo.entity.UserEntity;
import com.example.crud_demo.util.EditTextValidation;

public class DetailActivity extends AppCompatActivity {
    EditText edUsername, edDescription;
    Spinner spSex;
    String gender;
    String[] genderList = {"Male", "Female", "Other"};
    Button btnUpdate, btnDelete;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        setBtnUpdate();
        setBtnDelete();
    }

    private void initView() {
        edUsername = findViewById(R.id.edUsername);
        edDescription = findViewById(R.id.edDescription);
        spSex = findViewById(R.id.spSex);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = AppDatabase.getAppDatabase(this);
    }

    private void setBtnUpdate() {
        UserEntity user = (UserEntity) getIntent().getSerializableExtra("USER");
        edUsername.setText(user.getUsername());
        edDescription.setText(user.getDescription());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genderList);
        spSex.setAdapter(adapter);
        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String description = edDescription.getText().toString().trim();
                String sex = gender;

                if (username.isEmpty() || description.isEmpty()) {
                    Toast.makeText(DetailActivity.this,
                            "Username and description is required", Toast.LENGTH_SHORT).show();
                }else {
                    if (EditTextValidation.isValidUsername(username)) {
                            user.setUsername(username);
                            user.setDescription(description);
                            user.setSex(gender);
                            db.userDao().updateUser(user);
                            Toast.makeText(DetailActivity.this,
                                    "Register success", Toast.LENGTH_SHORT).show();
                            toListUser();
                    }else
                        Toast.makeText(DetailActivity.this,
                                "Username's characters > 4 and < 20", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toListUser() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void setBtnDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity user = (UserEntity) getIntent().getSerializableExtra("USER");
                db.userDao().deleteUser(user);
                Toast.makeText(DetailActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                toListUser();
            }
        });
    }
}