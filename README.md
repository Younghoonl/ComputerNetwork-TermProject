# JavaServer

프로젝트 사양 Eclipse jdk 17버전 이상.>
아래 url은 18버전 Java SE Development kit 18.0.2.1 다운받고 초기 작성하였음을 알림.

https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html
->사실 람다나 특수한 문법 안쓸거면 사실 11버전 이상 이어도 상관 無
매번 clone, pull 한 다음에 jdk 설정하는게 귀찮을 뿐임.

아마 레포지토리 다운받고 난 뒤 분명히 jdk없다고 뜹니다.
자신의 jdk를 추가해줍시다.

1.Code 누르기->Download zip (그룹 멤버이면 할 필요 없음. clone, fork 하면 됨.)
2. 다운로드 받은 폴더 ( TermProject-main )->javaserver 폴더 있는거 확인.
3. 이클립스 켜서, import -> general-> existing project ... -> TermProject-main 을 루트 폴더로 선택.
3.1 이때 확실히, TermProject-main 밑에 바로 javaserver가 있는 루트TermProject-main 폴더를 선택해줘야 합니다.
4. 로딩이 된 후, 분명 에러가 뜬다고 할텐데, javaserver "패키지" 우클릭-> 프로퍼티-> Module path누르고 Add Library
->JRE system Library -> Execution environment->JAVA SE 11이상 버전으로 선택하고 Apply and close
이렇게 하면 기본적인 세팅이 완료가 됩니다. 아마 여기서도 에러나시는 분은

다시 프로퍼티 누르셔서 라이브러리 가보시면 두개의 JRE가 선택되어 있을텐데, 선택한 SE만 두고 다 삭제합니다.
11/5 N:1까지 완료
ISSUE FIX 11/6: ClientThread의 게임번호 0으로고정된 이슈 수정.



거의 끄으으으으으으으으읏
서버는 JavaServer에다가 /** 하고 / 한다음에 '하고' 에 커서 누르고 엔터누르면 @javadoc 어쩌구 나옴. 거기다가 이름적고 추가할 부분, 수정할 부분 등 뭐뭐 거시기 완료 적으면 됩니다.
어차피 주석이 위에 있는게 너무 길다 싶으면 왼쪽에 접기 눌러서 싹 접어버리면 n명이면 n줄만 차지해서 굿.

About
N:1까지 완료

