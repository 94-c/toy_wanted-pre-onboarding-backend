# 원티드 프리온보딩 백엔드 인턴십 - 선발과제

게시판을 관리하는 RESTful API를 개발해 주세요. 이때, 다음의 기능을 구현해야 합니다. 데이터베이스의 테이블 설계는 지원자분의 판단에 맡겨져 있습니다. 요구사항을 충족시키는 데 필요하다고 생각되는 구조로 자유롭게 설계해 주세요.

- [x] **과제 1. 사용자 회원가입 엔드포인트**
    - [x] 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
    - [x] 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
        - [x] 이메일 조건: **@** 포함
        - [x] 비밀번호 조건: 8자 이상
        - [x] 비밀번호는 반드시 암호화하여 저장해 주세요.
        - [x] 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
- [X] **과제 2. 사용자 로그인 엔드포인트**
    - [X] 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
    - [X] 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
- [X] **과제 3. 새로운 게시글을 생성하는 엔드포인트**
- [X] **과제 4. 게시글 목록을 조회하는 엔드포인트**
    - [X] 반드시 Pagination 기능을 구현해 주세요.
- [X] **과제 5. 특정 게시글을 조회하는 엔드포인트**
    - [X] 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
- [X] **과제 6. 특정 게시글을 수정하는 엔드포인트**
    - [X] 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    - [X] 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
- [X] **과제 7. 특정 게시글을 삭제하는 엔드포인트**
    - [X] 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    - [X] 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.


## 🧑🏻‍💻 지원자 소개

----
### 이름 :  최형우
### 이메일 : hyeongwoo26@gmail.com 
### 📌 기술 스택

----
- Java 17
- Spring Boot 2.7.12
- Maven 
- MySQL 

### 📌 라이브러리 및 프레임 워크

---
- Spring Data JPA
- Spring Security
- Junit
- validation
- jjwt
- lombok

## 💻 애플리케이션의 실행 방법

---
~~~
git clone https://github.com/94-c/wanted-pre-onboarding-backend.git

docker-compose up --build -d
~~~
### 📌 AWS 배포 IP 주소

---
~~~
http://15.164.204.138:8080/
~~~

### 📌 AWS 배포 환경

---
![Group 8](https://github.com/94-c/wanted-pre-onboarding-backend/assets/79362952/c7a16b63-d25c-4f85-a190-e3106cfe0a8c)


### 📌 엔드포인트 호출 방법

---
> request 와 response 정보는 "API 명세(request/response 포함)" 를 참고해 주세요.

- 회원가입 엔드포인트
    - POST `/api/v1/signup`
- 로그인 엔드포인트
    - POST `/api/v1/login`
- 게시글 전체 목록 조회 엔드포인트
  - GET `/api/v1/posts`
- 게시글 생성 엔드포인트
    - POST `/api/v1/posts`
- 게시글 상세 조회 엔드포인트
    - GET `/api/v1/posts/{postId}`
- 게시글 수정 엔드포인트
    - PUT `/api/v1/posts/{postId}`
- 게시글 삭제 엔드포인트
    - DELETE `/api/v1/posts/{postId}`



## 💿 데이터베이스 테이블 구조

---
![wanted_api](https://github.com/94-c/wanted-pre-onboarding-backend/assets/79362952/4ecee0a9-e908-45cb-8522-543f33aee50b)

## 🖥 구현한 API의 동작을 촬영한 데모 영상 링크

---
- youtube link : https://studio.youtube.com/video/Y7Q37BDZ8OA/edit

### - 📌 구현 방법 및 이유에 대한 간략한 설명

---
## 🧾 API 명세(request/response 포함)

---
### Swagger 
- http://15.164.204.138:8080/swagger-ui/

### 1. 회원가입 (Signup)

### 1.1 Request
- POST `/api/v1/signup`
    ```json
  {
      "email" : "hyeongwoo26@google.com",
      "password" : "12341234",
      "name" : "최형우"
    }
  ```
### 1.2 Response
- 201 Created
    ```json
  {
      "message": "success",
      "data": {
          "id": 3,
          "email": "hyeongwoo26@gmail.com",
          "name": "최형우",
          "createdAt": "2023-08-11T17:06:07.769654429"
      }
  }
  ```
- 400 Bad Request
    ```json
   {
      "message": "이메일이 중복되었습니다",
      "errorCode": "EMAIL_DUPLICATED"
    }
   ```
  ```json
  {
    "message": "이메일 형식에 맞지않습니다",
    "errorCode": "UNAUTHORIZED"
  }
  ```
  ```json
  {
    "message": "패스워드 길이를 8자 이상 작성해 주세요",
    "errorCode": "WRONG_PASSWORD_INFO"
  }
  ```   

### 2. 로그인 (login)

### 2.1 Request
- POST `/api/v1/login`
    ```json
  {
      "email" : "hyeongwoo26@google.com",
      "password" : "12341234"
    }
  ```
### 2.2 Response
- 200 OK
    ```json
  {
  "message": "success",
  "data": {
        "grantType": "Bearer",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoeWVvbmd3b28yMEBnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkxODYwMzUzfQ.iUJ-FtCiQlKPDcvhUMwF-874qSI24imlR-u0MztaiCI5YCRQ3zzSDiUXo4HmyNQY0ckfrSEyyl-V8fc9wnITiQ"
      }
  }
  ```
- 400 Bad Request
    ```json
   {
     "message": "존재하지 않는 사용자입니다",
     "errorCode": "MEMBER_NOT_FOUND"
    }
   ```

### 3. 게시글 전체 조회 (findAllPosts)

### 3.1 Response
- GET `/api/v1/posts`
  ```json
  {
    "message": "success",
    "data": {
        "content": [
            {
                "id": 16,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 15,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 14,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 13,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 12,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 11,
                "title": "게시글 원티드 온보딩 backend",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 10,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 9,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 8,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            },
            {
                "id": 7,
                "title": "게시글 테스트 1",
                "content": "게시글 테스트 컨텐트",
                "createdAt": null,
                "modifiedAt": null,
                "users": {
                    "email": "hyeongwoo26@gmail.com",
                    "name": "최형우"
                }
            }
        ],
        "pageNo": 0,
        "pageSize": 10,
        "totalElements": 15,
        "totalPages": 2,
        "last": false
    }
  }
  ```

### 4. 게시글 등록 (createPost)

### 4.1 Request
- POST `/api/v1/posts`

  ```json
    {
	   "title" : "게시글 테스트 1",
      "content" : "게시글 테스트 컨텐트"
    }
  ```