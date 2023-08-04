package com.wanted.backend.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

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
