# 크롤링이란

**web** 상에 존재하는 **contents** 를 수집하는 작업 

### BeautifulSoup

```python
from bs4 import BeautifulSoup
import requests
```

두 전처리기를 통해 크롤링을 준비 해준다.

#### 첫번째로 원하는 페이지의 url을 가져와준다.

```python
url = "데이터 수집을 위한 웹 페이지의 url"
responce = requests.get(url)
```

url을 가져와 **import** 한 **requests** 클레스를 사용해 해당 url을 가져온다

requests.get을 통해 url의 데이터를 가져온다
**url**에 접속을 잘했다면 `RESPONSE[200]` 라는 결과 값을 가져온다.

```python
soup = BeauifulSoup(responce.text , "html.parser")
```

requests 메서드를 통해  가져온 url 데이터를 **BeautifulSoup** 에 설정 해준다.

#### 두번째로 원하는 content의 tag값을 가져온다

1. **find()** 함수

   ```python
   soup.find(가져오고싶은 content의 tag값)
   ```

   만약 tag의 속성값을 통해 **content** 를 가져오고 싶다면 **딕션어리** 타입으로

    `("table" , {"class": "table_develop3"})`

   **table** 태그 형식에 **class** 이름이 **table_develop3** 이다. 

2. **select_one()** 함수 ★★ 주로 사용

   web 개발자들이 크롤링을 **잘 하지 못하도록** 프로그래밍 했으므로 **selector**를 많이 사용하여 에러를 줄인다.

   ```python
   soup.select_one(원하는 id값)
   ```

   **매개변수**로 들어갈 값은 

   ![](./selecter.png)

   를 통해 **카피**하고 적용시켜 준다

   ```python
   daller = soup.select_one("#exchangeList > li.on > a.head.usd > div > span.value")
   ```

   가져온 **태그**의 **contents** 를 가져오고 싶다면

   ```python
   print("daller: ",daller.string)
   ```

   `daller`변수에 `string`만 추가 한다면 원하는 **값**을 얻을 수 있다.

   ★★ selector **태그** 안에 **태그** 찾을때

   ```python
   tag_1 = soup.select_one(".subway>li:nth-chlid(1)")
   ```

   ![](BeautifulSoup_사용법.assets/li.png)

   **만약** subway라는 **클래스**에 **class**나 **id**값이 없는 **li**태그가 3개가 있을때 

   **첫번째** li값을 가져오고 싶다면!! `.subway>li:nth-chlid(1)"` 다음과 같이 사용한다.

   3.**list**형식의 태그 값을 가져올때

   ```python
   list_rt = soup.select(f"#PM_ID_ct > div.header > div.section_navbar > 									  div.area_hotkeyword.PM_CL_realtimeKeyword_base > 									  div.ah_roll.PM_CL_realtimeKeyword_rolling_base > div > ul > 						  li)
   ```

   `select`를 단일로 사용을 한다면 list형식의 **태그**를 들고 올 수 있다.

   