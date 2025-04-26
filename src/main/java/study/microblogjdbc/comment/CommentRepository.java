package study.microblogjdbc.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final NamedParameterJdbcTemplate namedJdbc;

    /**
     * 게시글 ID 목록으로 댓글 수를 한 번에 조회
     */
    public Map<Long, Integer> findCommentCountsByPostIds(List<Long> postIds) {
        if (postIds.isEmpty()) {
            return Collections.emptyMap();
        }

        String sql = """
            SELECT post_id, COUNT(*) AS comment_count
            FROM comment
            WHERE post_id IN (:ids)
            GROUP BY post_id
        """;

        MapSqlParameterSource params = new MapSqlParameterSource("ids", postIds);

        return namedJdbc.query(
                sql,
                params,
                rs -> {
                    Map<Long, Integer> map = new HashMap<>();
                    while (rs.next()) {
                        map.put(rs.getLong("post_id"), rs.getInt("comment_count"));
                    }
                    return map;
                }
        );
    }

    public void save(Comment comment) {
        String sql = """
            INSERT INTO comment (post_id, content, created_at)
            VALUES (:postId, :content, :createdAt)
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", comment.getPostId())
                .addValue("content", comment.getContent())
                .addValue("createdAt", comment.getCreatedAt());

        namedJdbc.update(sql, params);
    }

    public List<Comment> findByPostId(Long postId) {
        String sql = """
            SELECT *
            FROM comment
            WHERE post_id = :postId
            ORDER BY id DESC
        """;

        return namedJdbc.query(
                sql,
                new MapSqlParameterSource("postId", postId),
                new BeanPropertyRowMapper<>(Comment.class)
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM comment WHERE id = :id";

        namedJdbc.update(
                sql,
                new MapSqlParameterSource("id", id)
        );
    }
}
