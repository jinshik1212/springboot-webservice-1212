package com.jinshik1212.book.springboot.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity 상속할 경우 필드들도 컬럼으로 인식되도록 해줌
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 에 Auditing 기능 포함
public abstract class BaseTimeEntity {
    // 모든 Entity 의 상위 클래스가 되어 Entity 들의 createdDate, modifiedDate 자동으로 관리

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
