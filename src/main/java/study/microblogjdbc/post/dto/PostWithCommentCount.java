package study.microblogjdbc.post.dto;

import lombok.Getter;
import study.microblogjdbc.post.Post;

import java.time.LocalDateTime;

@Getter
public class PostWithCommentCount {
    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;
    private final int commentCount;

    private PostWithCommentCount(Long id, String title, LocalDateTime createdAt, int commentCount) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
    }

    public static PostWithCommentCount of(Post post, int commentCount) {
        return new PostWithCommentCount(
            post.getId(),
            post.getTitle(),
            post.getCreatedAt(),
            commentCount
        );
    }
} 