# springboot-webservice-1212

1. domain 패키지에 posts 패키지와 Posts 클래스 만듦
  - 속성(Id...PK / title / content / author)
  - @Entity : 테이블과 링크될 클래스
  - @Id : PK 필드
 
2. Posts 클래스로 Database 접근하게 해줄 JpaRepository 생성
   - domain.posts 패키지 내 PostsRepository.java
   - 인터페이스, JpaRepository<Entity 클래스, PK타입> 상속하면 기본 CRUD 메소드 자동 생성됨
   - Entity 클래스와 기본 Entity REpository 는 함께 위치해야 함
   
3. 등록/수정/삭제 기능 만들기
   - web/PostsApiController
     . @PostMapping("/api/v1/posts") --> save 호출 (등록)
     . @PutMapping("/api/v1/posts/{id}") --> update 호출 (수정)
     . @GetMapping("/api/v1/posts/{id}") --> findById
   - service/posts/PostsService
     . 실제 save 메소드 구현 (Jpa 호출)
     . update 메소드 구현 (변경될 항목 Entity 값만 변경해주면 Transaction 끝나는 시점에 알아서 반영 됨..더티체킹)
     . findById 메소드 구현 (
   - web/dto/PostsSaveRequestDto, PostsUpdateRequestDto, PostsResponseDto
     . 주고 받는 dto
