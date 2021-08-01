package com.jinshik1212.book.springboot.service.posts;

import com.jinshik1212.book.springboot.domain.posts.Posts;
import com.jinshik1212.book.springboot.domain.posts.PostsRepository;
import com.jinshik1212.book.springboot.web.dto.PostsListResponseDto;
import com.jinshik1212.book.springboot.web.dto.PostsResponseDto;
import com.jinshik1212.book.springboot.web.dto.PostsSaveRequestDto;
import com.jinshik1212.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor // final 선언된 모든 필드를 인자값으로 하는 생성자를 롬복이 대신 생성함(Autowired 필요 없음)
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto)
    {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto)
    {
        Posts posts = postsRepository.findById(id)
                            .orElseThrow(() -> new
                                    IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        // 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다. (영속성 컨텍스트)
        // Entity 객체 값만 변경하면 별도 Update 쿼리를 날릴 필요 없다

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                        .orElseThrow(() -> new
                                IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되 조회기능만 남겨둠(조회 속도 개선)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts); //
    }
}
