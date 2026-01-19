package com.example.one_line_memo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    private final List<Integer> days = new ArrayList<>();
    private OnDayClickListener listener;
    private int year;
    private int month;
    private Set<String> diaryDates = new HashSet<>();

    public void setDiaryDates(Set<String> dates) {
        this.diaryDates = dates;
        notifyDataSetChanged();
    }



    public interface OnDayClickListener {
        void onDayClick(int day);
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.listener = listener;
    }

    public void setMonth(int year, int month) {
        this.year = year;
        this.month = month;

        days.clear();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue(); // 월=1

        int empty = firstDayOfWeek % 7;
        for (int i = 0; i < empty; i++) {
            days.add(0);
        }

        int lastDay = firstDay.lengthOfMonth();
        for (int i = 1; i <= lastDay; i++) {
            days.add(i);
        }

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        int day = days.get(position);

        if (day == 0) {
            holder.tvDay.setText("");
            holder.dot.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(null);
            return;
        }

        holder.tvDay.setText(String.valueOf(day));

        // 요일 색상 변경
        int dayOfWeek = (position % 7); // 0:일요일, 6:토요일 기준 (GridLayoutManager 7열)
        if (dayOfWeek == 0) {
            holder.tvDay.setTextColor(Color.parseColor("#E57373"));       // 일요일 빨강
        } else if (dayOfWeek == 6) {
            holder.tvDay.setTextColor(Color.parseColor("#177ed1"));      // 토요일 파랑
        } else {
            holder.tvDay.setTextColor(Color.BLACK);     // 평일 검정
        }

        String dateKey = year + "-" +
                String.format("%02d", month) + "-" +
                String.format("%02d", day);

        if (diaryDates.contains(dateKey)) {
            holder.dot.setVisibility(View.VISIBLE);
        } else {
            holder.dot.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDayClick(day);
        });
    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        View dot;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            dot = itemView.findViewById(R.id.dot);
        }
    }

}
