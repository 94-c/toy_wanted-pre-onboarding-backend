package com.wanted.backend.entity;

import com.wanted.backend.entity.common.DateField;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Table(name = "posts")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post extends DateField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(String title, String content, User user) {
        return new Post(title, content, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return getId() != null && this.getId().equals(post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, user);
    }

}
