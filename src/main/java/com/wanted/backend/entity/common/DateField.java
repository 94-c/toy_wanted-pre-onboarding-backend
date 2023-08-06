package com.wanted.backend.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = { "createdAt", "updatedAt" },
        allowGetters = true
)
public abstract class DateField {

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
