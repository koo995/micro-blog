package study.microblogjdbc.comment;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {
    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;

    private Comment(Long id, Long postId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Comment create(Long postId, String content) {
        return new Comment(null, postId, content, LocalDateTime.now());
    }
}
