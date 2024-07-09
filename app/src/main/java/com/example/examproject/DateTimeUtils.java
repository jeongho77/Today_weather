package com.example.examproject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final String TAG = "DateTimeUtils";
    private static long mNow;
    private static String date;
    private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static int hour1;
    private static String period;

    public static String getTime(Context context) {
        // 날짜 가져오는 함수
        mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);

        date = mFormat.format(mDate);

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault()); // 시간
        SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd", Locale.getDefault()); // 날짜
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault()); // 요일

        String tDate = tFormat.format(mDate); // 시간
        String dDate = dFormat.format(mDate); // 날짜
        String subDay = dayFormat.format(mDate); // 요일

        String day = subDay.substring(0, 1);

        int colonIndex = tDate.indexOf(':');
        int int_hour = Integer.parseInt(tDate.substring(0, colonIndex)); // 시간 (HH)
        int int_minute = Integer.parseInt(tDate.substring(colonIndex + 1)); // 분 (MM)

        colonIndex = dDate.indexOf('-');
        int int_day = Integer.parseInt(dDate.substring(0, colonIndex));
        int int_month = Integer.parseInt(dDate.substring(colonIndex + 1));

        TextView Tv_time = null;
        TextView Tv_date = null;
        View mainLayout = null;

        try {
            Tv_time = ((AppCompatActivity) context).findViewById(R.id.time);
            Tv_date = ((AppCompatActivity) context).findViewById(R.id.date);
            mainLayout = ((AppCompatActivity) context).findViewById(R.id.mainLayout);

            if (21 <= int_hour || int_hour <= 5) { // 21시~ 05시
                mainLayout.setBackgroundResource(R.drawable.night_bg);
            } else if (int_hour >= 6 && int_hour <= 9) { // 06시 ~ 9시
                mainLayout.setBackgroundResource(R.drawable.nightcloud_bg);
            } else if (int_hour >= 10 && int_hour < 17) { // 10시 ~ 16시
                mainLayout.setBackgroundResource(R.drawable.morning_bg);
            } else { // 17시~21시
                mainLayout.setBackgroundResource(R.drawable.afternoon_bg);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting layout background: ", e);
        }

        hour1 = int_hour;

        if (int_hour >= 12) { // 12시를 넘으면 PM , 12:50분가능
            period = "PM";
            if (int_hour > 12) { // 13시 이상이면 12 빼서 1시로 만들어주기
                int_hour -= 12;
            }
        } else {
            period = "AM";
        }

        String formattedTime = String.format(Locale.getDefault(), "|  %s %02d : %02d", period, int_hour, int_minute);
        String formattedDay = String.format(Locale.getDefault(), "%02d/%02d (%s)", int_day, int_month, day);

        if (Tv_time != null) {
            Tv_time.setText(formattedTime);
        }

        if (Tv_date != null) {
            Tv_date.setText(formattedDay);
        }

        Log.d(TAG, "tFormat:  " + formattedTime);

        return date;
    }
}
