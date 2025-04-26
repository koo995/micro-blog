package study.microblogjdbc.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.microblogjdbc.comment.Comment;
import study.microblogjdbc.comment.CommentRepository;
import study.microblogjdbc.post.dto.PostDetailDto;
import study.microblogjdbc.post.dto.PostWithCommentCount;
import study.microblogjdbc.post.mapper.PostMapper;

import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Page<PostWithCommentCount> getPostsWithCommentCount(Pageable pageable) {
        Page<Post> postPages = postRepository.findPosts(pageable);

        List<Long> postIds = postPages.getContent().stream()
                .map(Post::getId)
                .toList();

        Map<Long, Integer> commentCountMap = commentRepository.findCommentCountsByPostIds(postIds);

        return PostMapper.toPostWithCommentCountPage(postPages, commentCountMap, pageable);
    }

    public PostDetailDto getPostDetail(Long id) {
        Post post = postRepository.findById(id);
        List<Comment> comments = commentRepository.findByPostId(id);
        return PostDetailDto.of(post, comments);
    }

    public void createPost(String title, String content) {
        Post post = Post.create(title, content);
        postRepository.save(post);
    }

    public void updatePost(Long id, String title, String content) {
        Post post = Post.of(id, title, content, null);
        postRepository.update(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}