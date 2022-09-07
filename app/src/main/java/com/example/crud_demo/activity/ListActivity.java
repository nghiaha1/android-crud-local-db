package com.example.crud_demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_demo.R;
import com.example.crud_demo.adapter.ListUserAdapter;
import com.example.crud_demo.database.AppDatabase;
import com.example.crud_demo.entity.UserEntity;
import com.example.crud_demo.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ListActivity extends AppCompatActivity {
     TextView tvId, tvUsername, tvGender;
     AppDatabase db;
     List<UserEntity> users;
     RecyclerView rvListUser;
     Button btnReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
        fetchData();
        setBtnReset();
    }

    private void initView() {
        tvId = findViewById(R.id.tvId);
        tvUsername = findViewById(R.id.tvUsername);
        tvGender = findViewById(R.id.tvSex);
        rvListUser = findViewById(R.id.rvListUser);
        btnReset = findViewById(R.id.btnReset);
    }

    private void fetchData() {
        db = AppDatabase.getAppDatabase(this);
        users = db.userDao().getAllUser();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        ListUserAdapter adapter = new ListUserAdapter(this, users);

        rvListUser.setLayoutManager(layoutManager);
        rvListUser.setAdapter(adapter);
    }

    private void setBtnReset() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.userDao().deleteAll();
                Toast.makeText(ListActivity.this, "Reset success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.Event event) {
        UserEntity user = event.getEntity();
        goToDetail(user);
    }

    private void goToDetail(UserEntity user) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }
}