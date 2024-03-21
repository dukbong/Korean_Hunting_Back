# Korean_Hunting_Back
소스코드에 한글이 있는지 확인하는 프로젝트

#### 2024/02/29
- Sign up Controller 및 Test Code 작성
- [Test Code](https://github.com/dukbong/Korean_Hunting_Back/blob/main/src/test/java/com/hangulhunting/Korean_Hunting/controller/SignUpTest.java) : 동일 ID가 있을때와 없을때

#### 2024/03/04
- Login Controller 및 Test Code 작성
- [Test Code](https://github.com/dukbong/Korean_Hunting_Back/blob/main/src/test/java/com/hangulhunting/Korean_Hunting/controller/LoginTest.java) : 정상적인 로그인과 비정상적인 로그

#### 2024/03/20
- 압축 기능을 snappy 라이브러리 사용해보기로 했다.
  - 선택 이유 : 압축률 보다는 빠른 속도가 필요하며 코드가 간결하다
  - 실패 이유 : ReadMe를 정확히 읽지 않아서 zip파일 관련 압축 기능이 되지 않는다는걸 몰랐다.
- 파일을 읽을 때 BufferedReader 대신 Files.lines를 사용  
  - 선택 이유 : JDK 1.8 이후 부터 더 효율적이라고 해서 테스트 해 본 결과 Files.lines가 더 빨랐다.
- FileService 리팩토링
  - 단일 책임 원칙을 최대한 지키기 위해 메소드 분리
