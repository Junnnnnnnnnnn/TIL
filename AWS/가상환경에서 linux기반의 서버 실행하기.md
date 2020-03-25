# 가상환경에서 linux기반의 서버 실행하기

#### Window에서 Linux 원격제어

- SSH기능을 보다 수월하게 사용하기 위해 XSHELL 프로그램을 사용한다.

프로그램

- XSHELL
- url = https://www.netsarang.com/ko/xshell/ 설치 방법 생략

> XSHELL
>
> - EC2 세션 등록 방법
>   - 열기를 클릭하여 세션을 만들어 준다
>   - 세션을 만들때 EC2 연결 설명서에 명시되어 있는 ip주소와 다운 받아놓은 패스워드를 설정해준다.
>   - 그리고 연결을 하면 "Welcom to ubuntu" 라는 메세지와 함께 연결을 확인 할 수 있다.



#### Linux에서 웹서버 사용

1. XSHELL을 이용해서 ubuntu 가상머신에 접속한다
2. 접속은 세션을 등록해서 사용해도 되고 다음과 같은 코드를 작성해도 된다

```shell
ssh -i "password_name" ubuntu@aws_ip_address
```

3. 접속을 했으면 apache2를 설치해준다

```shell
sudo apt-get apache2
```

4. 접속이 안된다면 ubuntu를 최신버전으로 update해준다

```shell
sudo apt-get update
```

5. 해준다음 AWS에서 EC2 를 인스턴스 할때 생성된 도매인을 window에서 접속 해준다면 접속이 잘되는 것을 볼 수 있다.