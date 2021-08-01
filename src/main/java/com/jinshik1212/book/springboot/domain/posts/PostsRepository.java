package com.jinshik1212.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Interface 생성 후, JpaRepository<Entity클래스, PK타입> 를 상속하면 CRUD 메소드가 자동으로 생김
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
