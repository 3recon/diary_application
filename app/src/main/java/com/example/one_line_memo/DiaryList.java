package com.example.one_line_memo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DiaryList extends AppCompatActivity {
    ImageButton btnMenu;
    DrawerLayout drawerLayout2;
    ImageButton imgbtnclose;
    DrawerLayout drawerLayout;
    //달력부분
    RecyclerView rvCalendar;
    CalendarAdapter calendarAdapter;
    TextView tvYearMonth;
    int currentYear;
    int currentMonth;
    TextView tvSelectedDate;
    TextView tvDiaryContent;
    Set<String> diaryDates = new HashSet<>();



    //사이드바 부분
    TextView tvDiaryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        btnMenu=(ImageButton) findViewById(R.id.btnMenu2);
        drawerLayout=(DrawerLayout)findViewById(R.id.sidemenu2);
        imgbtnclose=(ImageButton) findViewById(R.id.imgbtnclose);
        tvDiaryList = findViewById(R.id.tvDiaryList);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvDiaryContent = findViewById(R.id.tvDiaryContent);



        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });
        imgbtnclose.setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        tvDiaryList.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            // 이미 일기 목록이므로 이동 없음
        });
        rvCalendar = findViewById(R.id.rvCalendar);
        tvYearMonth = findViewById(R.id.tvYearMonth);

        // 7칸 그리드 (일~토)
        rvCalendar.setLayoutManager(new GridLayoutManager(this, 7));

        calendarAdapter = new CalendarAdapter();
        rvCalendar.setAdapter(calendarAdapter);

        // 현재 달 설정
        LocalDate now = LocalDate.now();
        currentYear = now.getYear();      // 🔥 이 줄
        currentMonth = now.getMonthValue(); // 🔥 이 줄

        calendarAdapter.setMonth(currentYear, currentMonth);
        tvYearMonth.setText(currentYear + "년 " + currentMonth + "월");




        SharedPreferences sp = getSharedPreferences("diary", MODE_PRIVATE);

        Map<String, ?> allEntries = sp.getAll();
        diaryDates.clear();
        diaryDates.addAll(allEntries.keySet());

        calendarAdapter.setDiaryDates(diaryDates);

        calendarAdapter.setOnDayClickListener(day -> {
            String date = currentYear + "-" +
                    String.format("%02d", currentMonth) + "-" +
                    String.format("%02d", day);

            tvSelectedDate.setText(date);

            String memo = sp.getString(date, "작성된 일기가 없습니다.");
            tvDiaryContent.setText(memo);
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();   // 기본 동작: 이전 Activity로 돌아감
    }

}
