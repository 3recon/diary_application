package com.changhyeon.one_line_memo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    ImageButton btnPrevMonth, btnNextMonth;

//    Button btnDelete;
    ImageButton btnDelete;

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
        ImageButton btnPrevMonth = findViewById(R.id.btnPrevMonth);
        ImageButton btnNextMonth = findViewById(R.id.btnNextMonth);
        btnDelete=findViewById(R.id.btnDelete);

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
        currentYear = now.getYear();
        currentMonth = now.getMonthValue();

        calendarAdapter.setMonth(currentYear, currentMonth);
        tvYearMonth.setText(currentYear + "년 " + currentMonth + "월");
        //달력 초기화
        updateCalendar();

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

            // ✅ 삭제 버튼 표시 / 숨김
            if (sp.contains(date)) {
                btnDelete.setVisibility(View.VISIBLE);
            } else {
                btnDelete.setVisibility(View.GONE);
            }
        });


        //월 넘기기 버튼
        btnPrevMonth.setOnClickListener(v -> {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            updateCalendar();
        });

        btnNextMonth.setOnClickListener(v -> {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            updateCalendar();
        });

        //삭제 버튼 btnDelete
        btnDelete.setOnClickListener(v->{
            AlertDialog outerdialog=new AlertDialog.Builder(this,R.style.RoundedDialog)
                    .setMessage("부끄럽거나 지우고 싶더라도 소중한 기억이에요\n정말 삭제하시겠어요?\uD83D\uDE22")
                    .setPositiveButton("취소", null)
                    .setNegativeButton("삭제", (dialog, which) -> {
                        // 선택된 날짜 가져오기
                        String dateKey = tvSelectedDate.getText().toString();

                        // 이미 있는 sp 변수 사용
                        sp.edit().remove(dateKey).apply();

                        // 화면 갱신: 일기 내용 초기화
                        tvDiaryContent.setText("작성된 일기가 없습니다");

                        // 달력 마커 갱신
                        diaryDates.remove(dateKey);
                        calendarAdapter.setDiaryDates(diaryDates);
                        btnDelete.setVisibility(View.GONE); // 일기 삭제 시 버튼 숨김


                        new AlertDialog.Builder(this,R.style.RoundedDialog)
                                .setMessage("일기가 삭제되었습니다..")
                                .show();
                        // 버튼 색 변경


                    })
                    .show();
            outerdialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK); // 취소 버튼
            outerdialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);   // 삭제 버튼
        });
        // ✅ 처음 선택 날짜를 오늘로 설정
        int todayDay = now.getDayOfMonth();

        String todayDate = currentYear + "-" +
                String.format("%02d", currentMonth) + "-" +
                String.format("%02d", todayDay);

        // 상단 날짜 표시
        tvSelectedDate.setText(todayDate);

        // 일기 내용 표시
        String memo = sp.getString(todayDate, "작성된 일기가 없습니다.");
        tvDiaryContent.setText(memo);

        // 🔥 삭제 버튼 상태 갱신 (중요!!)
        if (sp.contains(todayDate)) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

    }
    private void updateCalendar() {
        // 상단 "2026년 1월"
        tvYearMonth.setText(currentYear + "년 " + currentMonth + "월");

        // 달력 갱신
        calendarAdapter.setMonth(currentYear, currentMonth);

        // 🔴 마커 갱신
        SharedPreferences sp = getSharedPreferences("diary", MODE_PRIVATE);
        Map<String, ?> allEntries = sp.getAll();

        Set<String> diaryDates = new HashSet<>();
        for (String key : allEntries.keySet()) {
            if (key.startsWith(currentYear + "-" + String.format("%02d", currentMonth))) {
                diaryDates.add(key);
            }
        }

        calendarAdapter.setDiaryDates(diaryDates);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();   // 기본 동작: 이전 Activity로 돌아감
    }

}
