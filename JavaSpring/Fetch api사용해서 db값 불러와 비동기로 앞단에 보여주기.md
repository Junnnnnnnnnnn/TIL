# Fetch api사용해서 db값 불러와 비동기로 앞단에 보여주기

## 개요

- 회원 전용으로 북마크기능을 추가하기 할것이다.

- **Fetch**를 사용한 이유는 북마크기능을 사용하기 위해서는 db에 사용자 정보를 통해 **bookmark** **table**에 레시피가 있는지 없는지, 만약 있다면 **select**문을 통해 **recipeID**를 가져오고, 가져온 **recipeID**를 통해 **recipe** **table**에 **recipeID**가 있는지 확인하고 **있다면** 레시피의 정보들을 가져오는 복잡한 로직으로 되어 있습니다.

  그렇기 때문에 요청과 응답을 **동기적으로** 한다면 앞단에서 데이터를 들고 올때 **버벅거림이** 있거나, **깜빡거림등이** 있어 깔끔하지 못합니다.

  그래서 **Fetch** **Api**를 통해 응답,요청을 **비동기화** 시켜서 **싱글** **페이지로** 북마크 기능을 사용할 수 있겠금 로직 구현하는것이 목표이다.

## 구현해야 할 기능

- **Fetch** **api**를 통해 **json** 데이터 **web** **server**로 보내기
- **web** **server**에서 **@**RequestBody**를 통해 **json** 데이터 브라우저로 보내기
- 브라우저에서 동적으로 가저온 **json** 데이터 li로 찍기

## 구현

### Front-end

![](readme/f1.jpg)

- 다음과 같이 재료에 대한 **detection**이 끝나고 레시피를 **추천해주는** 페이지이다.

  페이지를 보면 별표 모양이 있는데 저 **별표** 모양을 누르면 별표는 **노랑색으로** 변하고 `insertBookmark` **function**이 실행 된다.

  메서드의 로직은 다음과 같다.

> resultRecipe.js
>
> **funchtion insertBookmark(recipeID)**

```javascript
function insertBookmark(recipeID) {
    //console.log("recipeID", recipeID);
    const odj = {
        method: 'POST',
        body: JSON.stringify({userID: 0, recipeID: recipeID}),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        credentials: 'same-origin'
    };
    //fetch를 사용할때 url과 odj로 GET ,POST 메서드를 설정해 준다.
    fetch("http://localhost:8080/insertBookmark", odj)
    //reponse를 가져와서
        .then(res => {
        //status가 200이라면
        if (res.status == 200) {
            //servlet에서 return된 json값을 가져 온다.
            res
                .json()
                .then(json => console.log(json.recipeIDList));
        }
    })
}
```

- **mariaDB**에 있는 **bookmark** **table**에 **userID**와 **recipeID**를 **insert** 하기위한 로직중 fetch를 통해 recipeID를 **json** 데이터로 만들어 `http://localhost:8080/insertBookmark` **URL**로 요청하는 로직이다.

  > ```javascript
  > const odj = {
  >     method: 'POST',
  >     body: JSON.stringify({userID: 0, recipeID: recipeID}),
  >     headers: {
  >         "Content-type": "application/json; charset=UTF-8"
  >     },
  >     credentials: 'same-origin'
  > };
  > ```

- odj 변수는 총 네가지 정보로 구성되어 있다.

  - **method**

    - **URL**요청시 **GET** 방식과 **POST**방식 둘중에 하나의 방식으로 요청을 보낼 수 있다.

  - **body**

    - **url**에 요청할때 보내지는 데이터를 의미 한다.
    - **recipeID**를 보내야 하기 때문에 **recipeID**를 **Json** 데이터로 만든뒤 보내는 로직을 구현했다

  - **headers**

    - 보낼 데이터의 타입을 지정해 줘야 **web** **server**에서 값을 받을 수 있다.
    - `"Content-type": "application/json; charset=UTF-8"`
      - **Content-type** : 보낼 데이터의 타입을 지정할 수 있다.
      - **application/json** : **json** 타입의 데이터를 보낸다.
      - **charset = UTF-8**  : 인코딩 형식은 **UTF-8**로 할 것이다.

  - **credential**

    -  사이트에서 사용자 세션을 유지 관리해야하는 경우 **인증되지 않는 요청이 발생**한다.

      그래서 자격 증명 옵션을 통해 보내질 데이터가 **인증 된 데이터인지 아닌지를 정해야** 한다.

    - **same-origin**을 사용하면 자격 증명 설정을 했다라는 의미이므로 안전하게 데이터를 보낼 수 있다라는 의미와 같다.

  > ```javascript
  > fetch("http://localhost:8080/insertBookmark", odj)
  >         .then(res => {
  >             if (res.status == 200) {
  >                 res
  >                 .json()
  >                 .then(json => console.log(json.recipeIDList));
  >       }
  >     })
  > ```

- `fetch()` 메서드에 **두개의** **파라미터를** 넘긴다.

  - **URL**
    - 요청 보낼 **URL** 주소
  - **odj**
    - `fetch()`의 **init** 정보

- `then()`메서드란  요청으로 인한 **응답이 왔을때 처리**되야 될 로직을 구현한다. 

  위 코드의 구성은 다음과 같다

  - `.then(res => ...)` 응답 결과가 **res** 변수에 초기화된다.
  - `if(res.status == 200)` **res**에 **status**가 200(응답 정상)이라면 
  - `(json => console.log(json.recipeIDList))` 응답 받은 데이터중 **json** 데이터가 존재 하고 그 **json** 데이터의 **key** 중 **recipeIDList**가 있다면 **console**에 찍어라 라는 뜻입니다.



### Back-end

- web server에 넘어온 json 데이터를 구현해 놓은 bookmark table에 mapper를 통해 insert 시켜야 한다.

  로직은 다음과 같다.

> TestController.java
>
> **@RequestMapping(value="/insertBookmark", method=RequestMethod.POST)**

```java
 @RequestMapping(value="/insertBookmark", method=RequestMethod.POST)
    @ResponseBody public BookmarkVO insertBookmark(@RequestBody BookmarkVO resBody, @AuthenticationPrincipal SecurityUserInfo securityUserInfo) {
        //세션에 저장 되어 있는 id를 가져옴
        String userID = securityUserInfo.getUsername();
        //브라우저에서 json으로 넘어온 레시피 id를 int로 변환
        int recipeID = resBody.getRecipeID();
        //체크 되었는지 안되었는지 확인 할 수 있는 변수
        //db로 보낼 VO객체 생성
        BookmarkVO bookmarkVO = new BookmarkVO(userID, recipeID);
        
        if(bookmarkService.selectBookmark(bookmarkVO) != null){
            bookmarkService.deleteBookmark(bookmarkVO);
        }
        else{
            bookmarkService.insertBookmark(bookmarkVO);
        }
        System.out.println("userID: " + bookmarkVO.getUserID() + "recipeID: " + bookmarkVO.getRecipeID());
        System.out.println(bookmarkService.loadBookmark(userID));
        bookmarkVO.setRecipeIDList(bookmarkService.loadBookmark(userID));
        return bookmarkVO;
    }
```