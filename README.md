# 구티크
[![StackShare](https://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/opklnm102/9tique)  

온/오프라인 구제의류 쇼핑몰 상품들을 한군데로 모아 고객에게 편리한 구매 경험을 제공하는 모바일 커머스 서비스  
현재 구제 의류 시장에서 구매자의 니즈는 질좋고 희소성 있는 브랜드 의류를 값싸게 구매하는 것이지만 **여기저기 둘러봐야하는 문제**가 있고, 판매자는 **구제 의류 매장 및 상품을 소개해주는 마케팅 플랫폼의 부재**로 현재 인스타그램, 페이스북을 활용하고 있었습니다. 이런 현재 구제의류 시장에서의 문제점을 해결하기 위해 시작한 서비스입니다.  
구매자에게는 여기저기 둘러볼 필요 없이 **한곳에서 확인**할 수 있게, 판매자에게는 **구제의류만을 원하는 구매자들이 존재하는 곳**에서 마케팅을 할 수 있는 서비스를 제공하여 현재 구제의류시장의 불편함을 해소할 것입니다.


## Start
```sh
$ git clone https://github.com/mash-up-kr/9tique-backend.git
$ cd 9tique-backend

# jdk 1.8이상 필요
# 프로젝트 루트에서
$ chmod 700 ./gradlew
$ ./gradlew bootRun
```

## API 명세보기
* `http://127.0.0.1:8080/swagger-ui.html`로 접속


## 사용된 라이브러리
* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [QueryDSL](http://www.querydsl.com/)
* [Spring Security](http://projects.spring.io/spring-security/)
* [Lombok](https://projectlombok.org/)
* [Java JWT: JSON Web Token for Java and Android](https://github.com/jwtk/jjwt.git)
* [Springfox](https://github.com/springfox/springfox.git)
