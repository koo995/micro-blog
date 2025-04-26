package study.microblogjdbc.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void createComment(Long postId, String content) {
        commentRepository.save(Comment.create(postId, content));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}