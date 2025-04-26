package study.microblogjdbc.post;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Post {
    private Long id;
    
    @NotBlank(message = "제목은 필수 입력 항목입니다")
    @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하로 입력해주세요")
    private String title;
    
    @NotBlank(message = "내용은 필수 입력 항목입니다")
    @Size(min = 1, max = 1000, message = "내용은 1자 이상 1000자 이하로 입력해주세요")
    private String content;
    
    private LocalDateTime createdAt;

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
}