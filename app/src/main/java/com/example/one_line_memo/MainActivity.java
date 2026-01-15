package com.example.one_line_memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton btnMenu, imgbtnclose;
    EditText edtMemo;
    Button btnSaveMemo;
    FrameLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.sidemenu);
        btnMenu = findViewById(R.id.btnMenu);
        imgbtnclose=findViewById(R.id.imgbtnclose);
        btnSaveMemo=findViewById(R.id.btnSaveMemo);
        edtMemo=findViewById(R.id.edtMemo);
        loadingLayout = findViewById(R.id.loadingLayout);

        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });

        imgbtnclose.setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        btnSaveMemo.setOnClickListener(v->{
            int memoLength=edtMemo.getText().toString().length();

            if (memoLength>40) {
                Toast.makeText(MainActivity.this,"40자 미만으로 작성해주세요",Toast.LENGTH_SHORT).show();
            }
            if (memoLength==0) {
                Toast.makeText(MainActivity.this,"일기를 작성해주세요",Toast.LENGTH_SHORT).show();
            }
            else {
                //Todo: 로딩화면으로 이동(돌아가는 C)
//                loadingLayout.setVisibility(View.VISIBLE);
                edtMemo.setText("");

                Intent intent = new Intent(MainActivity.this, DiaryList.class);
                startActivity(intent);
//                finish();


            }
        });
    }
}
