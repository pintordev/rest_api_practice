# REST API PRACTICE

## Intro.
>- **REST API 공부 내용 정리**
>- **연습용 리포지터리**

## 23-11-26
- [x] #7 - MemberControllerTest.t2 post:/member/login; include accessToken in response header
- [x] #8 - JwtProvider를 통해 토큰 생성
- [x] #9 - RsData, Response 클래스 도입해서 응답 규격화
- [x] #10 - 응답 헤더에서 accessToken 삭제
- [x] #11 - MemberControllerTest.t3 post:/member/login; assert json response body
- [x] #12 - API versioning /api/v1
- [x] #13 - MemberControllerTest.t4 get:/api/v1/member/me; inquire user information
- [x] #14 - 로그인이 필요한 경로에서 로그인한 유저 정보 받아오도록 CustomUserDetailsService 추가
- [x] #15 - 요청 헤더 Authorization 키에 accessToken 포함 시 자동 로그인

## 23-11-24
- [x] #1 - 프로젝트 세팅
- [x] #2 - DB 생성 및 앱 설정 파일 설정
- [x] #3 - MemberControllerTest.t1 post:/member/login
- [x] #4 - REST API에 맞지 않는 spring security 설정 제거
- [x] #5 - 앱 시작 시 회원 2명 자동 생성
- [x] #6 - 앱 시작 시 생성된 회원 정보 반환