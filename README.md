# g to mL Converter

g, mL 간 상호 단위변환 앱

- 진행기간 : 2019. 07. 14 ~ 2019. 07. 18
- 사용기술 : Android Studio, Kotlin, SQLite
- [Play 스토어](https://play.google.com/store/apps/details?id=com.goldouble.android.gtomlconverter)


<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111610402-ab580180-881e-11eb-9a05-a8df57c1ea37.jpg" width="30%"/></p>

## 서비스 소개

- g to mL Converter는 베이킹 시 저울이 없는 경우 g 단위로 표기된 레시피를 각 식재료 별 밀도를 계산하여 부피 단위인 mL로 변환하여 **계량컵만으로 따라할 수 있게 도와주는 안드로이드 앱**입니다.
- **4개의 언어**(한국어, 영어, 스페인어, 포르투갈어)와 **총 39가지의 재료 단위**를 제공합니다.
- **계량한 결과를 저장**해 한 눈에 쉽게 볼 수 있도록 표시해놓을 수 있습니다.

## 상세 기능 소개

## 1. 단위 변환

### 1-1. 재료 선택

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111610582-d9d5dc80-881e-11eb-9117-a5c15e257612.jpg" width="30%"/></p>

- **Spinner**의 Dropdown을 활용해 재료를 선택할 수 있습니다.
- 재료는 총 39가지가 제공되고 있으며 한국어는 **가나다순**, 그 외의 언어는 **ABC순**으로 정렬되어 표시됩니다.
- 단위 입력 상태에서 재료를 변경할 경우 단위는 모두 0으로 초기화됩니다.

### 1-2. g → mL 변환

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111610614-e6f2cb80-881e-11eb-9de3-13c62e34cb3f.jpg" width="30%"/></p>

- 재료를 선택하고 g 단위에 숫자를 입력하면 mL 단위의 editText에 자동으로 변환된 단위가 표시됩니다.

### 1-3. mL → g 변환

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111610691-f96d0500-881e-11eb-901c-9ea3beaa2deb.jpg" width="30%"/></p>

- mL 단위의 editText에 숫자를 입력하면 반대로 g 단위의 editText에 변환된 단위가 표시됩니다.

### 2. 저장

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111610833-1d304b00-881f-11eb-8bd5-d52c9fa8a67e.jpg" width="30%"/></p>

- 저장 버튼을 누르면 하단 RecyclerView에 변환 기록이 저장됩니다.
- 데이터는 Room DB로 저장되어 앱을 종료해도 기록이 유지됩니다.
- 각 아이템 우측의 X 버튼을 터치해 리스트에서 제거할 수 있습니다.
