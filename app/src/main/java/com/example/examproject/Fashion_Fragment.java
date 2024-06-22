package com.example.examproject;

import android.view.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class Fashion_Fragment extends Fragment {

    private static final String ARG_TEMP = "argTemp";
    TextView outername, topname, bottomname;
    ImageView outerimg, topimg, bottomimg;

    View view;
    public static Fashion_Fragment newInstance(int nowTemp) {
        Fashion_Fragment fragment = new Fashion_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEMP, nowTemp);
        fragment.setArguments(args);
        return fragment;
    }

    public void suggest(int temp){

        // 23도 이상
        String[] Outerwear23 = {"바람막이", "져지" };
        String[] Tops23 = {"반팔 티셔츠", "얇은 셔츠"};
        String[] Bottoms23 = {"반바지", "면바지", "치마"};

        // 17~22도
        String[] Outerwear17to22 = {"가디건" , "재킷" , "바람막이"};
        String[] Tops17to22 = {"긴팔 티셔츠", "셔츠", "반팔 티셔츠"};
        String[] Bottoms17to22 = {"반바지", "면바지", "슬랙스"};

        // 12~16도
        String[] Outerwear12to16 = {"재킷", "가디건" , "바람막이"};
        String[] Tops12to16 = {"맨투맨", "셔츠", "후드티"};
        String[] Bottoms12to16 = {"청바지", "면바지" , "슬랙스"};

        // 9~11도
        String[] Outerwear9to11 = {"재킷", "점퍼", "코트"};
        String[] Tops9to11 = {"맨투맨" , "기모 후드티", "니트"};
        String[] Bottoms9to11 = {"청바지", "면바지",  "기모 바지"};

        // 5~8도
        String[] Outerwear5to8 = {"패딩" , "야상", "코트"};
        String[] Tops5to8 = {"맨투맨" , "기모 후드티", "니트"};
        String[] Bottoms5to8 = { "청바지", "두꺼운 바지", "기모 바지"};

        Random rand = new Random();

        TextView outername = view.findViewById(R.id.outername);
        TextView topname = view.findViewById(R.id.topname);
        TextView bottomname = view.findViewById(R.id.bottomname);

        ImageView outerimg = view.findViewById(R.id.outerimg);
        ImageView topimg = view.findViewById(R.id.topimg);
        ImageView bottomimg = view.findViewById(R.id.bottomimg);

        String OuterItem = null, TopItem = null, BottomItem = null;


        int randomIndex;
        if(temp <= 23){
            randomIndex = rand.nextInt(Outerwear23.length);
            OuterItem = Outerwear23[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops23.length);
            TopItem= Tops23[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms23.length);
            BottomItem = Bottoms23[randomIndex];
            bottomname.setText(BottomItem);

        } else if(20 <= temp && temp <= 22){

        }
        switch (OuterItem){
            case "바람막이":
                outerimg.setBackgroundResource(R.drawable.);
        }









    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 인플레이트합니다.
        view = inflater.inflate(R.layout.activity_today_fashion, container, false);

        if (getArguments() != null) {
            int temp = getArguments().getInt(ARG_TEMP);

            suggest(temp);

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(String.valueOf(temp)); // 정수를 문자열로 변환하여 설정
        }

        return view;
    }

}
