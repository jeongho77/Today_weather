package com.example.examproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ApiExplorer";
    private Handler handler = new Handler();
    long mNow;
    String date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
    String period = "AM";
    JSONArray list = null;
    Button next_btn;
    int int_hour, frag_temp, hour1;
    public LinearLayout mainLayout;

    String temp;


    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {

            getTime();
            handler.postDelayed(this, 10000); // 10초마다 현재 시간 갱신
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

        next_btn = findViewById(R.id.next_btn);
        Button fashion_btn = findViewById(R.id.fashion_btn);
        fashion_btn.setOnClickListener(new View.OnClickListener() {

            int trigger = 0;
            @Override
            public void onClick(View v) {
                switch (trigger) {
                    case 0:
                        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

                        Fashion_Fragment myFragment = Fashion_Fragment.newInstance(frag_temp);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, myFragment);
                        fragmentTransaction.commit();
                        trigger = 1;
                        break;
                    case 1:
                        findViewById(R.id.fragment_container).setVisibility(View.GONE);
                        trigger = 0;
                        break;
                    default:
                        // 기본 동작 (필요에 따라 추가)
                        break;
                }
            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            ImageView imageView = findViewById(R.id.mainIcon);
            TextView textView = findViewById(R.id.Main_temp);
            LinearLayout mainLayout = findViewById(R.id.mainLayout);

            String text = textView.getText().toString();

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NextWeatherActivity.class);
                intent.putExtra("jsonArray", list.toString());
                intent.putExtra("hour", hour1);
                intent.putExtra("text", text);
                startActivity(intent);
            }
        });
    }



    public String getTime(){
        //날짜 가져오는 함수

        mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);

        date = mFormat.format(mDate);

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm"); //시간
        SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd"); //날짜
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault()); // 요일

        String tDate = tFormat.format(mDate); //시간
        String dDate = dFormat.format(mDate); //날짜
        String subDay = dayFormat.format(mDate); //요일

        String day = subDay.substring(0,1);

        int colonIndex = tDate.indexOf(':');
        int_hour = Integer.parseInt(tDate.substring(0, colonIndex)); // 시간 (HH)
        int int_minute = Integer.parseInt(tDate.substring(colonIndex + 1)); // 분 (MM)

        colonIndex = dDate.indexOf('-');
        int int_day = Integer.parseInt(dDate.substring(0, colonIndex));
        int int_month = Integer.parseInt(dDate.substring(colonIndex + 1));

        TextView Tv_time = findViewById(R.id.time);
        TextView Tv_date = findViewById(R.id.date);

        mainLayout = findViewById(R.id.mainLayout);

        if(21 <= int_hour || int_hour <= 05){ //21시~ 05시
            mainLayout.setBackgroundResource(R.drawable.night_bg);
        }else if(int_hour >= 06 && int_hour <= 9){ //06시 ~ 9시
            mainLayout.setBackgroundResource(R.drawable.nightcloud_bg);
        }else if(int_hour >= 10 && int_hour < 17){ //10시 ~ 16시
            mainLayout.setBackgroundResource(R.drawable.morning_bg);
        }else{ //17시~21시
            mainLayout.setBackgroundResource(R.drawable.afternoon_bg);
        }

        hour1 = int_hour;

        if(int_hour >= 12){ //12시를 넘으면 PM , 12:50분가능
            period = "PM";
            if(int_hour > 12) { // 13시이상이면 12빼서 1시로 만들어주기
                int_hour -= 12;
                }
        }
        String formattedTime = String.format(Locale.getDefault(), "|  %s %02d : %02d", period, int_hour, int_minute);
        String formattedDay = String.format(Locale.getDefault(), "%02d/%02d (%s)",int_day, int_month, day);
        Tv_time.setText(formattedTime);
        Tv_date.setText(formattedDay);

        Log.d(TAG, "tFormat:  " + formattedTime);

        return date;
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
            StringBuilder result = null; //HTTP 응답 데이터 받을공간
            try {
                result = new StringBuilder(fetchData_Api());
            } catch (Exception e) {
                Log.e(TAG, "Error: ", e);
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.trim().isEmpty()) {
                try {
                    parseDataAndUpdateUI(new StringBuilder(result));
                } catch (JSONException | ParseException e) {
                    Log.e(TAG, "Error: ", e);
                }
            }
        }

        protected String fetchData_Api() {
            StringBuilder result = new StringBuilder();
            HttpURLConnection conn = null;
            BufferedReader rd = null;
            try {
                StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/forecast");
                urlBuilder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=35.5936");
                urlBuilder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode("129.352", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + URLEncoder.encode("7a87c8b563a09149247b0f3204175970", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode("kr", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=" + URLEncoder.encode("metric", "UTF-8"));

                URL url = new URL(urlBuilder.toString());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                Log.d(TAG, "urlBuilder: " + urlBuilder.toString());
                Log.d(TAG, "Responsecode: " + conn.getResponseCode());

                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException in fetchData_Api: ", e);
            } catch (IOException e) {
                Log.e(TAG, "IOException in fetchData_Api: ", e);
            } catch (Exception e) {
                Log.e(TAG, "General Exception in fetchData_Api: ", e);
            } finally {
                if (rd != null) {
                    try {
                        rd.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing BufferedReader: ", e);
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }

            Log.d(TAG, "result: " + result.toString());
            return result.toString();
        }



        private void parseDataAndUpdateUI(StringBuilder result) throws JSONException, ParseException {
            JSONObject response = new JSONObject(result.toString());
            list = response.getJSONArray("list");

            for (int i = 2; i < list.length(); i++) { //i = 0은 6시간전이라 필요없음 3시간전까지만 유효 3시일때 전날 21시 데이터 받는거 방지

                JSONObject item = list.getJSONObject(i); //날씨 item 오브젝트로 받아옴
                Date dtTxt_date = dateFormat.parse(item.getString("dt_txt"));
                String dTxt = mFormat.format(dtTxt_date); //날짜만 yy:mm:dd 형식
                String tTxt = tFormat.format(dtTxt_date); //시간 hh:mm 형식
                int colonIndex = tTxt.indexOf(':');
                int int_hour = Integer.parseInt(tTxt.substring(0, colonIndex)); // 시간 (HH)

                JSONObject main = item.getJSONObject("main");

                Log.d(TAG , "dtTxt: " + dTxt + "date :" + date);
                Log.d(TAG, "item: " + item.toString());

                String double_temp = main.getString("temp"); // 기온
                int dotIndex = double_temp.indexOf('.');

                if (dotIndex != -1) {
                    // 점이 문자열에 있는 경우
                    temp = double_temp.substring(0, dotIndex) + "℃";
                    frag_temp = Integer.parseInt(double_temp.substring(0, dotIndex));
                } else {
                    // 점이 문자열에 없는 경우, 전체 문자열을 사용
                    temp = double_temp + "℃";
                    frag_temp = Integer.parseInt(double_temp);
                }

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

                if(i <= 7){ //메인페이지 고정으로 출력해주기 6개까지 해줘야함

                    TextView Main_temp = findViewById(R.id.Main_temp);
                    ImageView mainIcon = findViewById(R.id.mainIcon);
                    if(i == 2){
                        Main_temp.setText(temp);


                    }
                    // 동적으로 ID 생성
                    String TimeV_Id = "mwTime_" + i;
                    int Time_resId = getResources().getIdentifier(TimeV_Id , "id", getPackageName());

                    String TempV_Id = "mwTemp_" + i;
                    int Temp_resId = getResources().getIdentifier(TempV_Id, "id", getPackageName());

                    String IconV_Id = "mwIcon_" + i;
                    int Icon_resId = getResources().getIdentifier(IconV_Id, "id", getPackageName());

                    // 해당 ID의 TextView 찾기
                    TextView mwTime = findViewById(Time_resId);
                    TextView mwTemp = findViewById(Temp_resId);
                    ImageView mwIcon = findViewById(Icon_resId);

                    Log.d(TAG, "mwTime" + mwTime + "tTxt" + tTxt);

                    if (mwTime != null && mwTemp != null && mwIcon != null) {
                        // TextView 설정 (예: 텍스트 설정)
                        mwTime.setText(tTxt);
                        mwTemp.setText(temp);

                        switch (description) {
                            case "맑음":
                                if(period == "AM") {
                                    mwIcon.setBackgroundResource(R.drawable.sun_ic);

                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.msun_ic);
                                    }
                                    if(int_hour == 00 || int_hour == 03) {
                                        mwIcon.setBackgroundResource(R.drawable.union_ic);
                                        if (i == 2) {
                                            mainIcon.setBackgroundResource(R.drawable.union_ic);
                                        }
                                    }
                                }else{
                                    mwIcon.setBackgroundResource(R.drawable.union_ic);
                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.union_ic);
                                    }
                                }

                                break;
                            case "구름조금":
                                if(period == "AM") {
                                    mwIcon.setBackgroundResource(R.drawable.suncloud_ic);

                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.msuncloud_ic);
                                    }
                                    if(int_hour == 00 || int_hour == 03) {
                                        mwIcon.setBackgroundResource(R.drawable.nightcloud_ic);
                                        if (i == 2) {
                                            mainIcon.setBackgroundResource(R.drawable.mnightcloud_ic);
                                        }
                                    }
                                }else{
                                    mwIcon.setBackgroundResource(R.drawable.nightcloud_ic);
                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.mnightcloud_ic);
                                    }
                                }
                                break;
                            case "튼구름":
                                if(period == "AM") {
                                    mwIcon.setBackgroundResource(R.drawable.suncloud_ic);
                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.msuncloud_ic);
                                    }
                                    if(int_hour == 00 || int_hour == 03) {
                                        mwIcon.setBackgroundResource(R.drawable.nightcloud_ic);
                                        if (i == 2) {
                                            mainIcon.setBackgroundResource(R.drawable.mnightcloud_ic);
                                        }
                                    }
                                }else{
                                    mwIcon.setBackgroundResource(R.drawable.nightcloud_ic);
                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.mnightcloud_ic);
                                    }
                                }
                                break;
                            case "온흐림":
                                mwIcon.setBackgroundResource(R.drawable.blur_ic);
                                if(i == 2){
                                    mainIcon.setBackgroundResource(R.drawable.mblur_ic);
                                }
                                break;
                            case "약간의 구름이 낀 하늘" :
                                if(period == "AM") {
                                    mwIcon.setBackgroundResource(R.drawable.sun_ic);

                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.msun_ic);
                                    }
                                    if(int_hour == 00 || int_hour == 03) {
                                        mwIcon.setBackgroundResource(R.drawable.union_ic);
                                        if (i == 2) {
                                            mainIcon.setBackgroundResource(R.drawable.union_ic);
                                        }
                                    }
                                }else{
                                    mwIcon.setBackgroundResource(R.drawable.union_ic);
                                    if(i == 2){
                                        mainIcon.setBackgroundResource(R.drawable.union_ic);
                                    }
                                }
                                break;
                            case "실 비" :
                                mwIcon.setBackgroundResource(R.drawable.rain_ic);
                                if(i == 2){
                                    mainIcon.setBackgroundResource(R.drawable.mrain_ic);
                                }
                                break;
                        }

                        // 디버그 로그 출력
                        Log.d("TextViewSetup", "설정된 TextView ID: " + Time_resId);
                    } else {
                        // 디버그 로그 출력
                        Log.e("TextViewSetup", "TextView를 찾을 수 없음: " + Time_resId);
                    }
                }

                Log.d(TAG, "weather description = " + description);

            }
        }

    }
}
