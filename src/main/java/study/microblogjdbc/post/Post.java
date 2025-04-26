package study.microblogjdbc.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import study.microblogjdbc.comment.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수 입력 항목입니다")
    @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하로 입력해주세요")
    @Column(length = 100, nullable = false)
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다")
    @Size(min = 1, max = 1000, message = "내용은 1자 이상 1000자 이하로 입력해주세요")
    @Column(length = 1000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private Post(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Post create(String title, String content) {
        return new Post(null, title, content, LocalDateTime.now());
    }

    public static Post of(Long id, String title, String content, LocalDateTime createdAt) {
        return new Post(id, title, content, createdAt);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
    }
}