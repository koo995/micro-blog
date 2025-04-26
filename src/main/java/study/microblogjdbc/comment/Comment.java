package study.microblogjdbc.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import study.microblogjdbc.post.Post;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotBlank(message = "내용은 필수 입력 항목입니다")
    @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하로 입력해주세요")
    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Comment create(String content) {
        Comment comment = new Comment();
        comment.content = content;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }
}