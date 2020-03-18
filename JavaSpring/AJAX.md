# AJAX

## AJAX 란
### 정의

- Asynchronous Javascript And Xml(비동기식 자바스크립트와 xml) 즉 **XMLHttpRequest를** 통해 **클라이언트와** **서버간의** 데이터 처리를 전체페이지를 새로고침 하지 않고 **비동기적으로** 처리하기 위한 **라이브러리** 이다.

### 사용하는 이유

- 기본적으로 HTTP프로토콜은 클라이언트쪽에서 **Request를** 보내고 , Server쪽에서 **Response를** 받게되면 유지 되어 있던 연결이 끊기게 되어 있다. 그래서 처리된 데이터를 **갱신하기** 위해서는 **Request와** **Response를** 하면서 **페이지를** **갱신해야** 한다. 그렇게 되면 일부분만 갱신해야되는 데이터를 위해 페이지 **전체를** **갱신해야** 함으로 **자원낭비와** **시간낭비를** 가져올 수 있다.  그러나 **ajax를** 이용하며 전체 페이지를 갱신하는것이 아닌 XML **HttpRequest를** 통해 필요로 하는 데이터를 **json** 이나 **xml로** 서버로 보내주면 갱신없이 브라우저에 처리된 데이터를 볼 수 있다.

### 단점

1. 보안에 많이 취약하다
2. 연속으로 데이터를 요청하면 서버 부하가 증가할 수 있다.
3. XMLHttpRequest를 통해 통신을 하는 경우 사용자에게 아무런 진행 정보가 주어지지 않기 때문에 아직 요청 이 완료 되지 않았지만 사용자가 페이지를 떠나거나 오작동할 우려가 발생하게 된다.

## 메소드 및 기능

#### $.ajax() 

- $.ajax()는 http 요청을 만드는 강력하고 직관적인 방법을 제공한다.

$.ajax() 메서드의 원형은 다음과 같다

```javascript
$.ajax([옵션])
```



다음 코드에서 url은 HTTP요청을 보낼 서버의 주소이다.

**옵션은** HTTP요청을 구성하는 키와 벨류값으로 이루어 지고 있다.



다음예제는 $.ajax() 메서드의 대표적인 옵션들이다.

```javascript
$.ajax([
	url : "openapi/upload_picture", // HTTP 요청을 보낼 서버의 url 주소
    data: {name : "전봇대"}, //HTTP 요청과 함께 보낼 데이터
    type: "GET" // HTTP 요청 방식 (GET , POST)
    dataType: "json" // 서버에서 보내줄 데이터 타입
])
.done(function(json)){
	//HTTP요청이 성공하면 요청한 데이터가 done()메소드로 전달됨      
}
.fail(function(xhr, status, errorThrown){
 	//HTTP 요청이 실패하면 오류와 상태에 관한 정보가 fail() 메소드로 전달됨
  	//errorThrown = 오류명
})
.always(function(xhr, status){
 	//HTTP 요청이 성공하거나 실패하는 것에 상관없이 언제나 always() 메소드가 실행 됨   
})
```



다음예제는 $.ajax() 메서드 예제이다.

```javascript
$(function(){
    $("#requestBtn").on("click", function(){
        $.ajax(
        	url : "openapi/upload_picture"
        )
        .done(function(){
            alert("요청 성공");
        })
        .fail(function(){
            alert("요청 실패");
        })
        .always(function(){
            alert("요청 완료")
        })
    })
})
```

