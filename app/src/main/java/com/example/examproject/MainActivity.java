package com.example.examproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ApiExplorer";
    private Handler handler = new Handler();

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            String currentTime = getCurrentTime();
//            timeTextView.setText(currentTime);
//            Log.d("CurrentTime", "현재 시간: " + currentTime);
            handler.postDelayed(this, 1000); // 1초마다 현재 시간 갱신
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.post(updateTimeRunnable); // Runnable 실행

//        // TextView에 현재 시간 표시
//        TextView timeTextView = findViewById(R.id.timeTextView);
//        timeTextView.setText(currentTime);

        // API 호출을 비동기 작업으로 실행
        Log.d(TAG, "Time: " + getTime());
        new ApiExplorerTask().execute();
    }

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        return mFormat.format(mDate);
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getDefault();
        calendar.setTimeZone(timeZone);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeRunnable); // Activity가 파괴될 때 Runnable 제거
    }


    // AsyncTask 클래스
    private class ApiExplorerTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/forecast");
                urlBuilder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=35.5936");
                urlBuilder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode("129.352", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + URLEncoder.encode("7a87c8b563a09149247b0f3204175970", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode("kr", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=" + URLEncoder.encode("metric", "UTF-8"));

                URL url = new URL(urlBuilder.toString());
                // 요청 헤더 생성, 유형 json으로 받아옴
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                Log.d(TAG, "urlBuilder: " + urlBuilder);
                Log.d(TAG, "Responsecode: " + conn.getResponseCode());

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line); // API 응답을 result StringBuilder에 추가
                    }
                    rd.close();
                } else {
                    // 실패 시 errorstream
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        // 에러 응답을 읽어 처리
                        Log.e(TAG, "Error Response: " + line);
                    }
                    rd.close();
                }

                // JSON 파싱
                JSONObject response = new JSONObject(result.toString());
                JSONArray list = response.getJSONArray("list");

                for (int i = 2; i < list.length(); i++) { //i = 0은 6시간전이라 필요없음 3시간전까지만 유효 3시일때 전날 21시 데이터 받는거 방지
                    JSONObject item = list.getJSONObject(i);
                    Log.d(TAG, "item: " + item.toString());

                    String dtTxt = item.getString("dt_txt");

                    JSONObject main = item.getJSONObject("main");
                    String temp = main.getString("temp"); //기온
                    String humidity = main.getString("humidity"); //습도

                    JSONArray weatherArray = item.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String description = weather.getString("description"); //날씨 상태

                    double pop = item.getDouble("pop");
                    double rainVolume = 0;
                    if (item.has("rain")) { //아이템이 있을때도 없을때도 있음
                        JSONObject rain = item.getJSONObject("rain");
                        if (rain.has("3h")) { //3시간동안의 강수량을 보여주기에 3h로 해줘야 가져올수 있음
                            rainVolume = rain.getDouble("3h");
                        }
                    }

                    Log.d(TAG, "weather dxTxt = " + dtTxt);
                    Log.d(TAG, "weather temp = " + temp);
                    Log.d(TAG, "weather humidity = " + humidity);
                    Log.d(TAG, "weather description = " + description);
                    Log.d(TAG, "weather pop = " + pop);
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error: ", e);
            }
            return result.toString();
        }
    }
}
