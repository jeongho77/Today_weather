## 프로젝트 소개
* Weather API - OpenWeatherMap를 사용하여 날씨 데이터를 받아왔습니다.
* 울산의 지역별로 날씨를 조회 할 수 있습니다.(중구,남구,동구,북구,울주군)
* 최대 4~5일의 날씨를 알 수 있습니다.
* 현재 기온에 맞는 옷을 추천 받을 수 있습니다.

## 팀원 구성
|서정호|이상협
|---|---
|백엔드|프론트엔드, 디자인
|[jeongho77](https://github.com/jeongho77)|[sionhyeop](https://github.com/sionhyeop)|

## 🔨 Stacks
<div style="display:flex; flex-direction:row;">
    <img src="https://img.shields.io/badge/android-34A853?style=for-the-badge&logo=android&logoColor=white">
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
    <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> 
</div>
<br>

## 프로젝트 시연 영상
https://www.youtube.com/watch?v=7gkp8PK1i60

## 프로젝트 기능
### 1. 스플래시 화면
* 앱이 켜진 후 첫화면입니다.
* 3초정도 이 화면이 나온 후 메인 화면으로 넘어갑니다.
<img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/49914aea-c262-4e3a-a834-3c29f786d8a3">

### 2. 메인 화면
* 메인화면입니다.
* 날씨 데이터를 받아와 분류하여 한눈에 알아보기 쉽게 아이콘을 사용하여 표현하였습니다.
* 월,일,요일,시간,AM,PM,현재 온도,오늘의 기온,현재 조회하고 있는 지역,시간대별 날씨와 온도를 메인 화면에서 보여줍니다.
* 뒤에 있는 배경화면은 현재 시간에 따라 바뀌며 분류한 사진은 4개로 나누어 배경화면을 설정하였습니다.(밤 21-05, 새벽 06-09, 오전 10-16, 오후 17-21)
* 오늘의 옷 추천받기, 다음날 날씨보기, 위치 바꾸기 버튼을 두어 기능들을 연결해 두었습니다. 
<img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/fa0d70b7-07d9-462f-a9d1-9c29b8633c8d">

### 3. 위치 바꾸기
* 울산에서만 조회할 수 있는 앱이기에 지역은 울산내에 있는 구로 분류하였습니다.
* 선택할 시 위도와 경도가 해당지역으로 바뀌어 날씨데이터를 재요청을 하며 해당 지역의 날씨 데이터를 새로 받아와 메인화면의 아이콘들이 일부 바뀔수가 있습니다.
* 지역이 바뀔시 다른 기능에도 영향을 미칩니다.
<img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/5bfe4df0-e219-4cf6-a95d-66c353846240">

### 4. 다음날 날씨보기
* 선택한 지역의 4~5일에까지 대략적인 날씨정보를 볼 수 있습니다.
* 4~5일인 이유는 날씨데이터가 현재 시간에 따라 조회하기에 18시 이후에 조회할 경우 최대 5일까지의 데이터를 받아올 수 있습니다.
* 최고온도, 최저온도, 그날의 날씨를 확인할 수 있습니다 .
<img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/6411099c-b150-404a-8dc0-3b5c6858da26">

### 5. 오늘의 옷 추천받기
* 현재 기온에 따른 옷을 추천받을 수 있습니다.
* 날씨 별 옷차림 추천 데이터는 표를 보고 참고하였습니다.
* 메인화면의 여백을 활용하기 위해 프레그먼트를 사용하여 구현하였습니다.
* 옷 추천은 랜덤이며 기온별로 데이터를 분류하고 난수를 사용하여 뽑는 형식으로 구현하였습니다.
  
<img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/c2851040-be56-4dcd-8a3f-b1b8a4d873fb">
<img width="500" height="300" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/9ebe5a8c-f874-4f73-b929-75d9d7616392">
https://namu.wiki/w/%EA%B8%B0%EC%98%A8%EB%B3%84%20%EC%98%B7%EC%B0%A8%EB%A6%BC

## 프로젝트 시간별 메인 뷰 사진
<div style="display:flex; flex-direction:row;">
    <img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/9d3afde7-29c5-420f-a37a-5d903685adaa">
    <img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/071468f2-27b7-460b-b572-0e2df065daff">
    <img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/be5b7050-d44a-478d-8dfa-2f30f56bf1e9">
    <img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/a736c712-28c6-4358-b05e-cc260e93023d">
    <img width="200" alt="index" src="https://github.com/jeongho77/Today_weather/assets/115057094/3c2e3acd-8ad8-4516-8188-52396744bec4">
</div>
