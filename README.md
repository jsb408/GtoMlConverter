# GtoMlConverter
베이킹을 할 때 저울이 없는 경우 g 단위로 표기된 레시피를 각 식재료 별 밀도를 계산하여 부피 단위인 mL로 변환하여 계량컵만으로 따라할 수 있게 도와주는 안드로이드 앱입니다.

## 기능설명
1. g to mL 변환 기능 : g 단위를 mL로 변환
2. mL to g 변환 기능 : mL 단위를 g 단위로 변환
3. 저장 기능 : 자주 사용하는 재료 혹은 레시피에 있는 재료들을 저장해두면 필요할 때 다시 변환할 필요없이 바로 확인 가능

## 사용기술
1. RoomDB : 재료 데이터 저장 시 SQLite를 통해 저장하고 읽어와 RecyclerView에 표시
2. AdMob : 하단 배너 광고 표시
