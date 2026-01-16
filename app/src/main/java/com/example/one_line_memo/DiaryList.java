package com.example.one_line_memo;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class DiaryList extends AppCompatActivity {
    ImageButton btnMenu2;
    DrawerLayout drawerLayout2;
    ImageButton imgbtnclose;
    DrawerLayout drawerLayout;

    //사이드바 부분
    TextView tvSetting;
    TextView tvDiaryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        btnMenu2=(ImageButton) findViewById(R.id.btnMenu2);
        drawerLayout=(DrawerLayout)findViewById(R.id.sidemenu);
        imgbtnclose=(ImageButton) findViewById(R.id.imgbtnclose);


        btnMenu2.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });
        imgbtnclose.setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        tvDiaryList.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            // 이미 일기 목록이므로 이동 없음
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();   // 기본 동작: 이전 Activity로 돌아감
    }

}
