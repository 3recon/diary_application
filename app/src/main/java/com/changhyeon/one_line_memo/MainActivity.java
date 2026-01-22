package com.changhyeon.one_line_memo;

import java.time.LocalDate;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton btnMenu, imgbtnclose;
    EditText edtMemo;
//    Button btnSaveMemo;
    ImageButton btnSaveMemo;
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
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
        );



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

        btnSaveMemo.setOnClickListener(v->{
            int memoLength=edtMemo.getText().toString().length();
            String memo=edtMemo.getText().toString().trim();


            if (memoLength>40) {
                AlertDialog dialog= new AlertDialog.Builder(this,R.style.RoundedDialog)
                    .setMessage("40자 이하로 일기를 작성해주세요.")
                    .setPositiveButton("확인", null)
                    .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(Color.BLACK); // 혹은 원하는 색
                return;
            }
            if (memoLength==0) {
                AlertDialog dialog= new AlertDialog.Builder(this,R.style.RoundedDialog)
                    .setMessage("일기를 작성하지 않았습니다.")
                    .setPositiveButton("확인", null)
                    .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(Color.BLACK);
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


    @Override
    //메인 화면이 출력될 때 마다 문구 새로고침, 저장/작성 완료 버튼 로직 재검사
    protected void onResume() {
        super.onResume();
        //랜덤 적용 부분
        String[] wiseArray = getResources().getStringArray(R.array.wise_array);
        Random random = new Random();
        int index = random.nextInt(wiseArray.length);
        tvwise.setText(wiseArray[index]);

        // 🔥 오늘 일기 존재 여부 다시 체크
        String today = LocalDate.now().toString();
        SharedPreferences sp = getSharedPreferences("diary", MODE_PRIVATE);

        if (sp.contains(today)) {
            btnSaveMemo.setImageResource(R.drawable.edit_off);
            btnSaveMemo.setEnabled(false);
//            btnSaveMemo.setText("기록 완료 🙂");
        } else {
            btnSaveMemo.setEnabled(true);
            btnSaveMemo.setImageResource(R.drawable.edit_note);
        }

    }
}
