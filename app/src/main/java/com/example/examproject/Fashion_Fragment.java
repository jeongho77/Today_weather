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
        String[] Outerwear23 = {"바람막이", "져지", "" , ""};
        String[] Tops23 = {"반팔 티셔츠", "셔츠"};
        String[] Bottoms23 = {"반바지", "면바지", "치마"};

        // 17~22도
        String[] Outerwear17to22 = {"가디건" , "져지" , "바람막이", ""};
        String[] Tops17to22 = {"긴팔 티셔츠", "셔츠", "반팔 티셔츠"};
        String[] Bottoms17to22 = {"반바지", "면바지", "슬랙스"};

        // 12~16도
        String[] Outerwear12to16 = {"자켓", "가디건" , "바람막이"};
        String[] Tops12to16 = {"맨투맨", "셔츠", "후드티"};
        String[] Bottoms12to16 = {"청바지", "면바지" , "슬랙스"};

        // 9~11도
        String[] Outerwear9to11 = {"재킷", "항공점퍼", "코트"};
        String[] Tops9to11 = {"맨투맨" , "기모 후드티", "니트"};
        String[] Bottoms9to11 = {"청바지", "면바지",  "기모 바지"};

        // 5~8도
        String[] Outerwear5to8 = {"패딩" , "야상", "항공점퍼"};
        String[] Tops5to8 = {"맨투맨" , "기모 후드티", "니트"};
        String[] Bottoms5to8 = { "청바지", "체육복", "기모 바지"};

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
            randomIndex = rand.nextInt(Outerwear17to22.length);
            OuterItem = Outerwear17to22[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops17to22.length);
            TopItem= Tops17to22[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms17to22.length);
            BottomItem = Bottoms17to22[randomIndex];
            bottomname.setText(BottomItem);
        } else if (17 <= temp && temp <= 19) {
            randomIndex = rand.nextInt(Outerwear12to16.length);
            OuterItem = Outerwear12to16[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops12to16.length);
            TopItem = Tops12to16[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms12to16.length);
            BottomItem = Bottoms12to16[randomIndex];
            bottomname.setText(BottomItem);
        } else if (12 <= temp && temp <= 16) {
            randomIndex = rand.nextInt(Outerwear12to16.length);
            OuterItem = Outerwear12to16[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops12to16.length);
            TopItem = Tops12to16[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms12to16.length);
            BottomItem = Bottoms12to16[randomIndex];
            bottomname.setText(BottomItem);

        } else if (9 <= temp && temp <= 11) {
            randomIndex = rand.nextInt(Outerwear9to11.length);
            OuterItem = Outerwear9to11[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops9to11.length);
            TopItem = Tops9to11[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms9to11.length);
            BottomItem = Bottoms9to11[randomIndex];
            bottomname.setText(BottomItem);

        } else if (5 <= temp && temp <= 8) {
            randomIndex = rand.nextInt(Outerwear5to8.length);
            OuterItem = Outerwear5to8[randomIndex];
            outername.setText(OuterItem);

            randomIndex = rand.nextInt(Tops5to8.length);
            TopItem = Tops5to8[randomIndex];
            topname.setText(TopItem);

            randomIndex = rand.nextInt(Bottoms5to8.length);
            BottomItem = Bottoms5to8[randomIndex];
            bottomname.setText(BottomItem);
        }

        switch (OuterItem){
            case "바람막이":
                outerimg.setBackgroundResource(R.drawable.windbreaker1);
                break;
            case "져지" :
                outerimg.setBackgroundResource(R.drawable.jersey);
                break;
            case "가디건" :
                outerimg.setBackgroundResource(R.drawable.cardigan);
                break;
            case "자켓" :
                outerimg.setBackgroundResource(R.drawable.jacket);
                break;
            case "코트" :
                outerimg.setBackgroundResource(R.drawable.coat);
                break;
            case "야상" :
                outerimg.setBackgroundResource(R.drawable.yasang);
                break;
            case "패딩" :
                outerimg.setBackgroundResource(R.drawable.padding);
                break;
            case "항공점퍼" :
                outerimg.setBackgroundResource(R.drawable.hanggong);
                break;
            case "" :
                break;
        }

        switch (TopItem) {
            case "반팔 티셔츠" :
                topimg.setBackgroundResource(R.drawable.tshirt);
                break;
            case "셔츠" :
                topimg.setBackgroundResource(R.drawable.shirt);
                break;
            case "긴팔 티셔츠" :
                topimg.setBackgroundResource(R.drawable.longsleeve);
                break;
            case "맨투맨" :
                topimg.setBackgroundResource(R.drawable.sweatshirt);
                break;
            case "후드티" :
                topimg.setBackgroundResource(R.drawable.hoodie);
                break;
            case "니트" :
                topimg.setBackgroundResource(R.drawable.knitted);
                break;
            case "기모 후드티" :
                topimg.setBackgroundResource(R.drawable.gimohoodi);
                break;
        }

        switch (BottomItem) {
            case "반바지" :
                bottomimg.setBackgroundResource(R.drawable.pairshorts);
                break;
            case "면바지" :
                bottomimg.setBackgroundResource(R.drawable.cottonpants);
                break;
            case "치마" :
                bottomimg.setBackgroundResource(R.drawable.skirt);
                break;
            case "슬랙스" :
                bottomimg.setBackgroundResource(R.drawable.slacks);
                break;
            case "청바지" :
                bottomimg.setBackgroundResource(R.drawable.jeans);
                break;
            case "기모 바지":
                bottomimg.setBackgroundResource(R.drawable.gimo);
                break;
            case "체육복" :
                bottomimg.setBackgroundResource(R.drawable.gymbazy);
                break;
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
        }
        return view;
    }

}
