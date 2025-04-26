package study.microblogjdbc.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.microblogjdbc.comment.Comment;
import study.microblogjdbc.post.dto.PostDetailDto;
import study.microblogjdbc.post.dto.PostWithCommentCount;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Page<PostWithCommentCount> getPostsWithCommentCount(Pageable pageable) {
        Page<Post> postPages = postRepository.findAll(pageable);

        return postPages.map(p ->
                PostWithCommentCount.of(p, p.getComments().size())
        );
    }

    public PostDetailDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        List<Comment> comments = post.getComments();
        return PostDetailDto.of(post, comments);
    }

    public void createPost(String title, String content) {
        Post post = Post.create(title, content);
        postRepository.save(post);
    }

    public void updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setTitle(title);
        post.setContent(content);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}