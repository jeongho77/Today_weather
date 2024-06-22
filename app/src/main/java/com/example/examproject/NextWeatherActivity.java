package com.example.examproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import static android.content.ContentValues.TAG;
public class NextWeatherActivity extends AppCompatActivity {
    static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_weather);

        Intent intent = getIntent();
        String jsonArrayString = intent.getStringExtra("jsonArray");
        String text = intent.getStringExtra("text");

        int int_hour = intent.getIntExtra("hour", -1);
        layoutSet_Background(int_hour);


        // 텍스트를 설정할 TextView
        TextView textView = findViewById(R.id.m_temp);
        if (text != null) {
            textView.setText(text);
        }

        if (jsonArrayString != null) {
            try {
                JSONArray list = new JSONArray(jsonArrayString);
                WeatherUtils weatherUtils = new WeatherUtils(this);
                weatherUtils.getJsonItem(list);
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void layoutSet_Background(int int_hour){

        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        Log.d(TAG, "nextweather : " + int_hour);

        if(21 <= int_hour || int_hour <= 05){ //21시~ 05시
            mainLayout.setBackgroundResource(R.drawable.night_bg);
        }else if(int_hour >= 06 && int_hour <= 9){ //06시 ~ 9시
            mainLayout.setBackgroundResource(R.drawable.nightcloud_bg);
        }else if(int_hour >= 10 && int_hour < 17){ //10시 ~ 16시
            mainLayout.setBackgroundResource(R.drawable.morning_bg);
        }else{ //17시~21시
            mainLayout.setBackgroundResource(R.drawable.afternoon_bg);
        }
    }

    int j = 0;
    String[] array = new String[8];
    public void setWeatherIcon(int i, String des){
        j++;

        String weather_Id = "weather_" + i;
        int weather_resId = getResources().getIdentifier(weather_Id ,"id", getPackageName());
        ImageView weather_v = findViewById(weather_resId);

        array[j] = des;

        if(j == 7) {

            int cloudyCount = 0;
            int overcastCount = 0;
            int rainCount = 0;

            for (String weather : array) {
                if (weather != null) { // null이 아닌지 확인합니다.
                    if (weather.contains("흐림") || weather.contains("튼구름")) {
                        cloudyCount++;
                    }

                    if (weather.contains("맑음") || weather.contains("조금")) {
                        overcastCount++;
                    }
                    if (weather.contains("비")) {
                        rainCount++;

                    }
                }
            }

            if (rainCount > 0) {
                weather_v.setBackgroundResource(R.drawable.pink_rain);
            } else if (overcastCount > cloudyCount) {
                weather_v.setBackgroundResource(R.drawable.pink_sun);
            } else {
                weather_v.setBackgroundResource(R.drawable.pink_cloudy);
            }

            Arrays.fill(array, null);
            j = 0;
        }
    }


    public void setDate(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.DATE, i);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based, so add 1
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        String[] days = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};

        String day_Id = "day_" + i;

        int day_resId = getResources().getIdentifier(day_Id, "id", getPackageName());

        TextView day = findViewById(day_resId);

        String formattedDate = String.format("%d/%d %s", month, dayOfMonth, days[dayOfWeek - 1]);
        day.setText(formattedDate);
    }


    public void setTemperatureValues(double maxTemp, double minTemp, int i) {
        setDate(i);

        String maxtemp_Id = "maxtemp_" + i;
        String mintemp_Id = "mintemp_" + i;

        int max_resId = getResources().getIdentifier(maxtemp_Id, "id", getPackageName());
        int min_resId = getResources().getIdentifier(mintemp_Id, "id", getPackageName());


        TextView maxTempView = findViewById(max_resId);
        TextView minTempView = findViewById(min_resId);

        int maxTempInt = getIntegerPart(maxTemp);
        int minTempInt = getIntegerPart(minTemp);

        Log.d(TAG, "MAX: "+maxTempInt+"min" + minTempView);

        if (maxTempView != null) {
            maxTempView.setText(String.valueOf(maxTempInt) + "°");
        }
        if (minTempView != null) {
            minTempView.setText(String.valueOf(minTempInt) + "°");
        }
    }

    public static int getIntegerPart(double temperature) {
        return (int) Math.floor(temperature); // 소수점 이하 버림
    }

    public static String getTime(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Add specified number of days to the current date
        calendar.add(Calendar.DATE, i);
        String Date2 = mFormat.format(calendar.getTime());
        Log.d(TAG, "DATEFORMAT : " + Date2);
        return Date2; // Return the formatted date string
    }

    private static class WeatherUtils {

        private final Context context;

        public WeatherUtils(Context context) {
            this.context = context;
        }

        public void getJsonItem(JSONArray list) throws JSONException, ParseException {

            double maxTemp = Double.MIN_VALUE;
            double minTemp = Double.MAX_VALUE;
            int day_Count = 0;
            int getTime_count = 1;
            int xml_count = 1;
            Log.d(TAG, "run");

            for (int i = 2; i < list.length(); i++) { //2번째부터 시작

                JSONObject item = list.getJSONObject(i);
                String dt_txt = item.getString("dt_txt");
                Date date = mFormat.parse(dt_txt);
                String formattedDate = mFormat.format(date);
                JSONObject main = item.getJSONObject("main");
                JSONArray weatherArray = item.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String description = weather.getString("description");

                Log.d(TAG, "list : " + list.length());
                Log.d(TAG, "formattedDate" + formattedDate);
                if (formattedDate.equals(NextWeatherActivity.getTime(getTime_count))) {
                    day_Count += 1; // 7번이 되면 초기화 날짜가 바뀜
                    double temp = main.getDouble("temp");
                    ((NextWeatherActivity) context).setWeatherIcon(xml_count, description);

                    Log.d(TAG, "maxTemp" + maxTemp + "minTemp : " + minTemp);

                    if (temp > maxTemp) {
                        maxTemp = temp;
                    }
                    if (temp < minTemp) {
                        minTemp = temp;
                    }

                    Log.d(TAG, "day_count : " + day_Count);
                    if (day_Count == 7 || list.length()-1 == i) {

                        getTime_count++; // 다음날 데이터받게함
                        ((NextWeatherActivity) context).setTemperatureValues(maxTemp, minTemp, xml_count);

                        maxTemp = Double.MIN_VALUE;
                        minTemp = Double.MAX_VALUE;
                        xml_count++;

                        Log.d(TAG, "Temperature : " + maxTemp + minTemp + xml_count);

                        day_Count = 0; // 리셋
                    }


                    Log.d(TAG, "Formatted date: " + formattedDate);
                }
            }
        }
    }
}
