package study.microblogjdbc.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.microblogjdbc.post.Post;
import study.microblogjdbc.post.PostRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void createComment(Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Comment comment = Comment.create(content);
        post.addComment(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        Post post = comment.getPost();
        post.removeComment(comment);
    }
}