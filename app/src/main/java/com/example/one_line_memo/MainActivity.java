package com.example.one_line_memo;

import java.time.LocalDate;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.content.SharedPreferences;
import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton btnMenu, imgbtnclose;
    EditText edtMemo;
    Button btnSaveMemo;
    TextView tvwise;
    //사이드바 부분
    TextView tvDiaryList;
    TextView tvCharCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.sidemenu);
        btnMenu = findViewById(R.id.btnMenu);
        imgbtnclose=findViewById(R.id.imgbtnclose);
        btnSaveMemo=findViewById(R.id.btnSaveMemo);
        edtMemo=findViewById(R.id.edtMemo);
        tvwise=(TextView)findViewById(R.id.tvwise);
        TextView tvCharCount = findViewById(R.id.tvCharCount);


        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });

        imgbtnclose.setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.END);
        });
        // 오늘 날짜 key
        String today = LocalDate.now().toString();

        // SharedPreferences 가져오기
        SharedPreferences sp = getSharedPreferences("diary", MODE_PRIVATE);

        // 오늘 일기 이미 있으면 버튼 비활성화
        //-----------테스트용으로 임시 비활성화--------------------------------------------
//        if (sp.contains(today)) {
//            btnSaveMemo.setEnabled(false);
//            btnSaveMemo.setText("일기 작성 완료");
//        }

        btnSaveMemo.setOnClickListener(v->{
            int memoLength=edtMemo.getText().toString().length();
            String memo=edtMemo.getText().toString().trim();

            // ✅ 오늘 일기가 이미 있으면 저장 금지
            //-----------테스트용으로 임시 비활성화--------------------------------------------
//            if (sp.contains(today)) {
//                Toast.makeText(MainActivity.this, "오늘 일기는 이미 작성되었습니다.", Toast.LENGTH_SHORT).show();
//                return;
//            }

            if (memoLength>40) {
                new AlertDialog.Builder(this)
                    .setMessage("40자 이하로 일기를 작성해주세요.")
                    .setPositiveButton("확인", null)
                    .show();
                return;
            }
            if (memoLength==0) {
                new AlertDialog.Builder(this)
                    .setMessage("일기를 작성하지 않았습니다.")
                    .setPositiveButton("확인", null)
                    .show();
                return;
            }
            // 저장
            sp.edit().putString(today,memo).apply();
            edtMemo.setText("");
            startActivity(new Intent(this, DiaryList.class));
        });
        //글자 수 세기
        edtMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                tvCharCount.setText(length + " / 40");

                // 40자 이상이거나 일기를 적지 않았을ㅁㄴ 때 글자색 빨강으로
                if (length > 40 || length==0) {
                    tvCharCount.setTextColor(Color.RED);
                } else {
                    tvCharCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //사이드바 부분
        tvDiaryList=(TextView)findViewById(R.id.tvDiaryList);

        tvDiaryList.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            startActivity(new Intent(MainActivity.this, DiaryList.class));
        });

    }
    //Todo
    //1.사이드바 메뉴 누르면 해당 화면으로 이동 ok
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
