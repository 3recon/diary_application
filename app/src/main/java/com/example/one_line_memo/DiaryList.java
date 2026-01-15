package com.example.one_line_memo;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class DiaryList extends AppCompatActivity {
    ImageButton btnMenu2;
    DrawerLayout drawerLayout2;
    ImageButton imgbtnclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        btnMenu2=(ImageButton) findViewById(R.id.btnMenu2);
        drawerLayout2=(DrawerLayout)findViewById(R.id.sidemenu2);
        imgbtnclose=(ImageButton) findViewById(R.id.imgbtnclose);

        btnMenu2.setOnClickListener(v -> {
            drawerLayout2.openDrawer(GravityCompat.END);
        });
        imgbtnclose.setOnClickListener(view -> {
            drawerLayout2.closeDrawer(GravityCompat.END);
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();   // 기본 동작: 이전 Activity로 돌아감
    }

}
