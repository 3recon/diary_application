package com.example.one_line_memo;

import java.util.Random;

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
    TextView tvwise;
    //사이드바 부분
    TextView tvSetting;
    TextView tvDiaryList;

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
        tvwise=(TextView)findViewById(R.id.tvwise);

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
                return;
            }
            if (memoLength==0) {
                Toast.makeText(MainActivity.this,"일기를 작성해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                edtMemo.setText("");
                Intent intent = new Intent(MainActivity.this, DiaryList.class);
                startActivity(intent);
//                finish();
            }
        });

        //사이드바 부분
        tvSetting=(TextView)findViewById(R.id.tvSetting);
        tvDiaryList=(TextView)findViewById(R.id.tvDiaryList);

        tvDiaryList.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            startActivity(new Intent(MainActivity.this, DiaryList.class));
        });


    }
    //Todo
    //1.사이드바 메뉴 누르면 해당 화면으로 이동
    //2.일기 로컬에 저장 회원가입 없이

    @Override
    //메인화면이 출력될 때 마다 명언 랜덤 새로고침
    protected void onResume() {
        super.onResume();
        //랜덤 적용 부분
        String[] wiseArray = getResources().getStringArray(R.array.wise_array);
        Random random = new Random();
        int index = random.nextInt(wiseArray.length);
        tvwise.setText(wiseArray[index]);
    }
}
