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

4. JPA Auditing 으로 생성/수정시간 자동화
   - domain/BaseTimeEntity.java 추가
   - Application 클래스 상에 어노테이션 추가
     . @EnableJpaAuditing
   - Posts 클래스 BaseTimeEntity 상속

5. 머스태치
   - 파일위치 : src/main/resources/templates (스프링 부트가 자동으로 로딩)
   - src/main/resources/templates/index.mustache 생성
   - URL 매핑 --> Controller
   - web/IndexController.java 추가 (@GetMapping("/") --> return "index";)
 
6. 게시글 등록화면 만들기
   - 레이아웃(공통영역 파일 분리) 추가
     . footer.mustache : JQuery, bootstrap 포함
     . header.mustache : CSS 포함 
   - 글등록 버튼 추가 (<a href="/posts/save" ...>글등록</a>) -> "/posts/save" 로 이동함
   - IndexController 반영 (@GetMapping("/posts/save")...return "posts-save"; --> post-save.mustache 파일 호출함
   - post-save.mustache 파일 생성 (입력 form, "btn-save" 등록 버튼)
   - API 호출하는 JS 추가(src/main/resources/static/js/app/index.js)
     . var main = { 'click' 이벤트, save() 메소드 기술..ajax/json)
   - index.js 파일을 footer.mustache 에 추가함
     . <script src="/js/app/index.js"></script> 
      : 스프링부트가 기본적으로 src/main/resoures/static 하위 위치한 javascript, css, 이미지 등 정적파일 URL 에서 / 로 설정
      
7. 전체 조회 화면 만들기    
   - index.mustache UI 변경
     . 출력영역 추가 : {{#posts}} 하위 {{id}}, {{title}} 등 
   - PostsRepository 인터페이스에 조회 쿼리 추가
     . @Query("SELECT p FROM Posts p ORDER BY p.id DESC") --> findAllDesc();
   - PostsService 에 해당 메소드 추가
     . postsRepository 결과 Posts 의 Stream 을 map 을 통해 PostsListResponseDto 로 변환하여 List 로 리턴함
   - PostsListResponseDto 추가

8. 게시글 수정, 삭제
   - PostsApiController 에 수정 API 이미 만듦(update)
   - posts-update.mustache 파일 생성({{post.title}}, {{post.content}} 수정 가능하며, "btn-update" 수정완료 버튼)
   - 눌렀을 때 API 로 전달할 js 에 내용 추가(index.js)
     . 'click' 이벤트 발생 시, this.update(); 수행
   - index.mustache 파일에서 수정 페이지로 이동할 수 있도록 수정 (title 에 a href 추가)
   - IndexController.java 에 update 메소드 추가
     . @GetMApping("/posts/update/{id}")
       : "post" 에 dto 로 model.addAttribute 추가 후, "posts-update" 리턴 (posts-update.mustache 호출)
