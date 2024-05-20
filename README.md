# 강연 신청 플랫폼 설계 및 API 개발


## 개발 접근 방법
### **1. DB 설계:**

강연 신청/취소를 DB에 어떻게 저장할지에 대한 고민을 거쳐, 신청 및 취소 내역을 모두 저장하는 테이블을 설계했습니다. 강연 신청 목록(application) 테이블에는 각 직원의 신청/취소 내역이 모두 기록되며, 생성일자를 기준으로 내림차순 정렬하여 가장 최근에 삽입된 내역을 가져와 해당 강의에 대한 신청 여부를 조회합니다.

이렇게 설계한 이유는 각 직원이 어떤 강연을 신청했는지, 언제 신청했는지, 그리고 신청을 취소한 내역이 있는지 추적할 수 있기 때문입니다. 또한, 가장 최신의 내역을 기준으로 강의 신청/취소 여부를 판단함으로써 정확한 상태를 파악할 수 있습니다.

``` mysql
PARTITION BY [컬렴명] ORDER BY [정렬 기준이 될 컬럼명] DESC
```
위와 같은 SQL 구문을 사용하여 row의 생성일자를 내림차순으로 정렬한 후, 가장 첫 번째 row를 가져와서 강의에 대한 신청 여부를 판단합니다.

<br>

### 2. 모킹(Mocking):
Junit을 프로젝트에 도입하면서 테스트 데이터의 처리 방법에 대해서 고민하였습니다.

1. 데이터를 insert하는 sql 모음을 파일로 작성하여 테스트 시도 전 파일을 읽는 방식으로 구현
2. Mock 코드로 작성

2가지의 방법 중에서 Mock 코드를 사용하여 하나의 코드 내에서 관리하는 것이 효율적이라고 판단하여 모킹을 활용하여 구현했습니다. 이를 통해 테스트 데이터를 손쉽게 관리하고 코드 내에서 필요한 테스트 시나리오를 자유롭게 작성할 수 있었습니다


<br> 

## 개발 언어 및 프레임워크
- Java 17
- Spring Boot v3.2.5
- Gradle v8.7
- JPA
- Lombok
- Junit 5
- Mysql

<br> 

## DB 설계
강연 신청 목록(application) 테이블과 강연(lecture) 테이블이 N:1,
강연 신청 목록(application) 테이블과 직원(employee) 테이블이 N:1인 구조를 갖도록 설계했습니다.

<br> 

![image](https://github.com/nacoon53/LectureBooking/assets/22345243/bc0e43dc-385e-4fe1-b5a1-b5a16e836fb0)

<br>

### 모델(DB)
- 강연(lecture)
    - 강연 ID **(PK)**
    - 강연자
    - 강연장
    - 신청 인원
    - 강연 시작 시간
    - 강연 내용
- 직원(employee)
    - 사번 **(PK)**
    - 이름
    - 퇴사 여부
    - 입사일
- 강연 신청 목록(application) (강연:유저 = 1:N)
    - 신청 ID **(PK)**
    - 강연ID
    - 사번
    - 신청 일자
    - 신청 상태 (0이면 취소, 1이면 신청)

<br> 

## API 명세
- BackOffice
  - 강연 목록(전체 강연 목록)
    - GET - /admin/lectures
  - 강연 등록(강연자, 강연장, 신청 인원, 강연 시간, 강연 내용 입력)
    - POST - /admin/lectures
  - 강연 신청자 목록(강연 별 신청한 사번 목록)
    - GET - /admin/lectures/{강연ID}/applications

- Front
  - 강연 목록(신청 가능한 시점부터 강연 시작 시간 1일 후까지 노출)
    - GET - /api/v1/lectures
  - 강연 신청(사번 입력, 같은 강연 중복 신청 제한)
    - POST - /api/v1/lectures/apply
  - 신청 내역 조회(사번 입력)
    - GET - /api/v1/users/T0005/applications
  - 신청한 강연 취소
    - POST -/api/v1/lectures/cancel
  - 실시간 인기 강연
    - GET - /api/v1/lectures?popular=true
