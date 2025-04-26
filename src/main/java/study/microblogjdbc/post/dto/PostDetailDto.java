package study.microblogjdbc.post.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import study.microblogjdbc.comment.Comment;
import study.microblogjdbc.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
public class PostDetailDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<Comment> comments;

    private PostDetailDto(Long id, String title, String content, LocalDateTime createdAt, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.comments = comments;
    }

    public static PostDetailDto of(Post post, List<Comment> comments) {
        return new PostDetailDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                comments
        );
    }

    public Post toPost() {
        return Post.of(id, title, content, createdAt);
    }
}