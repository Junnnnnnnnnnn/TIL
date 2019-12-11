# github 특강 branch

### branch란
- **가지** 라고도 하고 분기라고도 한다. 여러명에서 한 프로젝트를 완성할때 사용하는 기술이다.

#### * commit을 어떤것을 했는가 보여주는 명령어

`git log --oneline`  이 명령어로 commit으로 기록한 것을 보여준다.

```git
wjsxo@YMCA MINGW64 ~/OneDrive/바탕 화면/branch (master)
$ git log --oneline
25d0ce0 (HEAD -> master) 브랜치 샌성 명령어 추가
a4535d2 branc를 배워봅시다
3fb160a branch 시작
```

위와 같은 결과를 얻을 수 있다.



#### 브랜치 명령어

1. 브랜치 생성:  `git branch (브랜치 이름)`
2. 브랜치 확인:  `git branch` 
3. 브랜치 이동:  `git checkout change` 
4. 브랜치 이동:  `git checkout change`
5. 브랜치 삭제:  `git branch -d (브랜치이름)`
6. 브랜치 생성 + 이동:  `git checkout -d (브랜치이름)`
7. 마스터와 브랜치 병합 : `git marge (브랜치 이름)`