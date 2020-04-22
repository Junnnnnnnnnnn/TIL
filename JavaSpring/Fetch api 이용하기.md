# Fetch api 이용하기

## Fetch를 사용하는 이유

- 북마크 기능 구현을 위해 Fetch사용
- Ajax 방식 중 하나인 Fetch를 사용해서 응답,요청 을 동기가 아닌 비동기 적으로 함으로써 브라우저에 html이 끊김 없이 로직 처리를 해준다.

## 구현해야할 기능

1. Javascript로 Fetch api 로직 구현
2. Spring Framework에서 요청 받아 응답하는 로직 구현

일단 테스트로 동작 되는지에 요점을 두고 로직을 처리함



## 로직

> resultRecipe.js

```javascript
const odj = {
        method: 'POST',
        body: JSON.stringify({
            name: "mijoo",
            num: 00010001
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        } 
    };
    //fetch를 사용할때 url과 odj로 GET ,POST 메서드를 설정해 준다.
    fetch("http://localhost:8080/testFetch", odj)
    //reponse를 가져와서
    .then(res => {
        //status가 200이라면
        if(res.status == 200){
            //servlet에서 return된 json값을 가져 온다.
            res.json().then(json => console.log(json));
        }
    })
```

- odj는 fetch api를 사용할때 url로 보낼 부가 정보라고 생각하면 된다. 사용방법은 fetch() 메서드 두번째 파라미터로 값으로 넣어준다. odj가 가질 속성 중 필자가 중요하다고 생각하는 부분은 3가지 이다.
  - method
    - 요청 방식중 POST 와 GET 둘 중에 하나를 사용 할 수 있다.
    - json type을 보낼 때에는 POST를 사용한다.
  - body
    - 요청 url로 보내질 값이다. 
    - 필자는 JSON.stringify를 사용해서 문자열을 json을 변환 시켰다.
  - headers
    - 요철 할때 보내질 data의 타입을 정해놓는
    - json 타입을 보내고 싶다면 `application/json`을 입력 하면 된다
- fetch() 메서드는 1번째 인자로 요청 url , 2번째 인자로는 odj 정보를 가진다 여기서 .then이란 요청으로 인한 응답이 왔을때 처리되야 될 로직을 구현한다. 
  위 코드는 `.then(res => ...)` 응답 결과가 res 변수에 초기화되고 
  `if(res.status == 200)` res에 status가 200(응답 정상)이라면 
  `res.json().then(json => console.log(json))` 응답 받은 데이터중 json이 있으면 console에 찍어라 라는 뜻입니다.

> testController.java

```java
    @RequestMapping(value="/testFetch", method=RequestMethod.POST)
    public TestVo requestMethodName(@RequestBody TestVo testVo) {
        TestVo vo = new TestVo();
        
        System.out.println(testVo.getName() + " , " + testVo.getNum());
        return vo;
    }
```

- 응답과 요청에 대한 아주 기본적인 데이터 처리 로직이다.
- fetch api를 통해 요청 받은 객체를 `@RequsetBody`를 통해 가져올 수 있다. 데이터는 string형과 int형 변수로 이루어진 json파일이다. 
- TestVO는 필드변수가 String형 변수와 int형 변수로 구성되어 있고 lombok을 통해 자동 getter,setter로직 구현이 되어 있다.
- 요청받은 데이터를 가공하고 응답을 보내는 로직은 간단하다. 그냥 json형태로 return만 해주면 끝!! json형태는 사용자 지정 클래스로 구현해야 한다.





이렇게 fetch api를 이용해서 데이터 요청, 그리고 spring boot framework를 통해서 요청에 대한 데이터를 가공하고 다시 응답하는 로직을 구현해 봤다. 



좀더 공부해서 북마크 기능까지 구현할 생각이다. 

기한 4/17일 북마크 기능 commit이 목표이다