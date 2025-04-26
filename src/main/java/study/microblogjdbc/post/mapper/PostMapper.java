package study.microblogjdbc.post.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.microblogjdbc.post.Post;
import study.microblogjdbc.post.dto.PostWithCommentCount;

import java.util.List;
import java.util.Map;

public class PostMapper {

    public static Page<PostWithCommentCount> toPostWithCommentCountPage(
            Page<Post> postPage,
            Map<Long, Integer> commentCountMap,
            Pageable pageable
    ) {
        List<PostWithCommentCount> postsWithComments = postPage.getContent().stream()
                .map(post -> toPostWithCommentCount(post, commentCountMap))
                .toList();

        return new PageImpl<>(postsWithComments, pageable, postPage.getTotalElements());
    }

    public static PostWithCommentCount toPostWithCommentCount(Post post, Map<Long, Integer> commentCountMap) {
        return PostWithCommentCount.of(
                post,
                commentCountMap.getOrDefault(post.getId(), 0)
        );
    }
} 